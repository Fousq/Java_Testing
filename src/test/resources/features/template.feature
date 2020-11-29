Feature: Test template generating

  Scenario Outline: Simple template
    Given Provide template: "<template>"
    When Generating of content is required
    Then Content must be "<content>"
    Examples:
      | template | content |
      | Some text: #{value} | Some text: tag |
      | Some #{text}: #{value} | Some text: tag |
      | Some text: #{template.value} | Some text: tag |