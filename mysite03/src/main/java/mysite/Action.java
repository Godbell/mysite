package mysite;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Action {
    void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;
}