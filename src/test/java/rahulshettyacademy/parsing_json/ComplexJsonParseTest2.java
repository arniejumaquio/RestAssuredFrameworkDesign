package rahulshettyacademy.parsing_json;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.given;

public class ComplexJsonParseTest2 {

    RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("http://216.10.245.166/")
            .setContentType(ContentType.JSON).build();

    ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
            .expectStatusCode(200).build();

    @Test
    public void complexJsonParseTest2(){

        HashMap<String,Object> queryParams = new HashMap<String, Object>();
        queryParams.put("AuthorName","rahulshetty");

       Response response = given().log().all().spec(requestSpecification).queryParams(queryParams).
         when().get("Library/GetBook.php").
         then().spec(responseSpecification).extract().response();

        JsonPath jsonPath = new JsonPath(response.asString());
        int bookCounts = jsonPath.getInt("size()");

        String expectedBookName = "RestAssured";
        String expectedIsbn = "restAPI";
        String expectedAisle = "345";

        int occurence = 0;

        for(int i = 0; i < bookCounts; i++){

                String actualBookName = jsonPath.get("["+i+"].book_name");
                String actualIsbn = jsonPath.get("["+i+"].isbn");
                String actualAisle = jsonPath.get("["+i+"].aisle");

                if(actualBookName.equalsIgnoreCase(expectedBookName) && actualIsbn.equalsIgnoreCase(expectedIsbn) && actualAisle.equalsIgnoreCase(expectedAisle)){
                     occurence++;
                }

        }

        System.out.println("Occurence = "+occurence);

    }

}
