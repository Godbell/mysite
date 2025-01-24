package mysite.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import mysite.security.UserDetailsServiceImpl;
import mysite.service.UserService;

@SpringBootConfiguration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> webSecurity.httpFirewall(new DefaultHttpFirewall());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/user/login")
                    .loginProcessingUrl("/user/auth")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                    // .failureUrl("/user/login?result=fail");
                    .failureHandler((request, response, exception) -> {
                        request.setAttribute("email", request.getParameter("email"));
                        request.setAttribute("result", "fail");
                        request
                            .getRequestDispatcher("/user/login")
                            .forward(request, response);
                    })
            )
            .logout(logout ->
                logout
                    .logoutUrl("/user/logout")
                    .logoutSuccessUrl("/")
            )
            .authorizeHttpRequests(authorizeRequests ->
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
                    .permitAll()
            )
            .exceptionHandling(exceptionHandling -> {
                // exceptionHandling.accessDeniedPage("/WEB-INF/views/errors/403.jsp");
                exceptionHandling.accessDeniedHandler(
                    (request, response, accessDeniedException) -> {
                        response.sendRedirect(request.getContextPath());
                        System.out.println(accessDeniedException.getMessage());
                    }
                );
            });

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserService userService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService(userService));
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(
        UserService userService
    ) {
        return new UsernamePasswordAuthenticationFilter(authenticationManager(userService));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new UserDetailsServiceImpl(userService);
    }
}
