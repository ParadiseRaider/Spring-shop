Feature: Login

  Scenario Outline: Successful Login to the page and logout after
    Given I open web browser
    When I navigate to login page
    And I provide username as "<username>" and password as "<password>"
    And I click on login button
    When I click roles
    And I click new roles
    And I enter role name as "<rolename>"
    And I click on submit button
    Then Table roles is create
    Then name should be "<name>"
    When Open dropdown menu
    And click logout button
    Then user logged out

    Examples:
      | username | password | rolename | name |
      | admin    | admin    | tester   | admin |



