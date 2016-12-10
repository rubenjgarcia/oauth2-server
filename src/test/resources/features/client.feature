Feature: Clients REST

  Scenario: Try to reach client endpoint with a non admin user
    Given Exists client and user
    And I call oauth token url with right credentials
    And I call GET "/admin/clients/client" with authorization
    Then The response status should be 403

  Scenario: Reach client endpoint with admin user
    Given Exists client and user
    And I call oauth token url with right admin credentials
    And I call GET "/admin/clients/client" with authorization
    Then The response status should be 200
    And The response entity should contains "client_id"
    And The response entity should contains "client_secret"
    And The response entity should contains "authorized_grant_types"
    And The response entity should contains "authorities"
    And The response entity should contains "scope"