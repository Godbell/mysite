package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import mysite.security.Auth;
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

    @RequestMapping(value = "/joinsuccess", method = RequestMethod.GET)
    public String viewJoinSuccess() {
        return "user/joinsuccess";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String viewLogin() {
        return "user/loginform";
    }

    @Auth
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String viewUpdate(HttpSession session, Model model) {
        UserVo authUser = (UserVo)session.getAttribute("authUser");
        model.addAttribute("authUser", userService.getUser(authUser.getId()));
        return "user/updateform";
    }

    @Auth
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(HttpSession session, UserVo vo) {
        UserVo authUser = (UserVo)session.getAttribute("authUser");

        vo.setId(authUser.getId());
        userService.update(vo);

        authUser.setName(vo.getName());
        authUser.setGender(vo.getGender());

        return "user/updateform";
    }
}
