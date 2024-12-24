package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.dao.UserDao;
import mysite.vo.UserVo;
public class UpdateAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UserDao dao = new UserDao();

        UserVo vo = (UserVo)req.getSession().getAttribute("authUser");
        if (vo == null) {
            (new LoginFormAction()).execute(req, res);
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
