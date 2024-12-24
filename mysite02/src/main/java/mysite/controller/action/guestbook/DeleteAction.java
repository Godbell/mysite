package mysite.controller.action.guestbook;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.dao.GuestBookDao;
public class DeleteAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            res.sendError(400);
            return;
        }

        String password = req.getParameter("password");

        if (password == null || password.isEmpty()) {
            res.sendError(400);
            return;
        }

        GuestBookDao dao = new GuestBookDao();

        try {
            Long id = Long.parseLong(idParam);
            dao.deleteByIdAndPassword(id, password);
        } catch (NumberFormatException e) {
            res.sendError(400);
            return;
        }
        
        res.sendRedirect(req.getContextPath() + "/guestbook");
    }
}
