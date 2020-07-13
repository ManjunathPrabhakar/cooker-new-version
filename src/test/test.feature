@featureTags
Feature: Some Feature Name in TEST
  Some Feature Description

  Background: norule BG
  bgDesc
    Given no rule
    Then no need rule

  @noRule @need
  Scenario: noRuleScenario
    Given Test
    Then Test

  @noRule @SO
  Scenario Outline: some scenario name
  some scenario desc
    Given some give
        """xml
        some docstring
        """
    When some when "<param>"

    Examples:
      | param          |
      | some parameter |

# #rule cannot have tags
#  Rule: Some Rule Name
#  Some Rule Description
#
#    #background cannot have tags
#    Background: Some background Name
#    some bg description
#      Given When bg step
#      When Bg Step
#        | some data |
#      Then bg then

  @sctags
  Scenario: some scenario
    Given some fiven


  @scoutlineTags @need
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

#    Example: some examplename
#      Given some data
#      Then some data

  @lock
  Scenario Outline: Some new Scenario
    Given nn
    Then tt "<aa>"

    @need
    Examples: Ex Name
      | aa |
      | vv |

    @noneed
    Examples: Ex Name1
      | aa |
      | jj |


