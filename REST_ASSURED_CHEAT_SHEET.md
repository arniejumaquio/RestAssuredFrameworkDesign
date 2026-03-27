# REST Assured Java Automation - Interview Cheat Sheet

---

## 1. REST Assured Basics

### Maven Dependency
```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.4.0</version>
    <scope>test</scope>
</dependency>
```

### Static Imports (Must Know)
```java
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
```

### BDD Structure: Given → When → Then
```java
given()       // request setup (headers, body, params, auth)
    .when()   // HTTP method (get, post, put, delete, patch)
    .then()   // assertions (statusCode, body, headers)
```

---

## 2. HTTP Methods

```java
// GET
given().when().get("/api/users").then().statusCode(200);

// POST
given().body(payload).when().post("/api/users").then().statusCode(201);

// PUT (full update)
given().body(payload).when().put("/api/users/1").then().statusCode(200);

// PATCH (partial update)
given().body(payload).when().patch("/api/users/1").then().statusCode(200);

// DELETE
given().when().delete("/api/users/1").then().statusCode(204);
```

---

## 3. Request Configuration

### Base URI & Base Path
```java
RestAssured.baseURI = "https://api.example.com";
RestAssured.basePath = "/v1";
```

### Headers
```java
given()
    .header("Content-Type", "application/json")
    .header("Authorization", "Bearer token123")
    // OR multiple headers
    .headers("Content-Type", "application/json", "Accept", "application/json")
```

### Query Parameters
```java
// GET /api/users?page=2&limit=10
given()
    .queryParam("page", 2)
    .queryParam("limit", 10)
```

### Path Parameters
```java
// GET /api/users/{id}
given()
    .pathParam("id", 123)
    .when().get("/api/users/{id}")
```

### Form Parameters
```java
given()
    .formParam("username", "admin")
    .formParam("password", "pass123")
```

### Cookies
```java
given()
    .cookie("session_id", "abc123")
```

---

## 4. Request Body / Payload

### String Body
```java
String payload = "{\"name\":\"John\", \"job\":\"developer\"}";
given().body(payload).contentType(ContentType.JSON)
```

### Map Body
```java
Map<String, Object> body = new HashMap<>();
body.put("name", "John");
body.put("job", "developer");
given().body(body).contentType(ContentType.JSON)
```

### POJO Serialization (Most Common in Frameworks)
```java
User user = new User("John", "developer");
given().body(user).contentType(ContentType.JSON)
// REST Assured auto-serializes POJO to JSON using Jackson/Gson
```

### File Body
```java
given().body(new File("payload.json")).contentType(ContentType.JSON)
```

---

## 5. Response Extraction

### Extract Entire Response
```java
Response response = given().when().get("/api/users").then().extract().response();
```

### Extract Values
```java
// Status code
int statusCode = response.getStatusCode();

// Status line
String statusLine = response.getStatusLine();

// Headers
String contentType = response.getHeader("Content-Type");
Headers allHeaders = response.getHeaders();

// Body as String
String body = response.getBody().asString();

// Body as POJO (Deserialization)
User user = response.as(User.class);

// Response time
long time = response.getTime();

// Cookies
String cookie = response.getCookie("session_id");
Map<String, String> cookies = response.getCookies();
```

---

## 6. JsonPath - Extracting JSON Values

```java
// From response directly
String name = response.jsonPath().getString("name");
int age = response.jsonPath().getInt("age");
List<String> names = response.jsonPath().getList("users.name");

// From string
JsonPath js = new JsonPath(responseString);
String value = js.getString("key");

// Nested JSON
String city = response.jsonPath().getString("address.city");

// Array access
String firstName = response.jsonPath().getString("users[0].name");

// Array size
int count = response.jsonPath().getList("users").size();

// Find with condition (GPath)
String email = response.jsonPath().getString("users.find { it.name == 'John' }.email");
List<String> filtered = response.jsonPath().getList("users.findAll { it.age > 25 }.name");
```

---

## 7. Hamcrest Matchers (Inline Assertions)

```java
given()
    .when().get("/api/users")
    .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("name", equalTo("John"))
        .body("age", greaterThan(18))
        .body("skills", hasItem("Java"))
        .body("skills", hasItems("Java", "Python"))
        .body("skills.size()", equalTo(3))
        .body("address.city", equalTo("Manila"))
        .body("users[0].name", equalTo("John"))
        .body("$", hasKey("name"))           // root has key
        .body("name", not(emptyString()))
        .body("name", containsString("Jo"))
        .body("items", hasSize(5))
        .body("price", is(29.99f))
        .body("active", is(true));
```

### Common Hamcrest Matchers
| Matcher | Description |
|---------|-------------|
| `equalTo(x)` | Exact match |
| `not(x)` | Negation |
| `hasItem(x)` | Collection contains item |
| `hasItems(x, y)` | Collection contains multiple items |
| `hasSize(n)` | Collection size |
| `hasKey(k)` | Map/JSON has key |
| `greaterThan(x)` | Numeric comparison |
| `lessThan(x)` | Numeric comparison |
| `containsString(s)` | String contains |
| `startsWith(s)` | String starts with |
| `endsWith(s)` | String ends with |
| `nullValue()` | Is null |
| `notNullValue()` | Is not null |
| `empty()` | Collection is empty |
| `everyItem(matcher)` | All items match |

