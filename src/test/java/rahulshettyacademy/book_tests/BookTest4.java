package rahulshettyacademy.book_tests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.base.BaseTest;
import rahulshettyacademy.pojo_classes.book_apis.request.DeleteBookRequest;
import rahulshettyacademy.pojo_classes.book_apis.response.AddBookResponse;
import rahulshettyacademy.pojo_classes.book_apis.response.Book;
import rahulshettyacademy.pojo_classes.book_apis.response.DeleteBookResponse;
import rahulshettyacademy.pojo_classes.book_apis.response.GetBookByIdErrorResponse;
import rahulshettyacademy.utilities.JSONUtility;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import static io.restassured.RestAssured.given;

public class BookTest4 extends BaseTest {

   private RequestSpecification request;
   private Response response;
   private String id;
   private  HashMap<String,Object> payload ;

    @Test(dataProvider = "getAddBookTestData",priority = 1)
    public void addBookTest(HashMap<String,Object> addBookTestData){

        payload = (HashMap<String, Object>) addBookTestData.get("request");
        payload.put("aisle",JSONUtility.generateRandomNumber());

       request = given().log().all().spec(requestSpecification).body(payload);
       AddBookResponse addBookResponse =request.when().post("Library/Addbook.php").then().log().all().spec(responseSpecification).extract().response().as(AddBookResponse.class);
       id = addBookResponse.getID();

       Assert.assertEquals(addBookResponse.getMsg(),"successfully added");
       Assert.assertEquals(addBookResponse.getID(),(String)payload.get("isbn")+(String)payload.get("aisle"));

    }


    @Test(priority = 2)
    public void getBookByIdTest(){

        request = given().log().all().spec(requestSpecification).queryParam("ID",id);
        Book[] books  = request.when().get("Library/GetBook.php").then().log().all().spec(responseSpecification).extract().response().as(Book[].class);

        Assert.assertEquals(books[0].getBook_name(),payload.get("name"));
        Assert.assertEquals(books[0].getIsbn(),payload.get("isbn"));
        Assert.assertNotNull(books[0].getAisle());
        Assert.assertEquals(books[0].getAuthor(),payload.get("author"));

    }

    @Test(priority = 3)
    public void deleteBookTest(){

        DeleteBookRequest deleteBookRequest = new DeleteBookRequest(id);
        request = given().log().all().spec(requestSpecification).body(deleteBookRequest);
        DeleteBookResponse deleteBookResponse =  request.when().delete("Library/DeleteBook.php").then().log().all().spec(responseSpecification).extract().response().as(DeleteBookResponse.class);

        Assert.assertEquals(deleteBookResponse.getMsg(),"book is successfully deleted");
        deleteBookResponse = request.when().delete("Library/DeleteBook.php").then().log().all().assertThat().statusCode(404).extract().response().as(DeleteBookResponse.class);
        Assert.assertEquals(deleteBookResponse.getMsg(),"Delete Book operation failed, looks like the book doesnt exists");
    }


    @Test(priority = 4)
    public void getBookByIdTest2(){

        request = given().log().all().spec(requestSpecification).queryParam("ID",id);
        GetBookByIdErrorResponse getBookByIdErrorResponse =   request.when().get("Library/GetBook.php").then().log().all().assertThat().statusCode(404).extract().response().as(GetBookByIdErrorResponse.class);

        Assert.assertEquals(getBookByIdErrorResponse.getMsg(),"The book by requested bookid / author name does not exists!");

    }


    @DataProvider
    public Object[][] getAddBookTestData(){

        try {

            List<HashMap<String, Object>> addBookTestDatas = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/book_apis/add/EndToEndAddBookTestData.json");
            return new Object[][]{ {addBookTestDatas.get(0)} };

        }catch (IOException i){
            i.printStackTrace();
        }

        return null;
    }


}
