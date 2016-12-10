package es.rubenjgarcia.oauth2.server.controller.admin.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin/clients")
public class ClientController {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @RequestMapping("/{client}")
    public ClientDetails getClient(@PathVariable String client) {
        return this.clientDetailsService.loadClientByClientId(client);
    }
}
