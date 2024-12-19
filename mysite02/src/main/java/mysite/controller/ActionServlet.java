package mysite.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public abstract class ActionServlet extends HttpServlet {
    protected abstract Action getAction(String actionName);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String actionName = req.getParameter("a");

        Action action = getAction(actionName);
        action.execute(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }

    public static interface Action {
        void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;
    }
}