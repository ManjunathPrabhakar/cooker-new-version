Feature: Some Feature Name in TEST
  Some Feature Description
#Language : en
  Rule: Some Rule Name
  Some Rule Description
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
