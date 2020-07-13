@ftags
Feature: Minimal
  feat desc


  Background: SomeBG
  some bg desc

  @stagstest
  Scenario: minimalistic1
    Given the minimalism
      | somedata |
    When the minimalism
    And the minimalism

  @stags
  Scenario: minimalistic2
    Given the minimalism
      | somedata |
    Then the minimalism
       """
      first line (no indent)
        second line (indented with two spaces)

      third line was empty
      """
    When the minimalism
    And the minimalism


  @sotags
  Scenario Outline: minimalistic3
    Given the minimalism "<somedata>"
    Then the minimalism
    When the minimalism
    And the minimalism

    Examples:
      | somedata |
      | data     |

