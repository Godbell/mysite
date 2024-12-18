package mysite.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.dao.GuestBookDao;
import mysite.vo.GuestBookVo;

@WebServlet("/guestbook")
public class GuestBookServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        String action = request.getParameter("a");

        GuestBookDao dao = new GuestBookDao();

        if ("insert".equals(action)) {
            GuestBookVo vo = new GuestBookVo();
            vo.setName(request.getParameter("name"));
            vo.setContents(request.getParameter("content"));
            vo.setPassword(request.getParameter("pass"));

            dao.insert(vo);
            response.sendRedirect(request.getContextPath() + "/guestbook");
        } else if ("deleteform".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/guestbook/deleteform.jsp");
            dispatcher.forward(request, response);
        } else if ("delete".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            String password = request.getParameter("password");

            dao.deleteByIdAndPassword(id, password);
            response.sendRedirect(request.getContextPath() + "/guestbook");
        } else {
            List<GuestBookVo> list = dao.findAll();
            request.setAttribute("list", list);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/guestbook/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        doGet(request, response);
    }
}
