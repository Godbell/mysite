package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.Action;

public class LogoutAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (session != null) {
            session.removeAttribute("authUser");
            session.invalidate();
        }

        res.sendRedirect(req.getContextPath());
    }
}