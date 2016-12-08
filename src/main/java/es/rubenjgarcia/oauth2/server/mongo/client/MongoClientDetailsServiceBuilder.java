package es.rubenjgarcia.oauth2.server.mongo.client;

import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetailsService;

public class MongoClientDetailsServiceBuilder extends ClientDetailsServiceBuilder<MongoClientDetailsServiceBuilder> {

    private final MongoClientDetailsService mongoClientDetailsService;

    public MongoClientDetailsServiceBuilder(MongoClientDetailsService mongoClientDetailsService) {
        this.mongoClientDetailsService = mongoClientDetailsService;
    }

    @Override
    protected ClientDetailsService performBuild() {
        return this.mongoClientDetailsService;
    }
}
