package rahulshettyacademy;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.utilities.AddBodyUtility;
import rahulshettyacademy.utilities.JSONUtility;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class AddBookTest {

    RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("http://216.10.245.166").setContentType(ContentType.JSON).build();
    ResponseSpecification responseSpecification =  new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

    @Test(dataProvider = "Books Data Set")
    public void addBookTest(String name,String isbn,String aisle,String author){

       RequestSpecification request = given().log().all().spec(requestSpecification).body(AddBodyUtility.getAddBookBody(name,isbn,aisle,author));

        Response response = request.when().post("/Library/Addbook.php").
        then().log().all().spec(responseSpecification).extract().response();

        String msg = JSONUtility.getJsonValueStringFromPath(response.asString(),"Msg");
        String ID = JSONUtility.getJsonValueStringFromPath(response.asString(),"ID");

        Assert.assertEquals(msg,"successfully added");
        Assert.assertEquals(ID,isbn+aisle);


    }


    @Test(dataProvider = "Books Data Set")
    public void deleteBookTest(String name,String isbn,String aisle,String author){

        RequestSpecification request =  given().log().all().spec(requestSpecification).body(AddBodyUtility.getDeleteBookBody(isbn+aisle));

        Response response =request.when().delete("/Library/DeleteBook.php").
        then().log().all().spec(responseSpecification).extract().response();

        String msg = JSONUtility.getJsonValueStringFromPath(response.asString(),"msg");
        Assert.assertEquals(msg,"book is successfully deleted");

    }


    @Test(dataProvider = "Books Data Set")
    public void getBookByAuthorNameTest(String name,String isbn,String aisle,String author){

        RequestSpecification request = given().log().all().spec(requestSpecification).queryParam("AuthorName",author);
              Response response =  request.when().get("/Library/GetBook.php").
                then().log().all().spec(responseSpecification).extract().response();

       int responseArraySize = JSONUtility.getJsonValueIntFromPath(response.asString(),"size()");
       for(int i = 0; i < responseArraySize; i++){

           String actualIsbn = JSONUtility.getJsonValueStringFromPath(response.asString(),"["+i+"].isbn");
           String actualAisle = JSONUtility.getJsonValueStringFromPath(response.asString(),"["+i+"].aisle");
           String actualId = actualIsbn + actualAisle;

           if(!(actualId.equalsIgnoreCase(isbn+aisle))){
               Assert.assertTrue(true);
           }else {
               Assert.assertTrue(false);
           }

       }

    }


    @DataProvider(name="Books Data Set")
    public Object[][] getBookData(){

       return new Object[][] {{"Learn Selenium With Java","ISBN","987654327","Harry Roque",},{"Learn Appium With Java","ISBN","987654328","Harry Roque",},{"Learn RestAssured With Java","ISBN","987654329","Harry Roque",}};

    }

}
