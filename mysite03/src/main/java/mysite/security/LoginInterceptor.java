package mysite.security;

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
            request.setAttribute("email", email);
            request.setAttribute("result", "fail");
            request
                .getRequestDispatcher("/WEB-INF/views/user/loginform.jsp")
                .forward(request, response);

            return false;
        }

        request.getSession().setAttribute("authUser", user);
        response.sendRedirect(request.getContextPath() + "/");

        return false;
    }
}
