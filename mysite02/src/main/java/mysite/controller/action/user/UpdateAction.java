package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet;
import mysite.dao.UserDao;
import mysite.vo.UserVo;
public class UpdateAction implements ActionServlet.Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UserDao dao = new UserDao();

        UserVo vo = (UserVo)req.getSession().getAttribute("authUser");
        if (vo == null) {
            res.sendRedirect(req.getContextPath());
            return;
        }

        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String gender = req.getParameter("gender");

        vo.setName(name);
        vo.setPassword(("".equals(password) || password == null) ? null : password);
        vo.setGender(gender);

        dao.update(vo);
        req.setAttribute("result", "successful");

        res.sendRedirect(req.getContextPath() + "/user?a=updateform");
    }
}
