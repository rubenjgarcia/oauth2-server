package es.rubenjgarcia.oauth2.server.test.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RestSteps {

    @Autowired
    protected TestRestTemplate restTemplate;

    private ResponseEntity<Object> response;

    @Given("^I call GET \"([^\"]*)\"$")
    public void iCallGet(String path) {
        HttpEntity<?> request = new HttpEntity<>(null);
        response = callGet(path, request);
    }

    @And("^I call GET \"([^\"]*)\" with authorization$")
    public void iCallGETWithAuthorization(String path) {
        Map bodyMap = assertResponseContainsKey("access_token");
        String access_token = (String) bodyMap.get("access_token");

        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", Collections.singletonList("Bearer " + access_token));
        HttpEntity<?> request = new HttpEntity<>(headers);
        response = callGet(path, request);
    }

    private ResponseEntity<Object> callGet(String path, HttpEntity<?> request) {
        return restTemplate.exchange(path, HttpMethod.GET, request, Object.class);
    }

    @Given("^I call oauth token url with right credentials$")
    public void iCallOauthTokenUrlWithRightCredentials() {
        callOauthTokenUrlWithCredentials("client", "secret", "user", "mysecretpassword");
    }

    @Given("^I call oauth token url with right admin credentials$")
    public void iCallOauthTokenUrlWithRightAdminCredentials() {
        callOauthTokenUrlWithCredentials("client", "secret", "admin", "mysecretadminpassword");
    }
    
    private void callOauthTokenUrlWithCredentials(String client, String secret, String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        String authorization = Base64.getEncoder().encodeToString((client + ":" + secret).getBytes());
        headers.put("Authorization", Collections.singletonList("Basic " + authorization));

        String path = UriComponentsBuilder.fromPath("/oauth/token")
                .queryParam("grant_type", "password")
                .queryParam("username", username)
                .queryParam("password", password)
                .build()
                .toString();
        HttpEntity<?> request = new HttpEntity<>(headers);
        response = restTemplate.exchange(path, HttpMethod.POST, request, Object.class);
    }
    

    @Then("^The response status should be (\\d+)$")
    public void theResponseStatusShouldBe(int status) {
        assertEquals(status, response.getStatusCodeValue());
    }

    @And("^The response entity should contains \"([^\"]*)\"$")
    public void theResponseEntityShouldContains(String key) {
        assertResponseContainsKey(key);
    }

    @And("^The response entity should contains \"([^\"]*)\" with value \"([^\"]*)\"$")
    public void theResponseEntityShouldContainsWithValue(String key, String value) {
        Map bodyMap = assertResponseContainsKey(key);
        assertEquals(value, bodyMap.get(key));
    }

    private Map assertResponseIsMap() {
        Assert.assertNotNull(response);
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

    @And("^The response entity size should be (\\d+)$")
    public void theResponseEntitySizeShouldBe(int size) throws Throwable {
        Object body = response.getBody();
        Assert.assertNotNull(body);
        assertTrue(List.class.isAssignableFrom(body.getClass()));
        assertEquals(size, ((List) body).size());
    }
}
