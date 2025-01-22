package mysite.controller;

import java.io.IOException;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import mysite.service.SiteService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final SiteService siteService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ConfigurableApplicationContext applicationContext;

    public AdminController(
        SiteService siteService,
        ApplicationEventPublisher applicationEventPublisher,
        ConfigurableApplicationContext applicationContext
    ) {
        this.siteService = siteService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.applicationContext = applicationContext;
    }

    @RequestMapping({"", "/", "/main"})
    public String viewMain(Model model) {
        model.addAttribute("siteVo", siteService.getSiteFullInfo());
        return "admin/main";
    }

    @RequestMapping(value = "/main/update", method = RequestMethod.POST)
    public String updateMain(
        @RequestParam("title") String title,
        @RequestParam("welcome") String welcome,
        @RequestParam("description") String description,
        @RequestParam(value = "file", required = false) MultipartFile file,
        HttpServletRequest req
    ) throws IOException {
        siteService.updateSiteInfo(title, welcome, description, file, req);

        applicationEventPublisher.publishEvent(new ContextRefreshedEvent(applicationContext));

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
