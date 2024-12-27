package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.dao.BoardDao;
public class ViewAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String postIdParam = req.getParameter("post_id");

        if (postIdParam == null || postIdParam.isEmpty()) {
            res.sendError(400);
            return;
        }

        BoardDao dao = new BoardDao();

        try {
            Long postId = Long.parseLong(postIdParam);
            req.setAttribute("post", dao.findById(postId));
            dao.increaseHitByPostId(postId);
        } catch (NumberFormatException e) {
            res.sendError(400);
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/view.jsp");
        dispatcher.forward(req, res);
    }
}
