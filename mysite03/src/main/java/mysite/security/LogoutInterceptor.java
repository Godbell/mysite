package mysite.security;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.vo.UserVo;
public class LogoutInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(
        HttpServletRequest request, HttpServletResponse response, Object handler
    ) throws Exception {
        UserVo user = (UserVo)request.getSession().getAttribute("authUser");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }

        HttpSession session = request.getSession();
        session.removeAttribute("authUser");
        session.invalidate();

        response.sendRedirect(request.getContextPath() + "/");
        return false;
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
