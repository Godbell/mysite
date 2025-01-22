package mysite.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WhiteLabelErrorController implements ErrorController {
    @RequestMapping("400")
    public String badRequest() {
        return "errors/400";
    }

    @RequestMapping("404")
    public String notFound() {
        return "errors/404";
    }

    @RequestMapping("500")
    public String internal() {
        return "errors/500";
    }
}
