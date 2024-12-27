package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import mysite.service.UserService;
import mysite.vo.UserVo;

@RequestMapping("/user")
@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String viewJoin() {
        return "user/joinform";
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String join(UserVo vo) {
        userService.createUser(vo);
        return "redirect:/user/joinsuccess";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String viewLogin() {
        return "user/loginform";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(UserVo userVo, Model model, HttpSession session) {
        UserVo authUser = userService.getUser(userVo.getEmail(), userVo.getPassword());

        if (authUser == null) {
            model.addAttribute("email", userVo.getEmail());
            model.addAttribute("result", "fail");
            return "user/loginform";
        }

        session.setAttribute("authUser", authUser);
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("authUser");
        session.invalidate();
        
        return "redirect:/";
    }
}
