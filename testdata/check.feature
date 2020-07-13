Feature: Some Feature Name in TEST
  Some Feature Description
#Language : en

  @need
  Scenario Outline: Some new Scenario

    Given nn
    Then tt "<aa>"
    @need
    Examples: Ex Name
      | aa |
      | vv |
#    @need
#    @excel=root=book.xlsx=Sheet1
#    Examples: Ex Name
#      | aa |
#    Given nn
#    Then tt
#
#
#
#  Rule: Some Rule Name
#  Some Rule Description
#
#    Background: Some background Name
#    some bg description
#      Given When bg step
#      When Bg Step
#        | some data |
#      Then bg then
#
#    @scoutlineTags
#      @need
#    Scenario Outline: some scenario name
#    some scenario desc
#      Given some give
#"""xml
#some docstring
#"""
#      When some when "<param>"
#      Then some then
#        | data |
#      And some and
#      But some but
#      Examples:
#        | param          |
#        | some parameter |
#
#  Rule: some new Rule
#  scome
#
#    @need
#    Scenario: rules
#      Given some an
#      Then some th
