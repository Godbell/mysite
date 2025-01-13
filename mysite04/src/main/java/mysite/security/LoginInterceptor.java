package mysite.security;

import java.net.URLEncoder;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.service.UserService;
import mysite.vo.UserVo;
public class LoginInterceptor implements HandlerInterceptor {
    private final UserService userService;

    public LoginInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(
        HttpServletRequest request, HttpServletResponse response, Object handler
    ) throws Exception {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserVo user = userService.getUser(email, password);

        if (user == null) {
            response.sendRedirect(
                request.getContextPath() +
                    "/user/login?email=" +
                    URLEncoder.encode(email) +
                    "&result=fail"
            );

            return false;
        }

        request.getSession().setAttribute("authUser", user);
        response.sendRedirect(request.getContextPath() + "/");

        return false;
    }
}
