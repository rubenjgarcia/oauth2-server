package es.rubenjgarcia.oauth2.server;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableResourceServer
@ComponentScan(basePackages = {"es.rubenjgarcia.oauth2.server.config", "es.rubenjgarcia.oauth2.server.controller"})
public class OAuthServer2Application {

    //TODO Filter refresh_token in log

    public static void main(String[] args) {
        SpringApplication.run(OAuthServer2Application.class, args);
    }
}
