package ait.cohort63.online_shop.controller;

import ait.cohort63.online_shop.exception_handling.Response;
import ait.cohort63.online_shop.model.dto.UserRegisterDTO;
import ait.cohort63.online_shop.service.interfaces.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    private Response register(@RequestBody UserRegisterDTO userRegisterDTO) {
        userService.registerUser(userRegisterDTO);
        return new Response("Registration complete. Please check your e-mail");
    }
}
