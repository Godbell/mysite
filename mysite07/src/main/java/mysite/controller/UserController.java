package mysite.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import mysite.service.UserService;
import mysite.vo.UserVo;

@RequestMapping("/user")
@AllArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String viewJoin(
        @ModelAttribute("user") UserVo userVo
    ) {
        return "user/join";
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String join(
        @Valid @ModelAttribute("user") UserVo vo, BindingResult result, Model model
    ) {
        if (result.hasErrors()) {
            model.addAllAttributes(result.getModel());
            return "user/join";
        }

        userService.createUser(vo);
        return "redirect:/user/joinsuccess";
    }

    @RequestMapping(value = "/joinsuccess", method = RequestMethod.GET)
    public String viewJoinSuccess() {
        return "user/joinsuccess";
    }

    @RequestMapping(value = "/login")
    public String viewLogin() {
        return "user/login";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Authentication authentication, Model model) {
        UserVo user = (UserVo)authentication.getPrincipal();
        model.addAttribute("user", user);

        return "user/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Authentication authentication, UserVo vo, Model model) {
        UserVo user = (UserVo)authentication.getPrincipal();

        vo.setId(user.getId());
        userService.update(vo);

        user.setName(vo.getName());
        user.setGender(vo.getGender());

        model.addAttribute("user", user);
        return "redirect:/user/update";
    }

    @RequestMapping("/auth")
    public void auth() {
    }

    @RequestMapping("/logout")
    public void logout() {
    }
}
