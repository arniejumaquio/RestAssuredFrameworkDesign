package rahulshettyacademy.utilities;

import java.io.IOException;
import java.util.*;

public class ValidationLibrary {

    private static Map<String, Object> validationPatterns;

    static {
        try {
            List<HashMap<String, Object>> patterns = JSONUtility.getDataFromJsonFile("/src/main/resources/validation_library/ValidationPatterns.json");
            validationPatterns = patterns.isEmpty() ? new HashMap<>() : patterns.get(0);
        } catch (IOException e) {
            validationPatterns = new HashMap<>();
            System.err.println("Failed to load validation patterns: " + e.getMessage());
        }
    }

    public static List<Map<String, Object>> generateTestCasesForField(
            String fieldName,
            String fieldType,
            Map<String, Object> basePayload,
            Map<String, Object> baseHeaders,
            String baseTestCaseIdPrefix,
            int startingTestCaseNumber
    ) {
        List<Map<String, Object>> testCases = new ArrayList<>();
        
        Map<String, Object> fieldPatterns = (Map<String, Object>) validationPatterns.get("fieldValidationPatterns");
        if (fieldPatterns == null || !fieldPatterns.containsKey(fieldType)) {
            System.err.println("No validation patterns found for field type: " + fieldType);
            return testCases;
        }

        Map<String, Object> patternData = (Map<String, Object>) fieldPatterns.get(fieldType);
        List<Map<String, Object>> validations = (List<Map<String, Object>>) patternData.get("validations");

        int testCaseCounter = startingTestCaseNumber;

        for (Map<String, Object> validation : validations) {
            String validationType = (String) validation.get("type");
            Object invalidValue = validation.get("value");
            String expectedError = ((String) validation.get("expectedError")).replace("{fieldName}", fieldName);

            Map<String, Object> testCase = new HashMap<>();
            testCase.put("testCaseId", baseTestCaseIdPrefix + testCaseCounter);
            testCase.put("testCaseDescription", "Verify API with " + validationType + " " + fieldName);
            testCase.put("headers", new HashMap<>(baseHeaders));

            Map<String, Object> requestPayload = new HashMap<>(basePayload);
            
            if ("REMOVE_FIELD".equals(invalidValue)) {
                requestPayload.remove(fieldName);
            } else if (invalidValue instanceof String && ((String) invalidValue).startsWith("GENERATE_LONG_STRING_")) {
                int length = Integer.parseInt(((String) invalidValue).replace("GENERATE_LONG_STRING_", ""));
                requestPayload.put(fieldName, generateLongString(length));
            } else {
                requestPayload.put(fieldName, invalidValue);
            }

            testCase.put("request", requestPayload);

            Map<String, Object> expectedResponse = new HashMap<>();
            expectedResponse.put("error", "Bad Request");
            expectedResponse.put("message", expectedError);
            testCase.put("expectedResponse", expectedResponse);

            testCases.add(testCase);
            testCaseCounter++;
        }

        return testCases;
    }

    public static List<Map<String, Object>> generateTestCasesForAllFields(
            Map<String, String> fieldTypeMapping,
            Map<String, Object> basePayload,
            Map<String, Object> baseHeaders,
            String baseTestCaseIdPrefix,
            int startingTestCaseNumber
    ) {
        List<Map<String, Object>> allTestCases = new ArrayList<>();
        int currentTestCaseNumber = startingTestCaseNumber;

        for (Map.Entry<String, String> entry : fieldTypeMapping.entrySet()) {
            String fieldName = entry.getKey();
            String fieldType = entry.getValue();

            List<Map<String, Object>> fieldTestCases = generateTestCasesForField(
                    fieldName,
                    fieldType,
                    basePayload,
                    baseHeaders,
                    baseTestCaseIdPrefix,
                    currentTestCaseNumber
            );

            allTestCases.addAll(fieldTestCases);
            currentTestCaseNumber += fieldTestCases.size();
        }

        return allTestCases;
    }

    public static List<Map<String, Object>> generateHeaderValidationTests(
            Map<String, Object> basePayload,
            String baseTestCaseIdPrefix,
            int startingTestCaseNumber
    ) {
        List<Map<String, Object>> testCases = new ArrayList<>();
        
        Map<String, Object> headerPatterns = (Map<String, Object>) validationPatterns.get("headerValidationPatterns");
        if (headerPatterns == null) {
            return testCases;
        }

        List<Map<String, Object>> validations = (List<Map<String, Object>>) headerPatterns.get("validations");
        int testCaseCounter = startingTestCaseNumber;

        for (Map<String, Object> validation : validations) {
            String validationType = (String) validation.get("type");
            Map<String, Object> headers = (Map<String, Object>) validation.get("headers");
            String expectedError = (String) validation.get("expectedError");

            Map<String, Object> testCase = new HashMap<>();
            testCase.put("testCaseId", baseTestCaseIdPrefix + testCaseCounter);
            testCase.put("testCaseDescription", "Verify API with " + validationType);
            testCase.put("headers", new HashMap<>(headers));
            testCase.put("request", new HashMap<>(basePayload));

            Map<String, Object> expectedResponse = new HashMap<>();
            expectedResponse.put("error", "Bad Request");
            expectedResponse.put("message", expectedError);
            testCase.put("expectedResponse", expectedResponse);

            testCases.add(testCase);
            testCaseCounter++;
        }

        return testCases;
    }

    public static Map<String, Object> createTestCase(
            String testCaseId,
            String description,
            Map<String, Object> headers,
            Map<String, Object> request,
            Map<String, Object> expectedResponse
    ) {
        Map<String, Object> testCase = new HashMap<>();
        testCase.put("testCaseId", testCaseId);
        testCase.put("testCaseDescription", description);
        testCase.put("headers", headers);
        testCase.put("request", request);
        testCase.put("expectedResponse", expectedResponse);
        return testCase;
    }

    public static List<Map<String, Object>> getValidationPatternsForFieldType(String fieldType) {
        Map<String, Object> fieldPatterns = (Map<String, Object>) validationPatterns.get("fieldValidationPatterns");
        if (fieldPatterns == null || !fieldPatterns.containsKey(fieldType)) {
            return new ArrayList<>();
        }

        Map<String, Object> patternData = (Map<String, Object>) fieldPatterns.get(fieldType);
        return (List<Map<String, Object>>) patternData.get("validations");
    }

    private static String generateLongString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append("A");
        }
        return sb.toString();
    }

    public static void printTestCaseSummary(List<Map<String, Object>> testCases) {
        System.out.println("=== Generated Test Cases Summary ===");
        System.out.println("Total test cases: " + testCases.size());
        for (Map<String, Object> testCase : testCases) {
            System.out.println(testCase.get("testCaseId") + ": " + testCase.get("testCaseDescription"));
        }
        System.out.println("====================================");
    }
}
