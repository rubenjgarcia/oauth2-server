package es.rubenjgarcia.oauth2.server.test;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RestSteps extends BaseTest {

    private ResponseEntity<Object> response;

    @Given("^I call GET \"([^\"]*)\"$")
    public void iCallGet(String path) throws Throwable {
        HttpEntity<?> request = new HttpEntity<>(null);
        response = restTemplate.exchange(path, HttpMethod.GET, request, Object.class);
    }

    @Given("^I call oauth token url with right credentials$")
    public void iCallOauthTokenUrlWithRightCredentials() throws Throwable {
        HttpHeaders headers = new HttpHeaders();
        String authorization = Base64.getEncoder().encodeToString("client:secret".getBytes());
        headers.put("Authorization", Collections.singletonList("Basic " + authorization));

        String path = UriComponentsBuilder.fromPath("/oauth/token")
                .queryParam("grant_type", "password")
                .queryParam("username", "user")
                .queryParam("password", "mysecretpassword")
                .build()
                .toString();
        HttpEntity<?> request = new HttpEntity<>(headers);
        response = restTemplate.exchange(path, HttpMethod.POST, request, Object.class);
    }

    @Then("^The response status should be (\\d+)$")
    public void theResponseStatusShouldBe(int status) throws Throwable {
        assertEquals(status, response.getStatusCodeValue());
    }

    @And("^The response entity should contains \"([^\"]*)\"$")
    public void theResponseEntityShouldContains(String key) throws Throwable {
        assertResponseContainsKey(key);
    }

    @And("^The response entity should contains \"([^\"]*)\" with value \"([^\"]*)\"$")
    public void theResponseEntityShouldContainsWithValue(String key, String value) throws Throwable {
        Map bodyMap = assertResponseContainsKey(key);
        assertEquals(value, bodyMap.get(key));
    }

    private Map assertResponseIsMap() {
        Object body = response.getBody();
        Assert.assertNotNull(body);
        assertTrue(Map.class.isAssignableFrom(body.getClass()));
        return (Map) response.getBody();
    }

    private Map assertResponseContainsKey(String key) {
        return assertResponseContainsOrNotKey(key, true);
    }

    private Map assertResponseContainsOrNotKey(String key, boolean shouldContains) {
        Map bodyMap = assertResponseIsMap();
        boolean containsKey = bodyMap.containsKey(key);
        assertEquals(containsKey, shouldContains);
        return bodyMap;
    }
}
