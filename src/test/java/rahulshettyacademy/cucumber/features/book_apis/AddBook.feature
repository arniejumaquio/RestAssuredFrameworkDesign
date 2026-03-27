
  Feature: Add Book Functionality
    This is to test Add Book API

      # ============================================
      # FUNCTIONAL TESTS
      # ============================================

    Scenario Outline: Verify successful add  book  <test_case_description>
      Given add book payload for <testCaseId> from <testData>
      And   add book header
      When  call to <addbookendpoint> with <httpmethod> request
      Then  status code is <statuscode>
      And   success response is displayed
      Examples:
      | testCaseId | test_case_description  | addbookendpoint | httpmethod | statuscode | testData|
      | TC1 | Verify add book api with valid data| /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json|
      | TC2 | Verify add book api with duplicate data |   /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json|

      # ============================================
      # FIELD VALIDATION - POSITIVE & EDGE CASES
      # ============================================
      # name 1-50
      # isbn 5-15
      # aisle 5-15
      # author 1-50

    Scenario Outline: Verify add book with valid field variations and boundary values <test_case_description>
      Given add book payload for <testCaseId> from <testData>
      And   add book header
      When  call to <addbookendpoint> with <httpmethod> request
      Then  status code is <statuscode>
      And   success response is displayed
      Examples:
        | testCaseId | test_case_description |   addbookendpoint | httpmethod | statuscode | testData|
        |TC3 |Verify add book api with special characters in name|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC4 |Verify add book api with alphanumeric in name|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC5 |Verify add book api with aphabet in name|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC6 |Verify add book api with numeric in name|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC7 |Verify add book api with special characters in isbn|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC8 |Verify add book api with alphanumeric in isbn|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC9 |Verify add book api with aphabet in isbn|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC10 |Verify add book api with numeric in isbn|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC11 |Verify add book api with special characters in aisle|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC12 |Verify add book api with alphanumeric in aisle|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC13 |Verify add book api with aphabet in aisle|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC14 |Verify add book api with numeric in aisle|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC15 |Verify add book api with special characters in author|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC16 |Verify add book api with alphanumeric in author|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC17 |Verify add book api with aphabet in author|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC18 |Verify add book api with numeric in author|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC19 |Verify add book api with minimum length in name|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC20 |Verify add book api with maximum length in name|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC21 |Verify add book api with minimum length in isbn|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC22 |Verify add book api with maximum length in isbn|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC23 |Verify add book api with minimum length in aisle|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC24 |Verify add book api with maximum length in aisle|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC25 |Verify add book api with minimum length in author|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
        |TC26 |Verify add book api with maximum length in author|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |

      # ============================================
      # FIELD VALIDATION - NEGATIVE CASES
      # ============================================
      Scenario Outline: Verify add book with invalid field variations and boundary values <test_case_description>
        Given add book payload for <testCaseId> from <testData>
        And   add book header
        When  call to <addbookendpoint> with <httpmethod> request
        Then  status code is <statuscode>
        And   error response is displayed
        Examples:
          | testCaseId | test_case_description |   addbookendpoint | httpmethod | statuscode | testData|
          |  TC27      | Verify add book api with empty name | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC28      | Verify add book api with empty isbn | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC29      | Verify add book api with empty aisle | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC30      | Verify add book api with empty author | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC31      | Verify add book api with missing name | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC32      | Verify add book api with missing isbn | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC33      | Verify add book api with missing aisle | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC34      | Verify add book api with missing author | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC35      | Verify add book api with null name | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC36      | Verify add book api with null isbn | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC37      | Verify add book api with null aisle | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC38      | Verify add book api with null author | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC39      | Verify add book api with invalid(numeric) name | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC40      | Verify add book api with invalid(boolean) name | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC41      | Verify add book api with invalid(numeric) isbn | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC42      | Verify add book api with invalid(boolean) isbn | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC43      | Verify add book api with invalid(numeric) aisle | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC44      | Verify add book api with invalid(boolean) aisle | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC45      | Verify add book api with invalid(numeric) author | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
          |  TC46      | Verify add book api with invalid(boolean) author | /Library/Addbook.php | POST | 400 | /src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json|
         # |TC47 |Verify add book api with below minimum length in name|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
         # |TC48 |Verify add book api with above maximum length in name|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
         # |TC49 |Verify add book api with below minimum length in isbn|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
         # |TC50 |Verify add book api with above maximum length in isbn|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
         # |TC51 |Verify add book api with below minimum length in aisle|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
         # |TC52 |Verify add book api with above maximum length in aisle|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
         # |TC53 |Verify add book api with below minimum length in author|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |
         # |TC54 |Verify add book api with above maximum length in author|  /Library/Addbook.php | POST | 200 | /src/main/resources/test_data/book_apis/add/AddBookValidTestData.json |