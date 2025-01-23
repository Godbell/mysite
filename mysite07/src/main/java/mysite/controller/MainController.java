package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import mysite.service.SiteService;
import mysite.vo.SiteVo;
import mysite.vo.UserVo;

@Controller
@AllArgsConstructor
public class MainController {
    private final SiteService siteService;

    @RequestMapping({"", "/", "/main"})
    public String index(Model model, HttpServletRequest request) {
        SiteVo siteVo = siteService.getSiteFullInfo();
        model.addAttribute("siteVo", siteVo);
        model.addAttribute("lineSeparator", System.lineSeparator());

        return "main/index";
    }

    @ResponseBody
    @RequestMapping("/msg01")
    public String message01() {
        return "Hello World!";
    }

    @ResponseBody
    @RequestMapping("/msg02")
    public String message02() {
        return "안녕";
    }

    @ResponseBody
    @RequestMapping("/msg03")
    public Object message03() {
        UserVo vo = new UserVo();
        vo.setId(10L);
        vo.setName("둘리");
        vo.setEmail("dooly@gmail.com");
        return vo;
    }
}