Feature: Accounts can be queried
  Scenario: Get a single account
    When client makes a get on /accounts
    Then the client receives status code of 200
    And the client receives a bank account