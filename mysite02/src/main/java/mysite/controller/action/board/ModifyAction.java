package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.controller.action.user.LoginFormAction;
import mysite.dao.BoardDao;
import mysite.vo.PostVo;
import mysite.vo.UserVo;
public class ModifyAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String postIdParam = req.getParameter("post_id");

        if (postIdParam == null || postIdParam.isEmpty()) {
            res.sendError(400);
            return;
        }

        UserVo authUser = (UserVo)req.getSession().getAttribute("authUser");
        if (authUser == null) {
            new LoginFormAction().execute(req, res);
            return;
        }

        BoardDao dao = new BoardDao();

        try {
            PostVo vo = dao.findByIdAndUserId(Long.parseLong(postIdParam), authUser.getId());
            req.setAttribute("post", vo);
        } catch (NumberFormatException e) {
            res.sendError(400);
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/modify.jsp");
        dispatcher.forward(req, res);
    }
}
