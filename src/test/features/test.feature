@E2E1
Feature: Test Feature

  Background: im a background
    Given some five
    Then go fly

#  Rule: some Rule

  @E2E
  Scenario: Test New
    Given hello world
    Then Try me

  @E2E @excel=root=book.xlsx=Sheet1 @tag1 @tag2
  Scenario Outline: Test Scenario EXCEL
    Given Some Given
    When Some When "<MANJU>"
    Then Some Then
    And Some And


    Examples:
      | MANJU |
      | data  |

  @E2E2
  Scenario Outline: Test Scenario New One
    Given Some Given
    When Some When "<MANJU>"
    Then Some Then
    And Some And
    @excel=root=book.xlsx=Sheet1
    Examples:
      | MANJU |
      | data  |

