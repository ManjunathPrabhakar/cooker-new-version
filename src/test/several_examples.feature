Feature: Tagged Examples

  Background: Some Rule

  Scenario Outline: minimalistic
    Given the <what>

    @foo
    Examples:
      | what |
      | foo  |

    @bar
    Examples: for manju
      | what |
      | bar  |

    Example: some example
      Given some data


  Rule: some NRe
  help rule
    Background: BGG
    @zap
    Scenario: ha ok
