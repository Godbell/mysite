package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.dao.UserDao;
import mysite.vo.UserVo;

public class JoinAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String gender = req.getParameter("gender");

        if (name == null || name.isEmpty()
            || email == null || email.isEmpty()
            || password == null || password.isEmpty()
            || gender == null || gender.isEmpty()
        ) {
            res.sendError(400);
            return;
        }

        UserVo vo = new UserVo();
        vo.setName(name);
        vo.setEmail(email);
        vo.setPassword(password);
        vo.setGender(gender);

        new UserDao().insert(vo);

        res.sendRedirect(req.getContextPath() + "/user?a=joinsuccess");
    }
}