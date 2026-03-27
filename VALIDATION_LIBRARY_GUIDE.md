# Validation Library Guide

## Overview
The Validation Library is a reusable framework for generating negative test cases across different APIs. It eliminates the need to manually create repetitive validation test cases for each API endpoint.

## Structure

```
src/main/
├── resources/
│   └── validation_library/
│       └── ValidationPatterns.json          # Centralized validation patterns
└── java/rahulshettyacademy/utilities/
    ├── ValidationLibrary.java               # Core utility class
    └── examples/
        └── ValidationLibraryExample.java    # Usage examples
```

## Components

### 1. ValidationPatterns.json
Centralized repository of validation patterns organized by field types:
- **stringField**: empty, null, missing, invalid types, special chars, XSS, SQL injection
- **numericField**: invalid types, negative, zero, max value
- **emailField**: invalid formats, missing @, no domain
- **dateField**: invalid formats, wrong patterns
- **booleanField**: invalid types
- **headerValidationPatterns**: Content-Type, Authorization validations
- **businessLogicPatterns**: duplicate resources, non-existent resources

### 2. ValidationLibrary.java
Utility class with methods to generate test cases programmatically:

#### Key Methods:

**`generateTestCasesForField()`**
Generate validation tests for a single field.
```java
List<Map<String, Object>> testCases = ValidationLibrary.generateTestCasesForField(
    "name",              // field name
    "stringField",       // field type
    basePayload,         // base request payload
    baseHeaders,         // base headers
    "TC",                // test case ID prefix
    100                  // starting test case number
);
```

**`generateTestCasesForAllFields()`**
Generate validation tests for all fields in an API.
```java
Map<String, String> fieldTypeMapping = new LinkedHashMap<>();
fieldTypeMapping.put("name", "stringField");
fieldTypeMapping.put("email", "emailField");
fieldTypeMapping.put("age", "numericField");

List<Map<String, Object>> testCases = ValidationLibrary.generateTestCasesForAllFields(
    fieldTypeMapping,
    basePayload,
    baseHeaders,
    "TC",
    1
);
```

**`generateHeaderValidationTests()`**
Generate header validation tests.
```java
List<Map<String, Object>> testCases = ValidationLibrary.generateHeaderValidationTests(
    basePayload,
    "TC",
    300
);
```

**`createTestCase()`**
Create a custom test case manually.
```java
Map<String, Object> testCase = ValidationLibrary.createTestCase(
    "TC500",
    "Verify duplicate book",
    headers,
    request,
    expectedResponse
);
```

## Usage Examples

### Example 1: Add Book API (All String Fields)

```java
// Define base payload
Map<String, Object> basePayload = new HashMap<>();
basePayload.put("name", "Test Name");
basePayload.put("isbn", "Test ISBN");
basePayload.put("aisle", "123456");
basePayload.put("author", "Test Author");

// Define headers
Map<String, Object> baseHeaders = new HashMap<>();
baseHeaders.put("Content-Type", "application/json");

// Define field types
Map<String, String> fieldTypeMapping = new LinkedHashMap<>();
fieldTypeMapping.put("name", "stringField");
fieldTypeMapping.put("isbn", "stringField");
fieldTypeMapping.put("aisle", "stringField");
fieldTypeMapping.put("author", "stringField");

// Generate all validation test cases
List<Map<String, Object>> testCases = ValidationLibrary.generateTestCasesForAllFields(
    fieldTypeMapping,
    basePayload,
    baseHeaders,
    "TC",
    1
);

// This generates 44 test cases (11 validations × 4 fields)
// TC1-TC11: name field validations
// TC12-TC22: isbn field validations
// TC23-TC33: aisle field validations
// TC34-TC44: author field validations
```

### Example 2: User Registration API (Mixed Field Types)

```java
Map<String, Object> basePayload = new HashMap<>();
basePayload.put("username", "testuser");
basePayload.put("email", "test@example.com");
basePayload.put("age", 25);
basePayload.put("isActive", true);

Map<String, Object> baseHeaders = new HashMap<>();
baseHeaders.put("Content-Type", "application/json");

Map<String, String> fieldTypeMapping = new LinkedHashMap<>();
fieldTypeMapping.put("username", "stringField");    // 11 validations
fieldTypeMapping.put("email", "emailField");        // 5 validations
fieldTypeMapping.put("age", "numericField");        // 7 validations
fieldTypeMapping.put("isActive", "booleanField");   // 3 validations

List<Map<String, Object>> testCases = ValidationLibrary.generateTestCasesForAllFields(
    fieldTypeMapping,
    basePayload,
    baseHeaders,
    "TC_USER",
    1
);

// This generates 26 test cases total
```

