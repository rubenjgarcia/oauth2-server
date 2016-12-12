Feature: Clients REST

  Scenario: Try to reach client endpoint with a non admin user
    Given Exists client and user
    And I call oauth token url with right credentials
    When I call GET "/admin/clients/client" with authorization
    Then The response status should be 403

  Scenario: Reach client endpoint with admin user
    Given Exists client and user
    And I call oauth token url with right admin credentials
    When I call GET "/admin/clients/client" with authorization
    Then The response status should be 200
    And The response entity should contains "client_id"
    And The response entity should contains "client_secret"
    And The response entity should contains "authorized_grant_types"
    And The response entity should contains "authorities"
    And The response entity should contains "scope"

  Scenario: Reach client endpoint with admin user with not existing client
    Given Exists client and user
    And I call oauth token url with right admin credentials
    When I call GET "/admin/clients/xxx" with authorization
    Then The response status should be 404
    And The response entity should contains "message" with value "Client not found"

  Scenario: Create client
    Given Exists client and user
    And I call oauth token url with right admin credentials
    When I call POST "/admin/clients" with authorization and with data:
    """
      client_id: "new-client"
      client_secret: "secret"
      authorities: ["ROLE_CLIENT", "ROLE_TRUSTED_CLIENT"]
      authorized_grant_types: ["password", "authorization_code", "refresh_token"]
    """
    Then The response status should be 201

  Scenario: Delete client
    Given Exists client and user
    And I call oauth token url with right admin credentials
    And I call POST "/admin/clients" with authorization and with data:
    """
      client_id: "delete-client"
      client_secret: "secret"
      authorities: ["ROLE_CLIENT", "ROLE_TRUSTED_CLIENT"]
      authorized_grant_types: ["password", "authorization_code", "refresh_token"]
    """
    And I call oauth token url with right admin credentials
    When I call DELETE "/admin/clients/delete-client" with authorization
    Then The response status should be 204

  Scenario: Try to delete not existing client
    Given Exists client and user
    And I call oauth token url with right admin credentials
    When I call DELETE "/admin/clients/xxx" with authorization
    Then The response status should be 404