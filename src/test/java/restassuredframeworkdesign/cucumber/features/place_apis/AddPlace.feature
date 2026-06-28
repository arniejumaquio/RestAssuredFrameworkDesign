Feature: Add Place API
  This is to test add place functionality is working as expected

  Scenario Outline: Verify add place is successful
    Given add place payload from <tcId> and <test_data>
    And add place header
    When <add_place_endpoint> is called with <http> method
    Then status code is <status_code>
    And  response is successful
    Examples:
    | tcId | tc_description                         | test_data   |add_place_endpoint       | http |  status_code |
    | TC1  | Verify add place with all valid data   | functional |/maps/api/place/add/json | POST       | 200 |
    | TC2  | Verify multiple add place              | functional |/maps/api/place/add/json | POST       | 200 |

  Scenario Outline: Verify add place with different valid data and boundary
    Given add place payload from <tcId> and <test_data>
    And add place header
    When <add_place_endpoint> is called with <http> method
    Then status code is <status_code>
    And  response is successful
    Examples:
      | tcId | tc_description                        | test_data                 |add_place_endpoint       | http     |status_code |
      | TC3  | Verify add place with integer lat     | field_validation_positive |/maps/api/place/add/json | POST     | 200 |
      | TC4  | Verify add place with decimal lat     | field_validation_positive |/maps/api/place/add/json | POST     | 200 |

