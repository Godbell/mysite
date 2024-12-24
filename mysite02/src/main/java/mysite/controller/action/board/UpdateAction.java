package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.dao.BoardDao;
import mysite.vo.PostVo;
import mysite.vo.UserVo;
public class UpdateAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String postIdParam = req.getParameter("postId");

        if (postIdParam == null) {
            res.sendRedirect(req.getContextPath());
            return;
        }

        Long postId = Long.parseLong(postIdParam);
        UserVo authUser = (UserVo)req.getSession().getAttribute("authUser");

        BoardDao dao = new BoardDao();
        PostVo vo = new PostVo();

        vo.setId(postId);
        vo.setTitle(req.getParameter("title"));
        vo.setContents(req.getParameter("content"));

        dao.update(vo, authUser.getId());
        res.sendRedirect(req.getContextPath() + "/board?a=view&post_id=" + vo.getId());
    }
}
