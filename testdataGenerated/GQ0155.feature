@someFeature
Feature: Lorem ispum feature name
  lorem ipsum feature description
  and some thing else
#Language : en
Background: Lorem ipsum background
  execute prior to every scenario


@someScenario
Scenario: Lorem ipsum Scenario
  scenario desc
Given lorem ipsum given
When foo

@docStringWithExample
Scenario Outline: Greetings come in many forms

Given this file:
"""<type>
Greeting:<content>
"""
@test
Examples: 
|type|content|
|en|Hello|
|fr|Bonjour|

@someScenariowithDocstring
Scenario: minimalistic

Given a simple DocString
"""
first line (no indent)
  second line (indented with two spaces)

third line was empty
"""
Given a DocString with content type
"""xml
<foo>
  <bar />
</foo>
"""
And a DocString with wrong indentation
"""
wrongly indented line
"""
And a DocString with alternative separator
```
first line
second line
```
And a DocString with normal separator inside
```
first line
"""
    third line
```
And a DocString with alternative separator inside
"""
first line
```
third line
"""
And a DocString with escaped separator inside
"""
first line
"""
third line
"""
And a DocString with an escaped alternative separator inside
```
first line
```
third line
```

