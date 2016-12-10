package es.rubenjgarcia.oauth2.server.controller.admin.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin/clients")
public class ClientController {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private ClientRegistrationService clientRegistrationService;

    @RequestMapping("/{client}")
    public ClientDetails getClient(@PathVariable String client) {
        return this.clientDetailsService.loadClientByClientId(client);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid BaseClientDetails client) throws IOException {
        this.clientRegistrationService.addClientDetails(client);
    }
}
