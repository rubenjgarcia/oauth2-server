package es.rubenjgarcia.oauth2.server.config;

import es.rubenjgarcia.oauth2.server.mongo.client.ClientRepository;
import es.rubenjgarcia.oauth2.server.mongo.client.MongoClientDetailsService;
import es.rubenjgarcia.oauth2.server.mongo.client.MongoClientDetailsServiceBuilder;
import es.rubenjgarcia.oauth2.server.mongo.converter.StringToGrantedAuthority;
import es.rubenjgarcia.oauth2.server.mongo.token.MongoTokenStore;
import es.rubenjgarcia.oauth2.server.mongo.token.TokenRepository;
import es.rubenjgarcia.oauth2.server.mongo.user.MongoUserDetailsManager;
import es.rubenjgarcia.oauth2.server.mongo.user.MongoUserDetailsManagerConfigurer;
import es.rubenjgarcia.oauth2.server.mongo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Profile("mongo")
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "es.rubenjgarcia.oauth2.server.mongo")
@EnableMongoRepositories(basePackages = "es.rubenjgarcia.oauth2.server.mongo")
@EnableAuthorizationServer
public class MongoConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserApprovalHandler userApprovalHandler;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MappingMongoConverter mappingMongoConverter;

    @Bean
    @Autowired
    public TokenStore tokenStore(TokenRepository tokenRepository) {
        return new MongoTokenStore(tokenRepository);
    }

    @Bean
    public MongoClientDetailsService mongoClientDetailsService() {
        return new MongoClientDetailsService(clientRepository);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        MongoClientDetailsServiceBuilder mongoClientDetailsServiceBuilder = new MongoClientDetailsServiceBuilder(mongoClientDetailsService());
        clients.setBuilder(mongoClientDetailsServiceBuilder);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore(tokenRepository))
                .userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager);
    }

    @Bean
    public MongoUserDetailsManager userDetailsManager() {
        return new MongoUserDetailsManager(userRepository);
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth, UserDetailsManager userDetailsManager) throws Exception {
        MongoUserDetailsManagerConfigurer mongoUserDetailsManagerConfigurer = new MongoUserDetailsManagerConfigurer((MongoUserDetailsManager) userDetailsManager);
        auth.apply(mongoUserDetailsManagerConfigurer);
    }

    @PostConstruct
    public void init() {
        CustomConversions conversions = new CustomConversions(Collections.singletonList(new StringToGrantedAuthority()));
        mappingMongoConverter.setCustomConversions(conversions);
        mappingMongoConverter.afterPropertiesSet();
    }
}
