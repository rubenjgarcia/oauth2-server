package es.rubenjgarcia.oauth2.server.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/me")
public class MeController {

    @RequestMapping("")
    public UserDetails user(@AuthenticationPrincipal(errorOnInvalidType = true) UserDetails user) {
        return user;
    }

    @RequestMapping("/principal")
    public Principal principal(Principal principal) {
        return principal;
    }
}
