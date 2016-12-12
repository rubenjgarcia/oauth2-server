package es.rubenjgarcia.oauth2.server.controller.admin.user;

import es.rubenjgarcia.oauth2.server.controller.admin.user.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @RequestMapping(path = "/{user}", method = RequestMethod.GET)
    public UserDetails getUser(@PathVariable String user) throws IOException {
        UserDetails userDetails = this.userDetailsManager.loadUserByUsername(user);
        return new User(userDetails.getUsername(), "XXX", userDetails.isEnabled(),
                userDetails.isAccountNonExpired(), userDetails.isCredentialsNonExpired(),
                userDetails.isAccountNonLocked(), userDetails.getAuthorities());
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid UserRequest user) throws IOException {
        this.userDetailsManager.createUser(new User(user.getUsername(), user.getPassword(), user.getGrantedAuthorities()));
    }
}
