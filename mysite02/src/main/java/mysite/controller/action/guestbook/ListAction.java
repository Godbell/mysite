package mysite.controller.action.guestbook;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.dao.GuestBookDao;
import mysite.vo.GuestBookVo;

public class ListAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        GuestBookDao dao = new GuestBookDao();

        List<GuestBookVo> list = dao.findAll();
        req.setAttribute("list", list);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/guestbook/index.jsp");
        dispatcher.forward(req, res);
    }
}
