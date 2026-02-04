package rahulshettyacademy;

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

       Response response = given().spec(requestSpecification).queryParams(queryParams).
         when().get("Library/GetBook.php").
         then().spec(responseSpecification).extract().response();

        JsonPath jsonPath = new JsonPath(response.asString());
        int bookCounts = jsonPath.getInt("size()");

        for(int i = 0; i < bookCounts; i++){

            String bookName = jsonPath.getString("["+i+"].book_name");
            if(bookName.contains("Test")){
                //print all the book name,isbn and aisle
                String isbn = jsonPath.getString("["+i+"].isbn");
                String aisle = jsonPath.getString("["+i+"].aisle");

                System.out.println(bookName);
                System.out.println(isbn);
                System.out.println(aisle);

            }

        }

        System.out.println("--- ----  ----- ---- ------");
        String expectedIsbn = "acdc";
        String expectedAisle = "8905";

        for(int i = 0; i < bookCounts; i++){

           if(jsonPath.getString("["+i+"].isbn").equalsIgnoreCase(expectedIsbn) && jsonPath.getString("["+i+"].aisle").equalsIgnoreCase(expectedAisle)){

               //print the book name ,isbn and aisle
               System.out.println(jsonPath.getString("["+i+"].book_name"));
               System.out.println(jsonPath.getString("["+i+"].isbn"));
               System.out.println(jsonPath.getString("["+i+"].aisle"));

           }

        }


    }

}
