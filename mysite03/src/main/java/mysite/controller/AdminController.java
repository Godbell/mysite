package mysite.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletRequest;
import mysite.security.Auth;
import mysite.service.SiteService;

@Auth(role = "ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final SiteService siteService;

    public AdminController(SiteService siteService) {
        this.siteService = siteService;
    }

    @RequestMapping({"", "/", "/main"})
    public String viewMain() {
        return "admin/main";
    }

    @RequestMapping(value = "/main/update", method = RequestMethod.POST)
    public String updateMain(
        @RequestParam("title") String title,
        @RequestParam("welcome") String welcome,
        @RequestParam("description") String description,
        @RequestParam(value = "file", required = false) MultipartFile file,
        ServletRequest request
    ) throws IOException {
        siteService.updateSiteInfo(
            title, welcome, description, file,
            request.getServletContext().getRealPath("/META-INF/uploads")
        );
        return "redirect:/admin";
    }

    @RequestMapping({"/guestbook"})
    public String viewGuestbook() {
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
