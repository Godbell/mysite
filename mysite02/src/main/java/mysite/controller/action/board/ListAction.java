package mysite.controller.action.board;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class ListAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pageParameter = req.getParameter("page");
        int page = pageParameter == null ? 1 : Integer.parseInt(pageParameter);
        int postsCountPerPage = 5;
        BoardDao dao = new BoardDao();

        List<BoardVo> list = dao.findAll(page, postsCountPerPage);
        req.setAttribute("list", list);
        req.setAttribute("postsCountPerPage", postsCountPerPage);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
        dispatcher.forward(req, res);
    }
}
