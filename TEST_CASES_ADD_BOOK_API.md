# Add Book API - Test Cases Documentation

## API Details
- **Base URL**: http://216.10.245.166
- **Resource**: /Library/Addbook.php
- **Method**: POST
- **Content-Type**: application/json

## Request Payload Structure
```json
{
  "name": "string",
  "isbn": "string",
  "aisle": "string",
  "author": "string"
}
```

## Expected Response (Success)
```json
{
  "Msg": "successfully added",
  "ID": "isbn+aisle"
}
```

---

## Test Cases Summary

### Positive Test Cases (1-7, 20-22, 24)

| ID | Test Case | Description | Expected Result |
|---|---|---|---|
| TC01 | Valid Data | Add book with all valid fields | Status 200, Msg="successfully added", ID returned |
| TC02 | Special Characters | Add book with special chars in name | Status 200, Success message |
| TC03 | Numeric Values | Add book with numbers in text fields | Status 200, Success message |
| TC04 | Alphanumeric ISBN/Aisle | Add book with alphanumeric ISBN and aisle | Status 200, Success message |
| TC05 | Long Text Values | Add book with 500 char name and author | Status 200, Success message |
| TC06 | Minimum Length | Add book with single char values | Status 200, Success message |
| TC07 | Duplicate Book | Add same book twice | First: 200, Second: Error message |
| TC20 | Unicode Characters | Add book with Chinese, Japanese, Korean chars | Status 200, Success message |
| TC21 | Response Time | Verify response time < 5 seconds | Response within acceptable time |
| TC22 | Response Structure | Verify response contains Msg and ID fields | Both fields present in response |
| TC24 | ID Format | Verify ID is combination of ISBN+aisle | ID = isbn+aisle |

### Negative Test Cases - Empty Fields (8-11)

| ID | Test Case | Description | Expected Result |
|---|---|---|---|
| TC08 | Empty Name | Add book with empty name field | Status 400/404/422 |
| TC09 | Empty ISBN | Add book with empty ISBN field | Status 400/404/422 |
| TC10 | Empty Aisle | Add book with empty aisle field | Status 400/404/422 |
| TC11 | Empty Author | Add book with empty author field | Status 400/404/422 |

### Negative Test Cases - Missing Fields (12-15)

| ID | Test Case | Description | Expected Result |
|---|---|---|---|
| TC12 | Missing Name | Add book without name field in JSON | Status 400/404/422 |
| TC13 | Missing ISBN | Add book without ISBN field in JSON | Status 400/404/422 |
| TC14 | Missing Aisle | Add book without aisle field in JSON | Status 400/404/422 |
| TC15 | Missing Author | Add book without author field in JSON | Status 400/404/422 |

### Negative Test Cases - Invalid Data (16-19, 23, 25)

| ID | Test Case | Description | Expected Result |
|---|---|---|---|
| TC16 | Invalid JSON | Send malformed JSON | Status 400/500 |
| TC17 | Missing Content-Type | Send request without Content-Type header | Status 415/400 or handled gracefully |
| TC18 | Wrong Content-Type | Send request with text/plain Content-Type | Status 415/400 or handled gracefully |
| TC19 | Null Values | Send null values for all fields | Status 400/404/422 |
| TC23 | Whitespace Values | Send only whitespace in name and author | Status 400 or handled gracefully |
| TC25 | SQL Injection | Attempt SQL injection in fields | Handled safely, no DB corruption |

---

## Test Execution Strategy

### Priority Levels
- **P1 (Critical)**: TC01, TC07, TC08-TC15 - Core functionality and validation
- **P2 (High)**: TC02-TC06, TC16-TC19 - Data handling and error scenarios  
- **P3 (Medium)**: TC20-TC25 - Edge cases, performance, security

### Execution Order
Tests are prioritized (1-25) to ensure:
1. Basic functionality validated first
2. Positive cases before negative cases
3. Field validation tests grouped together
4. Edge cases and security tests at the end

### Test Data Strategy
- **Unique ISBN Generation**: Uses timestamp or UUID to avoid conflicts
- **Dynamic IDs**: Prevents duplicate entries across test runs
- **Parameterized Payloads**: Reusable utility methods for different scenarios

---

## Test Coverage

### Functional Coverage
- ✅ Valid data submission
- ✅ Field validation (required fields)
- ✅ Duplicate detection
- ✅ Data type handling
- ✅ Special characters and Unicode support

### Non-Functional Coverage
- ✅ Response time validation
- ✅ API response structure verification
- ✅ Security testing (SQL injection)
- ✅ Error handling and status codes

### Boundary Testing
- ✅ Minimum length values (1 character)
- ✅ Maximum length values (500 characters)
- ✅ Empty values
- ✅ Null values
- ✅ Whitespace only values

### Negative Testing
- ✅ Missing required fields
- ✅ Empty fields
- ✅ Invalid JSON format
- ✅ Missing/incorrect headers
- ✅ Malicious input (SQL injection)

---

## How to Run Tests

### Run All Tests
```bash
mvn test -Dtest=AddBookTestSuite
```

### Run Specific Test
```bash
mvn test -Dtest=AddBookTestSuite#testAddBookWithValidData
```

### Run with TestNG XML
Create a `testng.xml` file and specify the test suite configuration.

---

## Prerequisites
- Rest Assured library
- TestNG framework
- Java 8 or higher
- Network access to http://216.10.245.166

---

## Notes
- Some negative test cases check for multiple possible status codes (400/404/422) as API error handling may vary
- Tests use `.log().all()` for detailed request/response logging during debugging
- Unique identifiers (timestamps/UUIDs) prevent test data conflicts
- Tests are independent and can run in any order (though priority is set for logical flow)

---

## Maintenance
- Update `AddBodyUtility` class when adding new payload variations
- Add new test cases for any new API features or requirements
- Review and update expected status codes based on actual API behavior
- Keep test data unique to avoid database conflicts
