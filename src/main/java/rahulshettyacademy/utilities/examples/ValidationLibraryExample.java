package rahulshettyacademy.utilities.examples;

import rahulshettyacademy.utilities.ValidationLibrary;

import java.util.*;

public class ValidationLibraryExample {

    public static void main(String[] args) {
        
        // Example 1: Generate validation test cases for a single field
        example1_SingleFieldValidation();
        
        // Example 2: Generate validation test cases for all fields in an API
        example2_AllFieldsValidation();
        
        // Example 3: Generate header validation test cases
        example3_HeaderValidation();
        
        // Example 4: Create custom test case
        example4_CustomTestCase();
    }

    private static void example1_SingleFieldValidation() {
        System.out.println("\n=== EXAMPLE 1: Single Field Validation ===\n");
        
        // Base payload for Add Book API
        Map<String, Object> basePayload = new HashMap<>();
        basePayload.put("name", "Test Name");
        basePayload.put("isbn", "Test ISBN");
        basePayload.put("aisle", "123456");
        basePayload.put("author", "Test Author");
        
        // Base headers
        Map<String, Object> baseHeaders = new HashMap<>();
        baseHeaders.put("Content-Type", "application/json");
        
        // Generate test cases for 'name' field (string type)
        List<Map<String, Object>> nameValidationTests = ValidationLibrary.generateTestCasesForField(
                "name",              // field name
                "stringField",       // field type from ValidationPatterns.json
                basePayload,         // base payload
                baseHeaders,         // base headers
                "TC",                // test case ID prefix
                100                  // starting test case number
        );
        
        ValidationLibrary.printTestCaseSummary(nameValidationTests);
        
        // Print first test case as example
        if (!nameValidationTests.isEmpty()) {
            System.out.println("\nExample Test Case:");
            System.out.println(nameValidationTests.get(0));
        }
    }

    private static void example2_AllFieldsValidation() {
        System.out.println("\n=== EXAMPLE 2: All Fields Validation ===\n");
        
        // Base payload for Add Book API
        Map<String, Object> basePayload = new HashMap<>();
        basePayload.put("name", "Test Name");
        basePayload.put("isbn", "Test ISBN");
        basePayload.put("aisle", "123456");
        basePayload.put("author", "Test Author");
        
        // Base headers
        Map<String, Object> baseHeaders = new HashMap<>();
        baseHeaders.put("Content-Type", "application/json");
        
        // Define field type mapping
        Map<String, String> fieldTypeMapping = new LinkedHashMap<>();
        fieldTypeMapping.put("name", "stringField");
        fieldTypeMapping.put("isbn", "stringField");
        fieldTypeMapping.put("aisle", "stringField");
        fieldTypeMapping.put("author", "stringField");
        
        // Generate test cases for all fields
        List<Map<String, Object>> allValidationTests = ValidationLibrary.generateTestCasesForAllFields(
                fieldTypeMapping,    // field to type mapping
                basePayload,         // base payload
                baseHeaders,         // base headers
                "TC",                // test case ID prefix
                200                  // starting test case number
        );
        
        ValidationLibrary.printTestCaseSummary(allValidationTests);
    }

    private static void example3_HeaderValidation() {
        System.out.println("\n=== EXAMPLE 3: Header Validation ===\n");
        
        // Base payload
        Map<String, Object> basePayload = new HashMap<>();
        basePayload.put("name", "Test Name");
        basePayload.put("isbn", "Test ISBN");
        basePayload.put("aisle", "123456");
        basePayload.put("author", "Test Author");
        
        // Generate header validation test cases
        List<Map<String, Object>> headerValidationTests = ValidationLibrary.generateHeaderValidationTests(
                basePayload,         // base payload
                "TC",                // test case ID prefix
                300                  // starting test case number
        );
        
        ValidationLibrary.printTestCaseSummary(headerValidationTests);
    }

    private static void example4_CustomTestCase() {
        System.out.println("\n=== EXAMPLE 4: Custom Test Case ===\n");
        
        // Create headers
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        
        // Create request payload
        Map<String, Object> request = new HashMap<>();
        request.put("name", "Test Name");
        request.put("isbn", "Test ISBN");
        request.put("aisle", "123456");
        request.put("author", "Test Author");
        
        // Create expected response
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("error", "Conflict");
        expectedResponse.put("message", "Book already exists");
        
        // Create custom test case
        Map<String, Object> customTestCase = ValidationLibrary.createTestCase(
                "TC400",
                "Verify add book API with duplicate ISBN and Aisle",
                headers,
                request,
                expectedResponse
        );
        
        System.out.println("Custom Test Case:");
        System.out.println(customTestCase);
    }

    // Example: How to use for a different API (e.g., Update Book)
    public static void exampleForUpdateBookAPI() {
        System.out.println("\n=== EXAMPLE: Update Book API Validation ===\n");
        
        // Base payload for Update Book API
        Map<String, Object> basePayload = new HashMap<>();
        basePayload.put("ID", "12345");
        basePayload.put("name", "Updated Name");
        basePayload.put("author", "Updated Author");
        
        // Base headers
        Map<String, Object> baseHeaders = new HashMap<>();
        baseHeaders.put("Content-Type", "application/json");
        
        // Define field type mapping for Update Book
        Map<String, String> fieldTypeMapping = new LinkedHashMap<>();
        fieldTypeMapping.put("ID", "stringField");
        fieldTypeMapping.put("name", "stringField");
        fieldTypeMapping.put("author", "stringField");
        
        // Generate test cases
        List<Map<String, Object>> updateBookValidationTests = ValidationLibrary.generateTestCasesForAllFields(
                fieldTypeMapping,
                basePayload,
                baseHeaders,
                "TC_UPDATE",
                1
        );
        
        ValidationLibrary.printTestCaseSummary(updateBookValidationTests);
    }

    // Example: How to use for an API with different field types
    public static void exampleForUserRegistrationAPI() {
        System.out.println("\n=== EXAMPLE: User Registration API Validation ===\n");
        
        // Base payload for User Registration API
        Map<String, Object> basePayload = new HashMap<>();
        basePayload.put("username", "testuser");
        basePayload.put("email", "test@example.com");
        basePayload.put("age", 25);
        basePayload.put("isActive", true);
        
        // Base headers
        Map<String, Object> baseHeaders = new HashMap<>();
        baseHeaders.put("Content-Type", "application/json");
        
        // Define field type mapping with different field types
        Map<String, String> fieldTypeMapping = new LinkedHashMap<>();
        fieldTypeMapping.put("username", "stringField");
        fieldTypeMapping.put("email", "emailField");
        fieldTypeMapping.put("age", "numericField");
        fieldTypeMapping.put("isActive", "booleanField");
        
        // Generate test cases
        List<Map<String, Object>> userRegistrationValidationTests = ValidationLibrary.generateTestCasesForAllFields(
                fieldTypeMapping,
                basePayload,
                baseHeaders,
                "TC_USER",
                1
        );
        
        ValidationLibrary.printTestCaseSummary(userRegistrationValidationTests);
    }
}
