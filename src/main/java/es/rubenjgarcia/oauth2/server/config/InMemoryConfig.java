package es.rubenjgarcia.oauth2.server.config;

import es.rubenjgarcia.oauth2.server.memory.InMemoryClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.Arrays;
import java.util.Collections;

@Profile({"default", "inmemory"})
@Configuration
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, MongoRepositoriesAutoConfiguration.class})
@EnableAuthorizationServer
public class InMemoryConfig extends AuthorizationServerConfigurerAdapter {

    private TokenStore tokenStore = new InMemoryTokenStore();

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserApprovalHandler userApprovalHandler;

    private InMemoryClientDetailsService inMemoryClientDetailsService = new InMemoryClientDetailsService();

    @Bean
    public TokenStore tokenStore() {
        return this.tokenStore;
    }

    @Bean
    public InMemoryClientDetailsService inMemoryClientDetailsService() {
        return this.inMemoryClientDetailsService;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        BaseClientDetails client = new BaseClientDetails();
        client.setClientId("client");
        client.setClientSecret("secret");
        client.setScope(Arrays.asList("read", "write", "trust"));
        client.setAuthorizedGrantTypes(Arrays.asList("password", "authorization_code", "refresh_token"));
        client.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_CLIENT"), new SimpleGrantedAuthority("ROLE_TRUSTED_CLIENT")));
        this.inMemoryClientDetailsService.addClientDetails(client);

        clients.withClientDetails(this.inMemoryClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager);
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        InMemoryUserDetailsManagerConfigurer inMemoryUserDetailsManagerConfigurer = new InMemoryUserDetailsManagerConfigurer();
        UserDetailsManager userDetailsManager = (UserDetailsManager) inMemoryUserDetailsManagerConfigurer.getUserDetailsService();
        userDetailsManager.createUser(new User("user", "mysecretpassword", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
        userDetailsManager.createUser(new User("admin", "mysecretadminpassword", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"))));
        return userDetailsManager;
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth, UserDetailsManager userDetailsManager) throws Exception {
        auth.userDetailsService(userDetailsManager);
    }
}
