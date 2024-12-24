package mysite.controller.action.board;

import java.io.IOException;

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
        BoardVo boardVo = dao.findAll(page, postsCountPerPage);
        req.setAttribute("list", boardVo.getPosts());
        req.setAttribute("postsCountPerPage", postsCountPerPage);
        req.setAttribute("totalCount", boardVo.getTotalCount());
        req.setAttribute("currentPage", page);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
        dispatcher.forward(req, res);
    }
}
