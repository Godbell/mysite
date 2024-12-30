package mysite.security;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.service.UserService;
public class AuthInterceptor implements HandlerInterceptor {
    private final UserService userService;

    private AuthInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(
        HttpServletRequest request, HttpServletResponse response, Object handler
    ) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod)handler;

        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

        if (auth == null) {
            return true;
        }

        if (request.getSession().getAttribute("authUser") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        ModelAndView modelAndView
    ) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
        Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
