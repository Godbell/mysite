package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import mysite.security.Auth;

@Auth(role = "ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping({"", "/", "/main"})
    public String viewMain() {
        return "admin/main";
    }

    @RequestMapping({"/guestbook"})
    public String viewGestbook() {
        return "admin/guestbook";
    }

    @RequestMapping({"/board"})
    public String viewBoard() {
        return "admin/board";
    }

    @RequestMapping({"/user"})
    public String viewUser() {
        return "admin/user";
    }
}
