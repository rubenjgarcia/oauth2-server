package es.rubenjgarcia.oauth2.server.memory;

import org.springframework.security.oauth2.provider.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryClientDetailsService implements ClientDetailsService, ClientRegistrationService {

    private Map<String, ClientDetails> clientDetailsStore = new HashMap<>();

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return Optional.ofNullable(clientDetailsStore.get(clientId))
                .orElseThrow(() -> new ClientRegistrationException("error.clientNotFoundError"));
    }

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        if (this.clientDetailsStore.containsKey(clientDetails.getClientId())) {
            throw new ClientAlreadyExistsException("Client already exists: " + clientDetails.getClientId());
        }

        this.clientDetailsStore.put(clientDetails.getClientId(), clientDetails);
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        if (clientDetailsStore.containsKey(clientId)) {
            clientDetailsStore.remove(clientId);
        } else {
            throw new NoSuchClientException("error.clientNotFoundError");
        }
    }

    @Override
    public List<ClientDetails> listClientDetails() {
        throw new NotImplementedException(); //TODO
    }
}
