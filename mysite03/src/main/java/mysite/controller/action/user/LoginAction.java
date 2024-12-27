package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.Action;
import mysite.repository.UserDao;
import mysite.vo.UserVo;

public class LoginAction implements Action {
    private void failLogin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("result", "fail");
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp");
        rd.forward(req, res);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || password == null) {
            failLogin(req, res);
            return;
        }

        UserVo vo = new UserDao().findByEmailAndPassword(email, password);

        if (vo == null) {
            failLogin(req, res);
            return;
        }

        // 로그인 처리
        HttpSession session = req.getSession(true);
        session.setAttribute("authUser", vo);

        res.sendRedirect(req.getContextPath());
    }
}