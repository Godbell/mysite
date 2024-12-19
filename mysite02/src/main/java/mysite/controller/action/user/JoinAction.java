package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet;
import mysite.dao.UserDao;
import mysite.vo.UserVo;
public class JoinAction implements ActionServlet.Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UserDao dao = new UserDao();

        UserVo vo = new UserVo();
        vo.setName(req.getParameter("name"));
        vo.setEmail(req.getParameter("email"));
        vo.setPassword(req.getParameter("password"));
        vo.setGender(req.getParameter("gender"));

        dao.insert(vo);
        res.sendRedirect(req.getContextPath() + "/user?a=joinsuccess");
    }
}
