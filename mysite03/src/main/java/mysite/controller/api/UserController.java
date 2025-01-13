package mysite.controller.api;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mysite.dto.JsonResult;
import mysite.service.UserService;

@RequestMapping("/api/user")
@RestController("userRestController")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/checkemail")
    public JsonResult checkEmail(@RequestParam(value = "email") String email) {
        return JsonResult.success(
            Map.of("availability", userService.getEmailAvailability(email)),
            null
        );
    }
}