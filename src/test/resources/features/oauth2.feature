Feature: OAuth2 Server

  Scenario: Try to reach home without token
    Given I call GET "/"
    Then The response status should be 401
    And The response entity should contains "error_description" with value "Full authentication is required to access this resource"

  Scenario: Get access token with grant_type password
    Given Exists client and user
    And I call oauth token url with right credentials
    Then The response status should be 200
    And The response entity should contains "token_type" with value "bearer"
    And The response entity should contains "access_token"
    And The response entity should contains "refresh_token"
    And The response entity should contains "expires_in"
    And The response entity should contains "scope" with value "read write trust"

  Scenario: Try to reach /user/principal without token
    Given I call GET "/me/principal"
    Then The response status should be 401
    And The response entity should contains "error_description" with value "Full authentication is required to access this resource"

  Scenario: Get principal
    Given Exists client and user
    And I call oauth token url with right credentials
    And I call GET "/me/principal" with authorization
    Then The response status should be 200
    And The response entity should contains "details"
    And The response entity should contains "authorities"
    And The response entity should contains "authenticated"
    And The response entity should contains "userAuthentication"
    And The response entity should contains "clientOnly"
    And The response entity should contains "credentials"
    And The response entity should contains "oauth2Request"
    And The response entity should contains "principal"
    And The response entity should contains "name"

  Scenario: Try to reach /user without token
    Given I call GET "/me"
    Then The response status should be 401
    And The response entity should contains "error_description" with value "Full authentication is required to access this resource"

  Scenario: Get user
    Given Exists client and user
    And I call oauth token url with right credentials
    And I call GET "/me" with authorization
    Then The response status should be 200
    And The response entity should contains "password"
    And The response entity should contains "username"
    And The response entity should contains "authorities"
    And The response entity should contains "accountNonExpired"
    And The response entity should contains "accountNonLocked"
    And The response entity should contains "credentialsNonExpired"
    And The response entity should contains "enabled"
