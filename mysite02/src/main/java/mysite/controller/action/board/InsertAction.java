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
        vo.setContents(req.getParameter("contents"));
        vo.setDepth(Integer.parseInt(req.getParameter("depth")));
        vo.setGroupNo(Integer.parseInt(req.getParameter("g_no")));

        BoardDao dao = new BoardDao();
        dao.insert(vo);
    }
}
