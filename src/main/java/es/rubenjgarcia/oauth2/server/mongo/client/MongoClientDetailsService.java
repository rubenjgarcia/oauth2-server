package es.rubenjgarcia.oauth2.server.mongo.client;

import org.springframework.security.oauth2.provider.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MongoClientDetailsService implements ClientDetailsService, ClientRegistrationService {

    private ClientRepository clientRepository;

    public MongoClientDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return Optional.ofNullable(clientRepository.findOne(clientId))
                .orElseThrow(() -> new ClientRegistrationException("error.clientNotFoundError"));
    }

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        this.clientRepository.save(new Client(clientDetails));
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
        if (clientRepository.findOne(clientId) != null) {
            this.clientRepository.delete(clientId);
        } else {
            throw new NoSuchClientException("error.clientNotFoundError");
        }
    }

    @Override
    public List<ClientDetails> listClientDetails() {
        return new ArrayList<>(clientRepository.findAll());
    }
}