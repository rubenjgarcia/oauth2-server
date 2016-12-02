package es.rubenjgarcia.oauth2.server.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BaseTest.TestConfig.class})
@ActiveProfiles("test")
public abstract class BaseTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @TestConfiguration
    public static class TestConfig {

    }
}
