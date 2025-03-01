package mysite.config.app;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.repository.UserRepository;
import mysite.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(formLogin -> {
                formLogin
                    .loginPage("/user/login")
                    .loginProcessingUrl("/user/auth")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                    // .failureUrl("/user/login?result=fail");
                    .failureHandler(new AuthenticationFailureHandler() {
                        @Override
                        public void onAuthenticationFailure(
                            HttpServletRequest request,
                            HttpServletResponse response,
                            AuthenticationException exception) throws IOException, ServletException {
                            request.setAttribute("email", request.getParameter("email"));
                            request.setAttribute("result", "fail");
                            request
                                .getRequestDispatcher("/user/login")
                                .forward(request, response);
                        }
                    });
            })
            .logout(logout -> {
                logout
                    .logoutUrl("/user/logout")
                    .logoutSuccessUrl("/");
            })
            .authorizeHttpRequests(authorizeRequests -> {
                /* ACL */
                authorizeRequests
                    .requestMatchers(new RegexRequestMatcher("^/admin/?.*$", null))
                    .hasAnyRole("ADMIN")

                    .requestMatchers(new RegexRequestMatcher("^/user/update$", null))
                    //.authenticated()
                    .hasAnyRole("ADMIN", "USER")

                    .requestMatchers(new RegexRequestMatcher("^/board/?(write|modify|delete|reply)$", null))
                    .hasAnyRole("ADMIN", "USER")

                    .anyRequest()
                    .permitAll();
            })
            .exceptionHandling(exceptionHandling -> {
                // exceptionHandling.accessDeniedPage("/WEB-INF/views/errors/403.jsp");
                exceptionHandling.accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        response.sendRedirect(request.getContextPath());
                    }
                });
            });

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() {
        return new UsernamePasswordAuthenticationFilter(authenticationManager());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userRepository);
    }
}
