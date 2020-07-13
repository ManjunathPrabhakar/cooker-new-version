Feature: in Need

  Background: norule BG
  bgDesc
    Given no rule
    Then no need rule

  @noRule
  @need
  Scenario: noRuleScenario

    Given Test
    Then Test

  @scoutlineTags
    @need
  Scenario Outline: some scenario name
  some scenario desc
    Given some give
"""xml
some docstring
"""
    When some when "<param>"
    Then some then
      | data |
    And some and
    But some but
    Examples:
      | param          |
      | some parameter |

  @lock

  Scenario Outline: Some new Scenario

    Given nn
    Then tt "<aa>"
    @need
    Examples: Ex Name
      | aa |
      | vv |