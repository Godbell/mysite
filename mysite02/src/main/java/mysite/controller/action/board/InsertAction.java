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
public class InsertAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UserVo authUser = (UserVo)req.getSession().getAttribute("authUser");

        if (authUser == null) {
            new LoginFormAction().execute(req, res);
            return;
        }

        String title = req.getParameter("title");
        String contents = req.getParameter("content");

        if (title == null || title.isEmpty() || contents == null || contents.isEmpty()) {
            new WriteAction().execute(req, res);
            return;
        }

        String parentPostIdParam = req.getParameter("parentPostId");

        PostVo vo = new PostVo();
        vo.setUserId(authUser.getId());
        vo.setTitle(title);
        vo.setContents(contents);
        vo.setOrderNo(0);

        BoardDao dao = new BoardDao();

        if (parentPostIdParam != null) {
            try {
                Long parentPostId = Long.parseLong(parentPostIdParam);
                PostVo parentVo = dao.findById(parentPostId);

                vo.setDepth(parentVo.getDepth() + 1);
                vo.setGroupNo(parentVo.getGroupNo());
                vo.setOrderNo(parentVo.getOrderNo() + 1);
            } catch (NumberFormatException e) {
                res.sendError(400);
                return;
            }
        }

        PostVo inserted = dao.insert(vo);
        res.sendRedirect(req.getContextPath() + "/board?a=view&post_id=" + inserted.getId());
    }
}
