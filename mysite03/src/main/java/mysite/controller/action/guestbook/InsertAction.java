package mysite.controller.action.guestbook;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.repository.GuestBookDao;
import mysite.vo.GuestBookVo;
public class InsertAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String name = req.getParameter("name");
        String contents = req.getParameter("content");
        String password = req.getParameter("pass");

        if (name == null || name.isEmpty()
            || contents == null || contents.isEmpty()
            || password == null || password.isEmpty()
        ) {
            res.sendError(400);
            return;
        }

        GuestBookDao dao = new GuestBookDao();
        GuestBookVo vo = new GuestBookVo();

        vo.setName(name);
        vo.setContents(contents);
        vo.setPassword(password);

        dao.insert(vo);
        res.sendRedirect(req.getContextPath() + "/guestbook");
    }
}
