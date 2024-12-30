package mysite.security;

import org.springframework.web.servlet.HandlerInterceptor;

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
}
