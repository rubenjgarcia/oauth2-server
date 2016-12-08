package es.rubenjgarcia.oauth2.server.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("")
    public User user(@AuthenticationPrincipal User user) {
        return user;
    }

    @RequestMapping("/principal")
    public Principal principal(Principal principal) {
        return principal;
    }
}