---

## 8. Authentication

```java
// Basic Auth
given().auth().basic("username", "password")

// Preemptive Basic Auth (sends auth header immediately)
given().auth().preemptive().basic("username", "password")

// Bearer Token
given().header("Authorization", "Bearer " + token)

// OAuth 1.0
given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret)

// OAuth 2.0
given().auth().oauth2(accessToken)

// API Key as query param
given().queryParam("api_key", "your_key")

// API Key as header
given().header("x-api-key", "your_key")
```

---

## 9. Logging

```java
// Request logging
given().log().all()       // log everything
given().log().headers()   // log headers only
given().log().body()      // log body only
given().log().params()    // log params only

// Response logging
.then().log().all()
.then().log().body()
.then().log().status()
.then().log().ifError()                  // log only if error
.then().log().ifStatusCodeIsEqualTo(500) // conditional
```

---

## 10. Request & Response Specification (Reusable Specs)

### RequestSpecification
```java
RequestSpecification reqSpec = new RequestSpecBuilder()
    .setBaseUri("https://api.example.com")
    .setContentType(ContentType.JSON)
    .addHeader("Authorization", "Bearer token")
    .build();

given().spec(reqSpec).body(payload).when().post("/users");
```

### ResponseSpecification
```java
ResponseSpecification resSpec = new ResponseSpecBuilder()
    .expectStatusCode(200)
    .expectContentType(ContentType.JSON)
    .expectResponseTime(lessThan(3000L))
    .build();

given().when().get("/users").then().spec(resSpec);
```

---

## 11. File Upload & Download

### Upload (Multipart)
```java
given()
    .multiPart("file", new File("test.pdf"))
    .multiPart("description", "Test file")
    .when().post("/upload");
```

### Download
```java
byte[] fileBytes = given().when().get("/download/file.pdf")
    .then().extract().asByteArray();

// Or as InputStream
InputStream is = given().when().get("/download/file.pdf")
    .then().extract().asInputStream();
```

---

## 12. Serialization & Deserialization (POJO)

### POJO Class
```java
public class User {
    private String name;
    private String job;

    // Constructor
    public User(String name, String job) {
        this.name = name;
        this.job = job;
    }

    // Getters & Setters (required for deserialization)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }
}
```

### Serialization (POJO → JSON)
```java
User user = new User("John", "developer");
given().body(user).contentType(ContentType.JSON)
       .when().post("/users");
```

### Deserialization (JSON → POJO)
```java
User user = given().when().get("/users/1")
    .then().extract().response().as(User.class);
```

### Requires Jackson or Gson on classpath
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.2</version>
</dependency>
```

---

## 13. Schema Validation

```java
// JSON Schema Validation
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

given().when().get("/users/1")
    .then().body(matchesJsonSchemaInClasspath("user-schema.json"));
```

### Maven dependency for schema validation
```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>json-schema-validator</artifactId>
    <version>5.4.0</version>
    <scope>test</scope>
</dependency>
```

---

## 14. Filters & Interceptors

```java
// Global logging filter
RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

// Custom filter
RestAssured.filters((requestSpec, responseSpec, ctx) -> {
    requestSpec.header("X-Custom", "value");
    return ctx.next(requestSpec, responseSpec);
});
```

---

## 15. Handling SSL

```java
// Ignore SSL certificate validation
given().relaxedHTTPSValidation().when().get("https://self-signed.example.com");

// Or globally
RestAssured.useRelaxedHTTPSValidation();
```

---

## 16. Timeouts & Config

```java
// Connection timeout
RestAssured.config = RestAssuredConfig.config()
    .httpClient(HttpClientConfig.httpClientConfig()
        .setParam("http.connection.timeout", 5000)
        .setParam("http.socket.timeout", 5000));
```

---

## 17. Common Interview Questions & Answers

### Q: What is REST Assured?
**A:** REST Assured is a Java library for testing RESTful APIs. It provides a domain-specific language (DSL) for writing tests in a BDD (Given/When/Then) style, making HTTP requests and validating responses easy.

### Q: Difference between `body()` and `content()` ?
**A:** They are aliases. Both set the request body.

### Q: Difference between `queryParam` and `pathParam`?
**A:** `queryParam` appends `?key=value` to URL. `pathParam` replaces `{placeholder}` in URL path.

### Q: How does REST Assured serialize/deserialize?
**A:** Uses Jackson (default) or Gson library on the classpath. POJOs are auto-converted to/from JSON.

### Q: How to handle dynamic response values?
**A:** Use `extract()` to capture response, then `jsonPath()` to navigate and extract values.

### Q: What is RequestSpecification?
**A:** A reusable request configuration (base URI, headers, auth, content type) to avoid code duplication across tests.

### Q: How to chain API calls?
**A:** Extract values from one response and pass them to the next request:
```java
String token = given().body(loginPayload)
    .when().post("/login")
    .then().extract().path("token");

