package mysite.controller.action.guestbook;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.dao.GuestBookDao;
import mysite.vo.GuestBookVo;
public class InsertAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        GuestBookDao dao = new GuestBookDao();

        GuestBookVo vo = new GuestBookVo();
        vo.setName(req.getParameter("name"));
        vo.setContents(req.getParameter("content"));
        vo.setPassword(req.getParameter("pass"));

        dao.insert(vo);
        res.sendRedirect(req.getContextPath() + "/guestbook");
    }
}
