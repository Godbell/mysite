package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;
public class ModifyAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String postIdParam = req.getParameter("post_id");

        if (postIdParam == null) {
            res.sendRedirect(req.getContextPath());
            return;
        }

        BoardDao dao = new BoardDao();
        BoardVo vo = dao.findById(Long.parseLong(postIdParam));
        req.setAttribute("post", vo);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/modify.jsp");
        dispatcher.forward(req, res);
    }
}
