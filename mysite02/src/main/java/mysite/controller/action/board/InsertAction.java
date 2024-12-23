package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;
public class InsertAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UserVo authUser = (UserVo)req.getSession().getAttribute("authUser");

        if (authUser == null) {
            /* HANDLE UNAUTHORIZED */
            return;
        }

        BoardVo vo = new BoardVo();
        vo.setUserId(authUser.getId());
        vo.setTitle(req.getParameter("title"));
        vo.setContents(req.getParameter("content"));

        String parentPostIdParam = req.getParameter("parentPostId");
        BoardDao dao = new BoardDao();

        if (parentPostIdParam != null) {
            Long parentPostId = Long.parseLong(parentPostIdParam);
            BoardVo parentVo = dao.findById(parentPostId);

            vo.setDepth(parentVo.getDepth() + 1);
            vo.setGroupNo(parentVo.getGroupNo());
        }

        BoardVo inserted = dao.insert(vo);
        res.sendRedirect(req.getContextPath() + "/board?a=view&post_id=" + inserted.getId());
    }
}
