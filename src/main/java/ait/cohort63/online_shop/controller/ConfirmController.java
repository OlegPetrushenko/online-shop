package ait.cohort63.online_shop.controller;

import ait.cohort63.online_shop.exception_handling.Response;
import ait.cohort63.online_shop.service.interfaces.ConfirmationCodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/confirm")
public class ConfirmController {

    private final ConfirmationCodeService confirmationCodeService;

    public ConfirmController(ConfirmationCodeService confirmationCodeService) {
        this.confirmationCodeService = confirmationCodeService;
    }

    @GetMapping
    public Response confirmUser(@RequestParam("code") String code) {
        confirmationCodeService.confirmUser(code);
        return new Response("User successfully activated!");
    }
}
