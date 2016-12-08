package es.rubenjgarcia.oauth2.server.test.memory;

import cucumber.api.java.en.Given;
import es.rubenjgarcia.oauth2.server.OAuthServer2Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {OAuthServer2Application.class})
@ActiveProfiles({"test, inmemory"})
public class InMemoryTest {

    @Given("^Exists client and user$")
    public void existsClientAndUser() throws Throwable {

    }
}
