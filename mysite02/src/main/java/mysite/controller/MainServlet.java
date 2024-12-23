package mysite.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.controller.action.main.MainAction;

public class MainServlet extends ActionServlet {

    @Override
    public void init() throws ServletException {
        System.out.println(getServletConfig().getInitParameter("config"));
        super.init();
    }

    @Override
    protected Action getAction(String actionName) {
        return new MainAction();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,
        IOException {
        getAction(req.getParameter("a")).execute(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,
        IOException {
        doGet(req, res);
    }
}
