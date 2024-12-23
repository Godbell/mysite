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
        BoardDao dao = new BoardDao();
        Long postId = Long.parseLong(req.getParameter("post_id"));

        req.setAttribute("post", dao.findById(postId));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/view.jsp");
        dispatcher.forward(req, res);
    }
}