### Example 3: Update Book API

```java
Map<String, Object> basePayload = new HashMap<>();
basePayload.put("ID", "12345");
basePayload.put("name", "Updated Name");
basePayload.put("author", "Updated Author");

Map<String, Object> baseHeaders = new HashMap<>();
baseHeaders.put("Content-Type", "application/json");

Map<String, String> fieldTypeMapping = new LinkedHashMap<>();
fieldTypeMapping.put("ID", "stringField");
fieldTypeMapping.put("name", "stringField");
fieldTypeMapping.put("author", "stringField");

List<Map<String, Object>> testCases = ValidationLibrary.generateTestCasesForAllFields(
    fieldTypeMapping,
    basePayload,
    baseHeaders,
    "TC_UPDATE",
    1
);

// This generates 33 test cases (11 validations × 3 fields)
```

### Example 4: Single Field Validation

```java
// Only generate validations for the 'email' field
List<Map<String, Object>> emailTests = ValidationLibrary.generateTestCasesForField(
    "email",
    "emailField",
    basePayload,
    baseHeaders,
    "TC",
    100
);

// This generates 5 test cases for email field only
```

## How to Apply to Different APIs

### Step 1: Identify Your API Fields
List all fields in your API request payload.

### Step 2: Map Fields to Types
Determine the field type for each field:
- String fields → `stringField`
- Numeric fields → `numericField`
- Email fields → `emailField`
- Date fields → `dateField`
- Boolean fields → `booleanField`

### Step 3: Create Base Payload and Headers
Define a valid base request with all required fields.

### Step 4: Generate Test Cases
Use `generateTestCasesForAllFields()` to generate all validation test cases.

### Step 5: Export to JSON (Optional)
Save the generated test cases to a JSON file for use in your test data.

```java
// Example: Save to JSON file
List<Map<String, Object>> testCases = ValidationLibrary.generateTestCasesForAllFields(...);
JSONUtility.saveToJsonFile(testCases, "/path/to/testdata.json");
```

## Benefits

1. **Consistency**: Same validation patterns across all APIs
2. **Time-Saving**: Generate 40+ test cases in seconds instead of manual creation
3. **Maintainability**: Update patterns in one place, affects all APIs
4. **Comprehensive**: Covers common validation scenarios automatically
5. **Reusability**: Use for any API by just defining field mappings
6. **Scalability**: Easy to add new validation patterns

## Customization

### Add New Validation Pattern
Edit `ValidationPatterns.json` to add new validation types:

```json
{
  "type": "customValidation",
  "value": "custom value",
  "expectedError": "Custom error message"
}
```

### Add New Field Type
Add a new field type section in `ValidationPatterns.json`:

```json
"customFieldType": {
  "description": "Custom field validations",
  "validations": [...]
}
```

### Override Expected Error Messages
When generating test cases, you can modify the expected response:

```java
List<Map<String, Object>> testCases = ValidationLibrary.generateTestCasesForField(...);
for (Map<String, Object> testCase : testCases) {
    Map<String, Object> expectedResponse = (Map<String, Object>) testCase.get("expectedResponse");
    expectedResponse.put("message", "Your custom error message");
}
```

## Best Practices

1. **Start with High-Priority APIs**: Apply to critical APIs first (authentication, payment, data modification)
2. **Review Generated Test Cases**: Verify expected error messages match your API's actual responses
3. **Combine with Business Logic Tests**: Use the library for field validations, add custom business logic tests separately
4. **Update Patterns Regularly**: As you discover new edge cases, add them to ValidationPatterns.json
5. **Use Meaningful Test Case IDs**: Use prefixes that indicate the API (e.g., TC_USER, TC_BOOK, TC_ORDER)

## Integration with Existing Framework

The library integrates seamlessly with your existing test data structure. Generated test cases follow the same format as your current test data files:

```json
{
  "testCaseId": "TC1",
  "testCaseDescription": "Verify add book api with empty name",
  "headers": {
    "Content-Type": "application/json"
  },
  "request": {
    "aisle": "390490492",
    "author": "Test Author",
    "isbn": "Test ISBN",
    "name": ""
  },
  "expectedResponse": {
    "error": "Bad Request",
    "message": "Field name cannot be empty"
  }
}
```

## Quick Start

1. Review `ValidationLibraryExample.java` for usage examples
2. Run the examples to see generated test cases
3. Adapt the examples for your specific API
4. Generate test cases and save to JSON files
5. Use in your Cucumber feature files

## Support

For questions or issues, refer to:
- `ValidationLibraryExample.java` - Comprehensive usage examples
- `ValidationPatterns.json` - Available validation patterns
- `ValidationLibrary.java` - API documentation in code comments
