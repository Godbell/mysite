package mysite.config.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import mysite.security.AuthInterceptor;
import mysite.security.AuthUserHandlerMethodArgumentResolver;
import mysite.security.LoginInterceptor;
import mysite.security.LogoutInterceptor;
import mysite.service.UserService;

@Configuration
@EnableWebMvc
public class SecurityConfig implements WebMvcConfigurer {

    // Interceptors
    @Autowired
    private UserService userService;

    // Argument Resolver
    @Bean
    public HandlerMethodArgumentResolver handlerMethodArgumentResolver() {
        return new AuthUserHandlerMethodArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(handlerMethodArgumentResolver());
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor(userService);
    }

    @Bean
    public LogoutInterceptor logoutInterceptor() {
        return new LogoutInterceptor();
    }

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
            .addInterceptor(loginInterceptor())
            .addPathPatterns("/user/auth");

        registry
            .addInterceptor(logoutInterceptor())
            .addPathPatterns("/user/logout");

        registry
            .addInterceptor(authInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns("/user/auth", "/user/logout", "/assets/**");
    }
}