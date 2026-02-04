package rahulshettyacademy;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.utilities.AddBodyUtility;
import rahulshettyacademy.utilities.ExcelUtility;
import rahulshettyacademy.utilities.JSONUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class AddBookTestTwo {

    RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("http://216.10.245.166").setContentType(ContentType.JSON).build();
    ResponseSpecification responseSpecification =  new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
    ArrayList<String> testData ;

    @Test(priority = 1)
    public void addBookTest() throws IOException {

        testData =  ExcelUtility.getDataFromExcel("BookTestData.xlsx","Book","TC Name","Add Book");
        HashMap<String,Object> body =  AddBodyUtility.getAddBookBody(testData);
        RequestSpecification request = given().log().all().spec(requestSpecification).body(body);

        Response response = request.when().post("/Library/Addbook.php").
                then().log().all().spec(responseSpecification).extract().response();

        String msg = JSONUtility.getJsonValueStringFromPath(response.asString(),"Msg");
        String ID = JSONUtility.getJsonValueStringFromPath(response.asString(),"ID");

        Assert.assertEquals(msg,"successfully added");
        Assert.assertEquals(ID,testData.get(2)+testData.get(3));



    }


    @Test(priority = 2)
    public void deleteBookTest(){

        RequestSpecification request =  given().log().all().spec(requestSpecification).body(AddBodyUtility.getDeleteBookBody(testData.get(2)+testData.get(3)));

        Response response =request.when().delete("/Library/DeleteBook.php").
                then().log().all().extract().response();

        String msg = JSONUtility.getJsonValueStringFromPath(response.asString(),"msg");
        Assert.assertEquals(msg,"book is successfully deleted");

    }


    @Test(priority = 3)
    public void getBookByIDTest(){

        RequestSpecification request = given().log().all().spec(requestSpecification).queryParam("ID",testData.get(2)+testData.get(3));
        Response response =  request.when().get("/Library/GetBook.php").
                then().log().all().extract().response();

        int actualStatusCode = response.getStatusCode();
       String actualMsg = JSONUtility.getJsonValueStringFromPath(response.asString(),"msg");
       Assert.assertEquals(actualMsg,"The book by requested bookid / author name does not exists!");
       Assert.assertEquals(actualStatusCode,404);


    }

}
