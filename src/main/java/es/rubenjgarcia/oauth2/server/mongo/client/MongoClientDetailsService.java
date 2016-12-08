package es.rubenjgarcia.oauth2.server.mongo.client;

import org.springframework.security.oauth2.provider.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class MongoClientDetailsService implements ClientDetailsService, ClientRegistrationService {

    private ClientRepository clientRepository;

    public MongoClientDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return clientRepository.findOne(clientId);
    }

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        throw new NotImplementedException(); //TODO
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
        throw new NotImplementedException(); //TODO
    }

    @Override
    public List<ClientDetails> listClientDetails() {
        return new ArrayList<>(clientRepository.findAll());
    }
}