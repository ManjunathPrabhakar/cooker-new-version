@someFeature
Feature: Lorem ispum feature name
  lorem ipsum feature description
  and some thing else
#Language : en

  Background: Lorem ipsum background
  execute prior to every scenario

  Rule: someRule
  new Rule

    Background: Lorem ipsum background
    execute prior to every scenario

    @docStringWithExample
    Scenario Outline: Greetings come in many forms

      Given this file:
"""<type>
Greeting:<content>
"""
      @test
      Examples:
        | type | content |
        | en   | Hello   |
        | fr   | Bonjour |
