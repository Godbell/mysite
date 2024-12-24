package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.Action;
import mysite.vo.UserVo;

public class UpdateFormAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        UserVo authUser = (UserVo)session.getAttribute("authUser");
        if (authUser == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/updateform.jsp");
        rd.forward(request, response);
    }
}