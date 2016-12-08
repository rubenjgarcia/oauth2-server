package es.rubenjgarcia.oauth2.server.test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/site/cucumber"},
        glue = {"es.rubenjgarcia.oauth2.server.test.mongo", "es.rubenjgarcia.oauth2.server.test.steps"},
        features = "classpath:features",
        strict = true)
public class MongoCucumberTestCase {

}
