package mysite.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.Action;
import mysite.controller.action.guestbook.DeleteAction;
import mysite.controller.action.guestbook.DeleteFormAction;
import mysite.controller.action.guestbook.InsertAction;
import mysite.controller.action.guestbook.ListAction;

@WebServlet("/guestbook")
public class GuestBookServlet extends ActionServlet {
    private final transient Map<String, Action> mapAction = Map.of(
        "insert", new InsertAction(),
        "deleteform", new DeleteFormAction(),
        "delete", new DeleteAction(),
        "list", new ListAction()
    );

    @Override
    protected Action getAction(String actionName) {
        Action action = mapAction.get(actionName);
        return action == null ? mapAction.get("list") : action;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        String actionName = Optional.ofNullable(request.getParameter("a")).orElse("");
        getAction(actionName).execute(request, response);
    }
}
