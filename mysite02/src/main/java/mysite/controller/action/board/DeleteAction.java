package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.dao.BoardDao;
import mysite.vo.UserVo;
public class DeleteAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UserVo authUser = (UserVo)req.getSession().getAttribute("authUser");

        String postIdParam = req.getParameter("post_id");

        if (authUser == null) {
            res.sendRedirect(req.getContextPath());
            return;
        }

        Long postId = Long.parseLong(postIdParam);

        BoardDao dao = new BoardDao();
        dao.deleteById(postId, authUser.getId());

        res.sendRedirect(req.getContextPath() + "/board?a=list");
    }
}
