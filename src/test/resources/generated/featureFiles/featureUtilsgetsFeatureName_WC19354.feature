
Feature: Some Feature Name in TEST
  Some Feature Description
#Language : en

Background: Some background Name
    some bg description
Given When bg step
When Bg Step
|some data|
Then bg then

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
|data|
And some and
But some but
Examples: 

|param|
|some parameter|
