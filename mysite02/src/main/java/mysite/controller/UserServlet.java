package mysite.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.dao.UserDao;
import mysite.vo.UserVo;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("a");

        UserDao dao = new UserDao();

        if ("joinform".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/joinform.jsp");
            dispatcher.forward(request, response);
        } else if ("join".equals(action)) {
            UserVo vo = new UserVo();
            vo.setName(request.getParameter("name"));
            vo.setEmail(request.getParameter("email"));
            vo.setPassword(request.getParameter("password"));
            vo.setGender(request.getParameter("gender"));

            dao.insert(vo);
            response.sendRedirect(request.getContextPath() + "/user?a=joinsuccess");
        } else if ("joinsuccess".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/joinsuccess.jsp");
            dispatcher.forward(request, response);
        } else if ("loginform".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        doGet(request, response);
    }
}
