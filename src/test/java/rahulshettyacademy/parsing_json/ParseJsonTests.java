package rahulshettyacademy.parsing_json;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class ParseJsonTests {

   RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
           .setContentType(ContentType.JSON).build();

   ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200)
           .expectContentType(ContentType.JSON).build();

    @Test
    public void parseJsonTest(){

        RequestSpecification request = given().log().all().spec(requestSpecification).queryParam("AuthorName","Test Author");

        Response response = request.when().get("/Library/GetBook.php").then().spec(responseSpecification).extract().response();
        //assertions
        int actualStatusCode =response.getStatusCode();
        int expectedStatusCode = 200;
        Assert.assertEquals(actualStatusCode,expectedStatusCode);

        JsonPath jsonPath = new JsonPath(response.asString());
        int arraySize = jsonPath.get("size()");
        for(int i = 0; i < arraySize; i++){

            if( jsonPath.get("["+i+"].isbn").equals("SS670")){
                if(jsonPath.get("["+i+"].aisle").equals("617")){
                    System.out.println((String)jsonPath.get("["+i+"].book_name"));
                    System.out.println((String)jsonPath.get("["+i+"].isbn"));
                    System.out.println((String)jsonPath.get("["+i+"].aisle"));
                }
            }
        }

    }

}
