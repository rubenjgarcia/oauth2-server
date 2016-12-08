package es.rubenjgarcia.oauth2.server.mongo.token;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

    List<Token> findByClient(String client);
    List<Token> findByClientAndUser(String client, String user);
    Token findByClientAndExpirationAfter(String client, Date expiresInAfter);
}
