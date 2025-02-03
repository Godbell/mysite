package mysite.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.dto.JsonResult;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Log log = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(exception = {Exception.class})
    public void handler(
        HttpServletRequest req, HttpServletResponse res, Exception e
    ) throws IOException, ServletException {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        log.error(stringWriter);

        String accept = req.getHeader("accept");

        if (accept.matches(".*application/json.*")) {
            JsonResult jsonResult = JsonResult.fail(e.getMessage());
            String jsonString = new ObjectMapper().writeValueAsString(jsonResult);

            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json; charset=utf-8");

            OutputStream outputStream = res.getOutputStream();
            outputStream.write(jsonString.getBytes());
            outputStream.close();
        } else {
            req.setAttribute("errors", stringWriter.toString());

            if (Arrays.stream(new int[] {400, 404, 500}).anyMatch((val) -> val == res.getStatus())) {
                req.getRequestDispatcher("/error/" + res.getStatus()).forward(req, res);
            } else {
                req.getRequestDispatcher("/error").forward(req, res);
            }
        }
    }
}
