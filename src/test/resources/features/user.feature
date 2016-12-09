Feature: Users REST

  Scenario: Try to reach user endpoint with a non admin user
    Given Exists client and user
    And I call oauth token url with right credentials
    And I call GET "/admin/users/user" with authorization
    Then The response status should be 403

  Scenario: Reach user endpoint with admin user
    Given Exists client and user
    And I call oauth token url with right admin credentials
    And I call GET "/admin/users/user" with authorization
    Then The response status should be 200
    And The response entity should contains "username" with value "user"
    And The response entity should contains "password" with value "XXX"

  Scenario: Reach user endpoint with admin user with not existing user
    Given Exists client and user
    And I call oauth token url with right admin credentials
    And I call GET "/admin/users/xxx" with authorization
    Then The response status should be 404
    And The response entity should contains "message" with value "User not found"

  Scenario: Create user
    Given Exists client and user
    And I call oauth token url with right admin credentials
    And I call POST "/admin/users" with authorization and with data:
    """
      username: "perry"
      password: "platypus"
      authorities: ["USER"]
    """
    Then The response status should be 201


  Scenario Outline: Error trying to create user
    Given Exists client and user
    And I call oauth token url with right admin credentials
    And I call POST "/admin/users" with authorization and with data:
    """
      username: <username>
      password: <password>
      authorities: [<authorities>]
    """
    Then The response status should be 400

    Examples:

      | username | password   | authorities |
      | "perry"  | "platypus" |             |
      | "perry"  |            | "USER"      |
      |          | "platypus" | "USER"      |