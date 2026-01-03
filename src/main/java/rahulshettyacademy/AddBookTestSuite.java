package rahulshettyacademy;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import rahulshettyacademy.utilities.AddBodyUtility;
import rahulshettyacademy.utilities.JSONUtility;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class AddBookTestSuite {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://216.10.245.166";
    }

    @Test(priority = 1, description = "TC01: Verify successful book addition with valid data")
    public void testAddBookWithValidData() {
        String uniqueIsbn = "TC01" + System.currentTimeMillis();
        
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody("Learn Selenium WebDriver", uniqueIsbn, "123", "Rahul Shetty"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        String msg = JSONUtility.getJsonValueStringFromPath(response, "Msg");
        String ID = JSONUtility.getJsonValueStringFromPath(response, "ID");

        Assert.assertEquals(msg, "successfully added");
        Assert.assertNotNull(ID);
        Assert.assertTrue(ID.contains(uniqueIsbn));
    }

    @Test(priority = 2, description = "TC02: Verify book addition with special characters in name")
    public void testAddBookWithSpecialCharactersInName() {
        String uniqueIsbn = "TC02" + System.currentTimeMillis();
        
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody("Book Name @#$%^&*()_+-=[]{}|;:',.<>?/", uniqueIsbn, "456", "Author Name"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        String msg = JSONUtility.getJsonValueStringFromPath(response, "Msg");
        Assert.assertEquals(msg, "successfully added");
    }

    @Test(priority = 3, description = "TC03: Verify book addition with numeric values in text fields")
    public void testAddBookWithNumericValuesInTextFields() {
        String uniqueIsbn = "TC03" + System.currentTimeMillis();
        
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody("12345", uniqueIsbn, "789", "67890"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        String msg = JSONUtility.getJsonValueStringFromPath(response, "Msg");
        Assert.assertEquals(msg, "successfully added");
    }

    @Test(priority = 4, description = "TC04: Verify book addition with alphanumeric ISBN and aisle")
    public void testAddBookWithAlphanumericIsbnAndAisle() {
        String uniqueIsbn = "ISBN" + UUID.randomUUID().toString().substring(0, 8);
        
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody("Test Automation Book", uniqueIsbn, "A1B2", "Test Author"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        String msg = JSONUtility.getJsonValueStringFromPath(response, "Msg");
        Assert.assertEquals(msg, "successfully added");
    }

    @Test(priority = 5, description = "TC05: Verify book addition with long text values")
    public void testAddBookWithLongTextValues() {
        String longName = "A".repeat(500);
        String longAuthor = "B".repeat(500);
        String uniqueIsbn = "TC05" + System.currentTimeMillis();
        
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody(longName, uniqueIsbn, "999", longAuthor))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response().asString();

        String msg = JSONUtility.getJsonValueStringFromPath(response, "Msg");
        Assert.assertEquals(msg, "successfully added");
    }

    @Test(priority = 6, description = "TC06: Verify book addition with minimum length values")
    public void testAddBookWithMinimumLengthValues() {
        String uniqueIsbn = "A";
        String uniqueAisle = String.valueOf(System.currentTimeMillis());
        
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody("A", uniqueIsbn, uniqueAisle, "B"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        String msg = JSONUtility.getJsonValueStringFromPath(response, "Msg");
        Assert.assertEquals(msg, "successfully added");
    }

    @Test(priority = 7, description = "TC07: Verify duplicate book addition returns error")
    public void testAddDuplicateBook() {
        String duplicateIsbn = "DUP";
        String duplicateAisle = "999";

        given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody("Duplicate Book Test", duplicateIsbn, duplicateAisle, "Author"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .assertThat().statusCode(200);

        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody("Duplicate Book Test", duplicateIsbn, duplicateAisle, "Author"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response();

        String msg = JSONUtility.getJsonValueStringFromPath(response.asString(), "msg");
        Assert.assertTrue(msg.contains("already exist") || msg.contains("duplicate"), 
            "Expected error message for duplicate book but got: " + msg);
    }

    @Test(priority = 8, description = "TC08: Verify book addition with empty name field")
    public void testAddBookWithEmptyName() {
        String uniqueIsbn = "TC08" + System.currentTimeMillis();
        
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody("", uniqueIsbn, "100", "Author"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 404 || statusCode == 422, 
            "Expected error status code but got: " + statusCode);
    }

    @Test(priority = 9, description = "TC09: Verify book addition with empty ISBN field")
    public void testAddBookWithEmptyIsbn() {
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody("Book Name", "", "100", "Author"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 404 || statusCode == 422, 
            "Expected error status code but got: " + statusCode);
    }

    @Test(priority = 10, description = "TC10: Verify book addition with empty aisle field")
    public void testAddBookWithEmptyAisle() {
        String uniqueIsbn = "TC10" + System.currentTimeMillis();
        
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody("Book Name", uniqueIsbn, "", "Author"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 404 || statusCode == 422, 
            "Expected error status code but got: " + statusCode);
    }

    @Test(priority = 11, description = "TC11: Verify book addition with empty author field")
    public void testAddBookWithEmptyAuthor() {
        String uniqueIsbn = "TC11" + System.currentTimeMillis();
        
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody("Book Name", uniqueIsbn, "100", ""))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 404 || statusCode == 422, 
            "Expected error status code but got: " + statusCode);
    }

    @Test(priority = 12, description = "TC12: Verify book addition without name field")
    public void testAddBookWithoutNameField() {
        String uniqueIsbn = "TC12" + System.currentTimeMillis();
        
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBodyMissingField("name", "", uniqueIsbn, "100", "Author"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 404 || statusCode == 422, 
            "Expected error status code but got: " + statusCode);
    }

    @Test(priority = 13, description = "TC13: Verify book addition without ISBN field")
    public void testAddBookWithoutIsbnField() {
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBodyMissingField("isbn", "Book Name", "", "100", "Author"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 404 || statusCode == 422, 
            "Expected error status code but got: " + statusCode);
    }

    @Test(priority = 14, description = "TC14: Verify book addition without aisle field")
    public void testAddBookWithoutAisleField() {
        String uniqueIsbn = "TC14" + System.currentTimeMillis();
        
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBodyMissingField("aisle", "Book Name", uniqueIsbn, "", "Author"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 404 || statusCode == 422, 
            "Expected error status code but got: " + statusCode);
    }

    @Test(priority = 15, description = "TC15: Verify book addition without author field")
    public void testAddBookWithoutAuthorField() {
        String uniqueIsbn = "TC15" + System.currentTimeMillis();
        
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBodyMissingField("author", "Book Name", uniqueIsbn, "100", ""))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 404 || statusCode == 422, 
            "Expected error status code but got: " + statusCode);
    }

    @Test(priority = 16, description = "TC16: Verify book addition with invalid JSON format")
    public void testAddBookWithInvalidJson() {
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body("{invalid json}")
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 500, 
            "Expected error status code for invalid JSON but got: " + statusCode);
    }

    @Test(priority = 17, description = "TC17: Verify book addition without Content-Type header")
    public void testAddBookWithoutContentTypeHeader() {
        String uniqueIsbn = "TC17" + System.currentTimeMillis();
        
        Response response = given().log().all()
                .body(AddBodyUtility.getAddBookBody("Book Name", uniqueIsbn, "100", "Author"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 415 || statusCode == 400 || statusCode == 200, 
            "Expected specific status code but got: " + statusCode);
    }

    @Test(priority = 18, description = "TC18: Verify book addition with incorrect Content-Type")
    public void testAddBookWithIncorrectContentType() {
        String uniqueIsbn = "TC18" + System.currentTimeMillis();
        
        Response response = given().log().all()
                .header("Content-Type", "text/plain")
                .body(AddBodyUtility.getAddBookBody("Book Name", uniqueIsbn, "100", "Author"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 415 || statusCode == 400 || statusCode == 200, 
            "Expected specific status code but got: " + statusCode);
    }

    @Test(priority = 19, description = "TC19: Verify book addition with null values")
    public void testAddBookWithNullValues() {
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body("{\"name\":null,\"isbn\":null,\"aisle\":null,\"author\":null}")
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 404 || statusCode == 422, 
            "Expected error status code for null values but got: " + statusCode);
    }

    @Test(priority = 20, description = "TC20: Verify book addition with Unicode characters")
    public void testAddBookWithUnicodeCharacters() {
        String uniqueIsbn = "TC20" + System.currentTimeMillis();
        
        String response = given().log().all()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(AddBodyUtility.getAddBookBody("书名测试 日本語 한국어", uniqueIsbn, "100", "作者 著者 저자"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response().asString();

        String msg = JSONUtility.getJsonValueStringFromPath(response, "Msg");
        Assert.assertEquals(msg, "successfully added");
    }

    @Test(priority = 21, description = "TC21: Verify response time is within acceptable limits")
    public void testAddBookResponseTime() {
        String uniqueIsbn = "TC21" + System.currentTimeMillis();
        
        long startTime = System.currentTimeMillis();
        
        given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody("Response Time Test", uniqueIsbn, "100", "Author"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .assertThat().statusCode(200);
        
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        
        Assert.assertTrue(responseTime < 5000, 
            "Response time exceeded 5 seconds. Actual: " + responseTime + "ms");
    }

    @Test(priority = 22, description = "TC22: Verify response contains all expected fields")
    public void testAddBookResponseStructure() {
        String uniqueIsbn = "TC22" + System.currentTimeMillis();
        
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody("Structure Test", uniqueIsbn, "100", "Author"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        Assert.assertTrue(response.contains("Msg") || response.contains("msg"), 
            "Response should contain 'Msg' field");
        Assert.assertTrue(response.contains("ID") || response.contains("id"), 
            "Response should contain 'ID' field");
    }

    @Test(priority = 23, description = "TC23: Verify book addition with whitespace in fields")
    public void testAddBookWithWhitespaceValues() {
        String uniqueIsbn = "TC23" + System.currentTimeMillis();
        
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody("   ", uniqueIsbn, "100", "   "))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 200, 
            "Expected specific status code but got: " + statusCode);
    }

    @Test(priority = 24, description = "TC24: Verify ID format in successful response")
    public void testAddBookIdFormat() {
        String uniqueIsbn = "TC24";
        String uniqueAisle = String.valueOf(System.currentTimeMillis());
        
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody("ID Format Test", uniqueIsbn, uniqueAisle, "Author"))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        String ID = JSONUtility.getJsonValueStringFromPath(response, "ID");
        Assert.assertNotNull(ID, "ID should not be null");
        Assert.assertTrue(ID.length() > 0, "ID should not be empty");
        Assert.assertEquals(ID, uniqueIsbn + uniqueAisle, 
            "ID should be combination of ISBN and aisle");
    }

    @Test(priority = 25, description = "TC25: Verify book addition with SQL injection attempt")
    public void testAddBookWithSqlInjection() {
        String sqlInjection = "'; DROP TABLE books; --";
        String uniqueAisle = String.valueOf(System.currentTimeMillis());
        
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body(AddBodyUtility.getAddBookBody(sqlInjection, "TC25", uniqueAisle, sqlInjection))
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 200 || statusCode == 400, 
            "API should handle SQL injection safely. Status code: " + statusCode);
    }
}
