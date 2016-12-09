Feature: Admin users

  Scenario: Try to reach admin endpoint with a non admin user
    Given Exists client and user
    And I call oauth token url with right credentials
    And I call GET "/admin" with authorization
    Then The response status should be 403

  Scenario: Reach admin endpoint with admin user
    Given Exists client and user
    And I call oauth token url with right admin credentials
    And I call GET "/admin" with authorization
    Then The response status should be 200