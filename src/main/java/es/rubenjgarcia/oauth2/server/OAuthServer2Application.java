package es.rubenjgarcia.oauth2.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OAuthServer2Application {

    //TODO Filter refresh_token in log

    public static void main(String[] args) {
        SpringApplication.run(OAuthServer2Application.class, args);
    }
}
