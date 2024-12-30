package mysite.security;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.vo.UserVo;
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(
        HttpServletRequest request, HttpServletResponse response, Object handler
    ) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        Auth auth = null;

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        if (handlerMethod.getMethodAnnotation(Auth.class) != null) {
            auth = handlerMethod.getMethodAnnotation(Auth.class);
        } else if (handlerMethod.getBeanType().getAnnotation(Auth.class) != null) {
            auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
        } else {
            return true;
        }

        String requiredRole = auth.role();
        UserVo userVo = (UserVo)request.getSession().getAttribute("authUser");

        if (userVo == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return false;
        }

        if (requiredRole.equals("ADMIN") && userVo.getRole().equals("USER")) {
            response.sendRedirect(request.getContextPath() + "/");
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
