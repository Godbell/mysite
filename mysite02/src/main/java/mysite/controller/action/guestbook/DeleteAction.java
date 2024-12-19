package mysite.controller.action.guestbook;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet;
import mysite.dao.GuestBookDao;
public class DeleteAction implements ActionServlet.Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        String password = req.getParameter("password");

        GuestBookDao dao = new GuestBookDao();

        dao.deleteByIdAndPassword(id, password);
        res.sendRedirect(req.getContextPath() + "/guestbook");
    }
}
