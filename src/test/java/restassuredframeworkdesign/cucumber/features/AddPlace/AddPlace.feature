
  Feature: Add Place Functionality

    @AddPlace
    Scenario Outline: Verify add place API happy paths
      Given add place payload "<lat>" "<lng>" "<accuracy>" "<name>" "<phone_number>" "<address>" "<types>" "<website>" "<language>"
      When user calls "AddPlaceAPI" with "POST"  request
      Then the  status code is "200"
      And "status" in response body is "OK"
      And "scope" in response body is "APP"
      And response body contains "place_id"
      And response body contains "reference"
      And response body contains "id"
      And verify all details "<lat>" "<lng>" "<accuracy>" "<name>" "<phone_number>" "<address>" "<types>" "<website>" "<language>" are added in "GetPlaceAPI" with "GET"  request

      Examples:
      | lat | lng | accuracy| name | phone_number | address | types | website | language |
      # Test Case 1: Verify Add Place API with Valid Data
      | -89.383494| 103.427362 | 160    | Purokyot House Of Collab | (+91) 983 893 3937 | 29, side layout, cohen 09 | shoe park,shop | http://youtube.com | Tagalog |
       # Test Case 15: Verify Add Place API with Special Characters in Name
      | -89.383494| 120.427362 | 160    | Test@Place#123! | (+91) 983 893 3937 | 29, side layout, cohen 09 | shoe park,shop | http://youtube.com | Chavacano |


    @DeletePlace
    Scenario: Verify delete place API happy path
      Given delete place payload
      When user calls "DeletePlaceAPI" with "DELETE"  request
      Then the  status code is "200"
      And "status" in response body is "OK"