given().header("Authorization", "Bearer " + token)
    .when().get("/protected-resource");
```

### Q: What is the difference between `as()` and `jsonPath().get()`?
**A:** `as(Class)` deserializes the entire response to a POJO. `jsonPath().get("key")` extracts a specific field from the response.

### Q: How to validate response time?
```java
given().when().get("/api")
    .then().time(lessThan(2000L)); // milliseconds
```

### Q: How to handle XML responses?
```java
given().when().get("/api/xml")
    .then().body("root.element.name", equalTo("value"));

// Or using XmlPath
XmlPath xmlPath = response.xmlPath();
String value = xmlPath.getString("root.element.name");
```

---

## 18. Framework Design Patterns (Interview Favorites)

### Page Object Model equivalent for APIs
- **POJO classes** for request/response bodies
- **Utility classes** for common operations (JsonPath, auth, config)
- **Test data** externalized (JSON files, Excel, CSV)
- **Base test class** with common setup (baseURI, specs, auth)

### Typical Framework Structure
```
src/
├── main/java/
│   ├── pojo/              # Request/Response POJOs
│   ├── utils/             # Utility classes (JsonUtils, ConfigReader)
│   └── config/            # Configuration management
├── test/java/
│   ├── tests/             # Test classes
│   ├── testdata/          # Test data files
│   └── base/              # Base test class
├── resources/
│   ├── schemas/           # JSON schemas for validation
│   ├── testdata/          # JSON payloads, CSV, Excel
│   └── config.properties  # Environment configs
└── pom.xml
```

### Key Framework Components
1. **Config Management** - properties file for base URLs, credentials
2. **Request/Response Specs** - reusable specifications
3. **POJO Classes** - type-safe request/response handling
4. **Utility Layer** - JSON parsing, file reading, data generation
5. **Reporting** - Extent Reports, Allure
6. **Data-Driven** - TestNG DataProvider, Cucumber Examples, Excel
7. **CI/CD** - Maven Surefire plugin, Jenkins integration

---

## 19. TestNG Integration

### DataProvider for Data-Driven Testing
```java
@DataProvider(name = "userData")
public Object[][] getUserData() {
    return new Object[][] {
        {"John", "developer"},
        {"Jane", "tester"}
    };
}

@Test(dataProvider = "userData")
public void testCreateUser(String name, String job) {
    given().body(new User(name, job))
        .when().post("/users")
        .then().statusCode(201);
}
```

### TestNG Annotations Order
```
@BeforeSuite → @BeforeTest → @BeforeClass → @BeforeMethod → @Test → @AfterMethod → @AfterClass → @AfterTest → @AfterSuite
```

---

## 20. Cucumber + REST Assured (BDD)

### Feature File
```gherkin
Feature: User API
  Scenario: Create a new user
    Given the payload with name "John" and job "developer"
    When I call POST on "/api/users"
    Then the status code should be 201
    And the response should contain name "John"
```

### Step Definitions
```java
@Given("the payload with name {string} and job {string}")
public void setPayload(String name, String job) {
    request = given().body(new User(name, job)).contentType(ContentType.JSON);
}

@When("I call POST on {string}")
public void callPost(String endpoint) {
    response = request.when().post(endpoint);
}

@Then("the status code should be {int}")
public void verifyStatusCode(int code) {
    response.then().statusCode(code);
}
```

### Cucumber Expressions vs Regex
| Type | Example |
|------|---------|
| `{string}` | Matches quoted string `"value"` |
| `{int}` | Matches integer |
| `{float}` | Matches decimal |
| `{word}` | Matches single word (no spaces) |
| `(.+)` | Regex: matches any characters |
| `(\\d+)` | Regex: matches digits |

---

## 21. Quick Reference - Status Codes

| Code | Meaning |
|------|---------|
| 200 | OK |
| 201 | Created |
| 204 | No Content |
| 301 | Moved Permanently |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 405 | Method Not Allowed |
| 409 | Conflict |
| 415 | Unsupported Media Type |
| 422 | Unprocessable Entity |
| 500 | Internal Server Error |
| 502 | Bad Gateway |
| 503 | Service Unavailable |

---

## 22. Quick Reference - HTTP Methods

| Method | Idempotent | Safe | Body | Use Case |
|--------|-----------|------|------|----------|
| GET | Yes | Yes | No | Retrieve resource |
| POST | No | No | Yes | Create resource |
| PUT | Yes | No | Yes | Full update |
| PATCH | No | No | Yes | Partial update |
| DELETE | Yes | No | Optional | Delete resource |

---

## 23. Common Pitfalls in Interviews

1. **Forgetting `ContentType`** - Always set `contentType(ContentType.JSON)` for POST/PUT
2. **Static imports** - `given()`, `equalTo()`, etc. require static imports
3. **POJO needs default constructor** - For deserialization, include no-arg constructor
4. **Jackson vs Gson** - Know which serializer your project uses
5. **Greedy regex `(.+)`** - In Cucumber, spaces cause wrong parameter matching (use delimiters or keywords)
6. **`extract()` placement** - Must come after `.then()` in the chain
7. **Base URI trailing slash** - Don't double up slashes between baseURI and path
