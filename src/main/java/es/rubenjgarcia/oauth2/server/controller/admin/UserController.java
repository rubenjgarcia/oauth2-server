package es.rubenjgarcia.oauth2.server.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @RequestMapping("/{user}")
    public UserDetails getUsers(@PathVariable String user) throws IOException {
        UserDetails userDetails = this.userDetailsManager.loadUserByUsername(user);
        return new User(userDetails.getUsername(), "XXX", userDetails.isEnabled(),
                userDetails.isAccountNonExpired(), userDetails.isCredentialsNonExpired(),
                userDetails.isAccountNonLocked(), userDetails.getAuthorities());
    }
}
