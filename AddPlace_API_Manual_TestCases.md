# Add Place API - Manual Test Cases

## API Information
**Endpoint:** `POST /maps/api/place/add/json`  
**Base URL:** (As configured in Global.properties)  
**Authentication:** Query Parameter - `key=qaclick123`  
**Content-Type:** `application/json`

---

## Test Case 1: Verify Add Place API with Valid Data
**Priority:** High  
**Type:** Positive Test

### Pre-conditions
- API server is up and running
- Valid API key is available

### Test Steps
1. Prepare request with valid payload:
```json
{
  "location": {
    "lat": -38.383494,
    "lng": 33.427362
  },
  "accuracy": 50,
  "name": "Frontline house",
  "phone_number": "(+91) 983 893 3937",
  "address": "29, side layout, cohen 09",
  "types": ["shoe park", "shop"],
  "website": "http://google.com",
  "language": "French-IN"
}
```
2. Send POST request to endpoint with API key
3. Verify response

### Expected Results
- Status Code: `200`
- Response body contains:
  - `"status": "OK"`
  - `"scope": "APP"`
  - `"place_id"` (not null)
  - `"reference"` (not null)
  - `"id"` (not null)

---

## Test Case 2: Verify Add Place API with Minimum Required Fields
**Priority:** High  
**Type:** Positive Test

### Test Steps
1. Prepare request with only mandatory fields:
```json
{
  "location": {
    "lat": -38.383494,
    "lng": 33.427362
  },
  "accuracy": 50,
  "name": "Test Place",
  "phone_number": "(+91) 123 456 7890",
  "address": "Test Address",
  "types": ["test"],
  "website": "http://test.com",
  "language": "en-US"
}
```
2. Send POST request
3. Verify response

### Expected Results
- Status Code: `200`
- Response contains valid `place_id`, `status: OK`, and `scope: APP`

---

## Test Case 3: Verify Add Place API without API Key
**Priority:** High  
**Type:** Negative Test

### Test Steps
1. Prepare request with valid payload
2. Send POST request WITHOUT the `key` query parameter
3. Verify response

### Expected Results
- Status Code: `400` or `401` or `403`
- Error message indicating missing or invalid API key

---

## Test Case 4: Verify Add Place API with Invalid API Key
**Priority:** High  
**Type:** Negative Test

### Test Steps
1. Prepare request with valid payload
2. Send POST request with invalid key (e.g., `key=invalid123`)
3. Verify response

### Expected Results
- Status Code: `403` (Forbidden) or `401` (Unauthorized)
- Error message indicating invalid API key

---

## Test Case 5: Verify Add Place API with Missing Location Field
**Priority:** High  
**Type:** Negative Test

### Test Steps
1. Prepare request WITHOUT `location` field:
```json
{
  "accuracy": 50,
  "name": "Test Place",
  "phone_number": "(+91) 123 456 7890",
  "address": "Test Address",
  "types": ["test"],
  "website": "http://test.com",
  "language": "en-US"
}
```
2. Send POST request
3. Verify response

### Expected Results
- Status Code: `400` (Bad Request)
- Error message indicating missing required field `location`

---

## Test Case 6: Verify Add Place API with Invalid Latitude
**Priority:** Medium  
**Type:** Negative Test

### Test Steps
1. Prepare request with invalid latitude (e.g., `lat: 91` or `lat: -91`, valid range: -90 to 90):
```json
{
  "location": {
    "lat": 100,
    "lng": 33.427362
  },
  "accuracy": 50,
  "name": "Test Place",
  "phone_number": "(+91) 123 456 7890",
  "address": "Test Address",
  "types": ["test"],
  "website": "http://test.com",
  "language": "en-US"
}
```
2. Send POST request
3. Verify response

### Expected Results
- Status Code: `400` (Bad Request)
- Error message indicating invalid latitude value

---

## Test Case 7: Verify Add Place API with Invalid Longitude
**Priority:** Medium  
**Type:** Negative Test

