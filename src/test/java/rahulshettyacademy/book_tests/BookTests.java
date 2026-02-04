package rahulshettyacademy.book_tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.BookAPIResources;
import rahulshettyacademy.pojo_classes.book_apis.request.AddBookRequest;
import rahulshettyacademy.pojo_classes.book_apis.request.DeleteBookRequest;
import rahulshettyacademy.pojo_classes.book_apis.response.AddBookResponse;
import rahulshettyacademy.pojo_classes.book_apis.response.Book;
import rahulshettyacademy.pojo_classes.book_apis.response.DeleteBookResponse;
import rahulshettyacademy.pojo_classes.book_apis.response.GetBookByIdErrorResponse;
import rahulshettyacademy.utilities.ExcelUtility;
import java.io.IOException;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class BookTests {

    RequestSpecification requestSpecification =  new RequestSpecBuilder().setBaseUri("http://216.10.245.166/").setContentType("application/json").build();
    ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType("application/json").build();
    String ID;

    @Test(dataProvider = "getAddBookData",priority = 1)
    public void addBookTest(String name,String isbn,String aisle,String author){

        AddBookRequest addBookRequest = new AddBookRequest(name, isbn, aisle, author);

        RequestSpecification request = given().log().all().spec(requestSpecification).body(addBookRequest);
        AddBookResponse response = request.when().post(BookAPIResources.ADD.getUrl()).then().log().all().spec(responseSpecification).extract().response().as(AddBookResponse.class);
        ID = response.getID();

        Assert.assertEquals(response.getMsg(),"successfully added");
        Assert.assertEquals(response.getID(),isbn+aisle);
    }

    @Test(dataProvider = "getAddBookData",priority = 2)
    public void getBookByIdTest(String name,String isbn,String aisle,String author){

        RequestSpecification request = given().log().all().spec(requestSpecification).queryParams("ID",ID);
        Book[] response = request.when().get(BookAPIResources.GET_BY_ID.getUrl()).then().log().all().spec(responseSpecification).extract().response().as(Book[].class);

        Assert.assertEquals(response[0].getBook_name(),name);
        Assert.assertEquals(response[0].getIsbn(),isbn);
        Assert.assertEquals(response[0].getAisle(),aisle);
        Assert.assertEquals(response[0].getAuthor(),author);

    }

    @Test(priority = 3)
    public void deleteBookTest(){

        DeleteBookRequest deleteBookRequest = new DeleteBookRequest(ID);
        RequestSpecification request = given().log().all().spec(requestSpecification).body(deleteBookRequest);
        DeleteBookResponse response = request.when().delete(BookAPIResources.DELETE.getUrl()).then().log().all().spec(responseSpecification).extract().response().as(DeleteBookResponse.class);

        Assert.assertEquals(response.getMsg(),"book is successfully deleted");

    }

    @Test(dataProvider = "getAddBookData",priority = 4)
    public void getBookByIdTest2(String name,String isbn,String aisle,String author){

        RequestSpecification request = given().log().all().spec(requestSpecification).queryParams("ID",ID);
        GetBookByIdErrorResponse response =  request.when().get(BookAPIResources.GET_BY_ID.getUrl()).then().log().all().assertThat().statusCode(404).extract().response().as(GetBookByIdErrorResponse.class);

        Assert.assertEquals(response.getMsg(),"The book by requested bookid / author name does not exists!");

    }


    @DataProvider
    public Object[][] getAddBookData() throws IOException {

       ArrayList<String> testData =  ExcelUtility.getDataFromExcel("Addbooktestdata.xlsx","Book","Test Scenario","Add Book");
       return new Object[][]{ {testData.get(1),testData.get(2),testData.get(3),testData.get(4)} };

    }

}
