package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;
import mysite.security.Auth;
import mysite.security.AuthUser;
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
    public String viewJoin(
        @ModelAttribute("user") UserVo userVo
    ) {
        return "user/joinform";
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String join(
        @Valid @ModelAttribute("user") UserVo vo, BindingResult result, Model model
    ) {
        if (result.hasErrors()) {
            model.addAllAttributes(result.getModel());
            return "/user/joinform";
        }

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
    public String viewUpdate(@AuthUser UserVo authUser, Model model) {
        model.addAttribute("authUser", userService.getUser(authUser.getId()));
        return "user/updateform";
    }

    @Auth
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@AuthUser UserVo authUser, UserVo vo) {
        vo.setId(authUser.getId());
        userService.update(vo);

        authUser.setName(vo.getName());
        authUser.setGender(vo.getGender());

        return "user/updateform";
    }

    @RequestMapping("/auth")
    public void auth() {
    }

    @RequestMapping("/logout")
    public void logout() {
    }
}