### Test Steps
1. Prepare request with invalid longitude (e.g., `lng: 181` or `lng: -181`, valid range: -180 to 180):
```json
{
  "location": {
    "lat": -38.383494,
    "lng": 200
  },
  "accuracy": 50,
  "name": "Test Place",
  "phone_number": "(+91) 123 456 7890",
  "address": "Test Address",
  "types": ["test"],
  "website": "http://test.com",
  "language": "en-US"
}
```
2. Send POST request
3. Verify response

### Expected Results
- Status Code: `400` (Bad Request)
- Error message indicating invalid longitude value

---

## Test Case 8: Verify Add Place API with Missing Name Field
**Priority:** High  
**Type:** Negative Test

### Test Steps
1. Prepare request WITHOUT `name` field:
```json
{
  "location": {
    "lat": -38.383494,
    "lng": 33.427362
  },
  "accuracy": 50,
  "phone_number": "(+91) 123 456 7890",
  "address": "Test Address",
  "types": ["test"],
  "website": "http://test.com",
  "language": "en-US"
}
```
2. Send POST request
3. Verify response

### Expected Results
- Status Code: `400` (Bad Request)
- Error message indicating missing required field `name`

---

## Test Case 9: Verify Add Place API with Empty Name Field
**Priority:** Medium  
**Type:** Negative Test

### Test Steps
1. Prepare request with empty `name` field:
```json
{
  "location": {
    "lat": -38.383494,
    "lng": 33.427362
  },
  "accuracy": 50,
  "name": "",
  "phone_number": "(+91) 123 456 7890",
  "address": "Test Address",
  "types": ["test"],
  "website": "http://test.com",
  "language": "en-US"
}
```
2. Send POST request
3. Verify response

### Expected Results
- Status Code: `400` (Bad Request)
- Error message indicating invalid or empty name field

---

## Test Case 10: Verify Add Place API with Invalid Phone Number Format
**Priority:** Low  
**Type:** Negative Test

### Test Steps
1. Prepare request with invalid phone number (e.g., special characters, too short, etc.):
```json
{
  "location": {
    "lat": -38.383494,
    "lng": 33.427362
  },
  "accuracy": 50,
  "name": "Test Place",
  "phone_number": "abc123",
  "address": "Test Address",
  "types": ["test"],
  "website": "http://test.com",
  "language": "en-US"
}
```
2. Send POST request
3. Verify response

### Expected Results
- Status Code: `400` (Bad Request) OR `200` (if API accepts any format)
- If rejected, error message indicating invalid phone number format

---

## Test Case 11: Verify Add Place API with Invalid Website URL
**Priority:** Low  
**Type:** Negative Test

### Test Steps
1. Prepare request with invalid website URL:
```json
{
  "location": {
    "lat": -38.383494,
    "lng": 33.427362
  },
  "accuracy": 50,
  "name": "Test Place",
  "phone_number": "(+91) 123 456 7890",
  "address": "Test Address",
  "types": ["test"],
  "website": "invalid-url",
  "language": "en-US"
}
```
2. Send POST request
3. Verify response

