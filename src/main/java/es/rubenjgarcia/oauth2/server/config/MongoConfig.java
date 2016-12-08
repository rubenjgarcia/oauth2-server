package es.rubenjgarcia.oauth2.server.config;

import es.rubenjgarcia.oauth2.server.mongo.client.ClientRepository;
import es.rubenjgarcia.oauth2.server.mongo.client.MongoClientDetailsService;
import es.rubenjgarcia.oauth2.server.mongo.client.MongoClientDetailsServiceBuilder;
import es.rubenjgarcia.oauth2.server.mongo.token.MongoTokenStore;
import es.rubenjgarcia.oauth2.server.mongo.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Profile("mongo")
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "es.rubenjgarcia.oauth2.server.mongo")
@EnableMongoRepositories(basePackages = "es.rubenjgarcia.oauth2.server.mongo")
@EnableAuthorizationServer
public class MongoConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        MongoClientDetailsService mongoClientDetailsService = new MongoClientDetailsService(clientRepository);
        MongoClientDetailsServiceBuilder mongoClientDetailsServiceBuilder = new MongoClientDetailsServiceBuilder(mongoClientDetailsService);
        clients.setBuilder(mongoClientDetailsServiceBuilder);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new MongoTokenStore(tokenRepository))
                .authenticationManager(authenticationManager);
    }
}
