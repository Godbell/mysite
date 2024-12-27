package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.controller.action.user.LoginFormAction;
import mysite.repository.BoardDao;
import mysite.vo.UserVo;
public class DeleteAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UserVo authUser = (UserVo)req.getSession().getAttribute("authUser");

        if (authUser == null) {
            new LoginFormAction().execute(req, res);
            return;
        }

        String postIdParam = req.getParameter("post_id");

        if (postIdParam == null) {
            res.sendError(400);
            return;
        }

        try {
            Long postId = Long.parseLong(postIdParam);

            BoardDao dao = new BoardDao();
            dao.deleteById(postId, authUser.getId());
        } catch (NumberFormatException e) {
            res.sendError(400);
            return;
        }

        res.sendRedirect(req.getContextPath() + "/board?a=list");
    }
}
