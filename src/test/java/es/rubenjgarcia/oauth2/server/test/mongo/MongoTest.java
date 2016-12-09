package es.rubenjgarcia.oauth2.server.test.mongo;

import com.github.fakemongo.Fongo;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;
import cucumber.api.java.en.Given;
import es.rubenjgarcia.oauth2.server.OAuthServer2Application;
import es.rubenjgarcia.oauth2.server.mongo.client.Client;
import es.rubenjgarcia.oauth2.server.mongo.client.ClientRepository;
import es.rubenjgarcia.oauth2.server.mongo.user.User;
import es.rubenjgarcia.oauth2.server.mongo.user.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {OAuthServer2Application.class, MongoTest.TestConfig.class})
@ActiveProfiles({"test, mongo"})
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @TestConfiguration
    public static class TestConfig extends AbstractMongoConfiguration {

        private static final Fongo FONGO = new Fongo("mongo-test");

        @Override
        public String getDatabaseName() {
            return "testdb";
        }

        @Override
        @Bean
        public Mongo mongo() {
            return FONGO.getMongo();
        }
    }

    @Given("^Exists client and user$")
    public void existsClientAndUser() throws Throwable {
        saveFromJson("/data/client.json", Client.class, clientRepository);
        saveFromJson("/data/user.json", User.class, userRepository);
    }

    private <T> void saveFromJson(String path, Class<T> modelClazz, MongoRepository<T, ? extends Serializable> repository) throws IOException {
        String json = IOUtils.toString(MongoTest.class.getResourceAsStream(path));
        DBObject dbObject = (DBObject) JSON.parse(json);

        if (List.class.isAssignableFrom(dbObject.getClass())) {
            List<T> entities = ((List<DBObject>) dbObject).stream()
                    .map(o -> mongoTemplate.getConverter().read(modelClazz, o))
                    .collect(Collectors.toList());
            repository.save(entities);
        } else {
            T entity = mongoTemplate.getConverter().read(modelClazz, dbObject);
            repository.save(entity);
        }
    }
}
