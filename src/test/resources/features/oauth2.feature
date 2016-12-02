Feature: OAuth2 Server

  Scenario: Try to reach home without token
    Given I call GET "/"
    Then The response status should be 401
    And The response entity should contains "message" with value "Full authentication is required to access this resource"

  Scenario: Get access token with grant_type password
    Given I call oauth token url with right credentials
    Then The response status should be 200
    And The response entity should contains "token_type" with value "bearer"
    And The response entity should contains "access_token"
    And The response entity should contains "refresh_token"
    And The response entity should contains "expires_in"
    And The response entity should contains "scope" with value "read write trust"