### Expected Results
- Status Code: `400` (Bad Request) OR `200` (if API doesn't validate URL format)
- If rejected, error message indicating invalid URL format

---

## Test Case 12: Verify Add Place API with Negative Accuracy Value
**Priority:** Medium  
**Type:** Negative Test

### Test Steps
1. Prepare request with negative accuracy value:
```json
{
  "location": {
    "lat": -38.383494,
    "lng": 33.427362
  },
  "accuracy": -50,
  "name": "Test Place",
  "phone_number": "(+91) 123 456 7890",
  "address": "Test Address",
  "types": ["test"],
  "website": "http://test.com",
  "language": "en-US"
}
```
2. Send POST request
3. Verify response

### Expected Results
- Status Code: `400` (Bad Request) OR `200` (if API accepts negative values)
- If rejected, error message indicating invalid accuracy value

---

## Test Case 13: Verify Add Place API with Empty Types Array
**Priority:** Medium  
**Type:** Negative Test

### Test Steps
1. Prepare request with empty `types` array:
```json
{
  "location": {
    "lat": -38.383494,
    "lng": 33.427362
  },
  "accuracy": 50,
  "name": "Test Place",
  "phone_number": "(+91) 123 456 7890",
  "address": "Test Address",
  "types": [],
  "website": "http://test.com",
  "language": "en-US"
}
```
2. Send POST request
3. Verify response

### Expected Results
- Status Code: `400` (Bad Request) OR `200` (if API accepts empty array)
- If rejected, error message indicating types array cannot be empty

---

## Test Case 14: Verify Add Place API with Very Long Name (Boundary Test)
**Priority:** Medium  
**Type:** Boundary Test

### Test Steps
1. Prepare request with very long name (e.g., 500+ characters):
```json
{
  "location": {
    "lat": -38.383494,
    "lng": 33.427362
  },
  "accuracy": 50,
  "name": "A very long name that exceeds normal length expectations..." (500 chars),
  "phone_number": "(+91) 123 456 7890",
  "address": "Test Address",
  "types": ["test"],
  "website": "http://test.com",
  "language": "en-US"
}
```
2. Send POST request
3. Verify response

### Expected Results
- Status Code: `400` (Bad Request) if there's a character limit
- OR Status Code: `200` with truncated or full name in response

---

## Test Case 15: Verify Add Place API with Special Characters in Name
**Priority:** Medium  
**Type:** Positive Test

### Test Steps
1. Prepare request with special characters in name:
```json
{
  "location": {
    "lat": -38.383494,
    "lng": 33.427362
  },
  "accuracy": 50,
  "name": "Test@Place#123!",
  "phone_number": "(+91) 123 456 7890",
  "address": "Test Address",
  "types": ["test"],
  "website": "http://test.com",
  "language": "en-US"
}
```
2. Send POST request
3. Verify response

### Expected Results
- Status Code: `200` OR `400` (depending on API validation rules)
- If accepted, verify name is stored correctly with special characters

---

## Test Case 16: Verify Add Place API with Duplicate Place Data
**Priority:** Medium  
**Type:** Negative Test

### Test Steps
1. Send the same place data twice with identical information
2. First request should succeed
3. Second request with same data should be validated

### Expected Results
- First Request: Status Code `200` with success
- Second Request: Either `200` (creates duplicate) OR `409` (Conflict) preventing duplicate entry

---

## Test Case 17: Verify Add Place API Response Time
**Priority:** Low  
**Type:** Performance Test

### Test Steps
1. Prepare valid request
2. Send POST request
3. Measure response time

### Expected Results
- Status Code: `200`
- Response time should be less than 3 seconds (acceptable threshold)

---

## Test Case 18: Verify Add Place API with Incorrect HTTP Method
**Priority:** Medium  
**Type:** Negative Test

### Test Steps
1. Prepare valid request payload
2. Send request using GET method instead of POST
3. Verify response

### Expected Results
- Status Code: `405` (Method Not Allowed)
- Error message indicating POST method is required

---

## Test Case 19: Verify Add Place API with Missing Content-Type Header
**Priority:** Medium  
**Type:** Negative Test

### Test Steps
1. Prepare valid request payload
2. Send POST request WITHOUT `Content-Type: application/json` header
3. Verify response

### Expected Results
- Status Code: `415` (Unsupported Media Type) OR `400` (Bad Request)
- Error message indicating Content-Type header is required

---

## Test Case 20: Verify Add Place API with Invalid JSON Format
**Priority:** High  
**Type:** Negative Test

### Test Steps
1. Prepare request with malformed JSON:
```json
{
  "location": {
    "lat": -38.383494,
    "lng": 33.427362
  },
  "accuracy": 50,
  "name": "Test Place"
  "phone_number": "(+91) 123 456 7890"  // Missing comma
}
```
2. Send POST request
3. Verify response

### Expected Results
- Status Code: `400` (Bad Request)
- Error message indicating invalid JSON format or parse error

---

## Test Case 21: Verify Place ID is Unique for Each Request
**Priority:** High  
**Type:** Functional Test

### Test Steps
1. Send first valid request and capture `place_id`
2. Send second valid request with different data
3. Compare both `place_id` values

### Expected Results
- Both requests return Status Code `200`
- Each `place_id` should be unique (not the same)

---

## Test Case 22: Verify Add Place API with Boundary Latitude Values
**Priority:** Medium  
**Type:** Boundary Test

### Test Steps
1. Test with minimum valid latitude: `-90`
2. Test with maximum valid latitude: `90`
3. Test with just below minimum: `-90.1`
4. Test with just above maximum: `90.1`

### Expected Results
- `-90` and `90`: Status Code `200` (valid boundary values)
- `-90.1` and `90.1`: Status Code `400` (invalid values)

---

## Test Case 23: Verify Add Place API with Boundary Longitude Values
**Priority:** Medium  
**Type:** Boundary Test

### Test Steps
1. Test with minimum valid longitude: `-180`
2. Test with maximum valid longitude: `180`
3. Test with just below minimum: `-180.1`
4. Test with just above maximum: `180.1`

### Expected Results
- `-180` and `180`: Status Code `200` (valid boundary values)
- `-180.1` and `180.1`: Status Code `400` (invalid values)

---

## Test Case 24: Verify Add Place API Response Headers
**Priority:** Low  
**Type:** Functional Test

### Test Steps
1. Send valid POST request
2. Verify response headers

### Expected Results
- Status Code: `200`
- Response headers should include:
  - `Content-Type: application/json`
  - `Connection: keep-alive` (or similar)
  - Appropriate server headers

---

## Test Case 25: Verify Add Place API with Unicode Characters in Name
**Priority:** Low  
**Type:** Positive Test

### Test Steps
1. Prepare request with Unicode characters in name (e.g., Chinese, Arabic, Emoji):
```json
{
  "location": {
    "lat": -38.383494,
    "lng": 33.427362
  },
  "accuracy": 50,
  "name": "测试地点 مكان 🏠",
  "phone_number": "(+91) 123 456 7890",
  "address": "Test Address",
  "types": ["test"],
  "website": "http://test.com",
  "language": "en-US"
}
```
2. Send POST request
3. Verify response

### Expected Results
- Status Code: `200`
- Unicode characters should be properly stored and returned in response

---

## Notes for Testers
1. **Always verify log files** at `src/test/resources/log.txt` for request/response details
2. **Check API documentation** for any specific validation rules not covered here
3. **Update expected results** based on actual API behavior during testing
4. **Report any discrepancies** between expected and actual results
5. **Test with different environments** (dev, staging, production) if applicable

---

## Test Execution Summary Template

| Test Case ID | Test Case Description | Status | Comments |
|--------------|----------------------|--------|----------|
| TC-01 | Valid Data | | |
| TC-02 | Minimum Required Fields | | |
| TC-03 | No API Key | | |
| TC-04 | Invalid API Key | | |
| TC-05 | Missing Location | | |
| TC-06 | Invalid Latitude | | |
| TC-07 | Invalid Longitude | | |
| TC-08 | Missing Name | | |
| TC-09 | Empty Name | | |
| TC-10 | Invalid Phone Format | | |
| TC-11 | Invalid Website URL | | |
| TC-12 | Negative Accuracy | | |
| TC-13 | Empty Types Array | | |
| TC-14 | Very Long Name | | |
| TC-15 | Special Characters | | |
| TC-16 | Duplicate Place | | |
| TC-17 | Response Time | | |
| TC-18 | Wrong HTTP Method | | |
| TC-19 | Missing Content-Type | | |
| TC-20 | Invalid JSON | | |
| TC-21 | Unique Place ID | | |
| TC-22 | Boundary Latitude | | |
| TC-23 | Boundary Longitude | | |
| TC-24 | Response Headers | | |
| TC-25 | Unicode Characters | | |

**Status Values:** Pass / Fail / Blocked / Not Executed
