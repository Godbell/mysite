package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.controller.action.user.LoginFormAction;
import mysite.dao.BoardDao;
import mysite.vo.PostVo;
import mysite.vo.UserVo;
public class UpdateAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UserVo authUser = (UserVo)req.getSession().getAttribute("authUser");
        if (authUser == null) {
            new LoginFormAction().execute(req, res);
            return;
        }

        String postIdParam = req.getParameter("postId");
        if (postIdParam == null || postIdParam.isEmpty()) {
            res.sendError(400);
            return;
        }

        BoardDao dao = new BoardDao();
        PostVo vo = new PostVo();

        try {
            Long postId = Long.parseLong(postIdParam);
            vo.setId(postId);
        } catch (NumberFormatException e) {
            res.sendError(400);
            return;
        }

        vo.setTitle(req.getParameter("title"));
        vo.setContents(req.getParameter("content"));
        dao.update(vo, authUser.getId());

        res.sendRedirect(req.getContextPath() + "/board?a=view&post_id=" + vo.getId());
    }
}
