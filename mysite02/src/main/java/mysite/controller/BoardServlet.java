package mysite.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.controller.action.board.ListAction;

@WebServlet("/board")
public class BoardServlet extends ActionServlet {
    private final transient Map<String, Action> mapAction = Map.of(
        "list", new ListAction()
    );

    @Override
    protected Action getAction(String actionName) {
        Action action = mapAction.get(actionName);
        return action == null ? mapAction.get("list") : action;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,
        IOException {
        String actionName = Optional.ofNullable(req.getParameter("a")).orElse("");
        getAction(actionName).execute(req, res);
    }
}
