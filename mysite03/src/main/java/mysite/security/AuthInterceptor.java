package mysite.security;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.vo.UserVo;
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
        throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        Auth auth = null;

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        if (handlerMethod.getMethodAnnotation(Auth.class) != null) {
            auth = handlerMethod.getMethodAnnotation(Auth.class);
        }
        if (handlerMethod.getBeanType().getAnnotation(Auth.class) != null) {
            auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
        }
        // if no @Auth annotated
        if (auth == null) {
            // run controller method
            return true;
        }

        String requiredRole = auth.role();
        UserVo userVo = (UserVo)req.getSession().getAttribute("authUser");

        if (userVo == null) {
            res.sendRedirect(req.getContextPath() + "/user/login");
            return false;
        }

        if (requiredRole.equals("ADMIN") && userVo.getRole().equals("USER")) {
            res.sendRedirect(req.getContextPath() + "/");
            return false;
        }

        return true;
    }
}
