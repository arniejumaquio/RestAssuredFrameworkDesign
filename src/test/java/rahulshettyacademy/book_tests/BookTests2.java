package rahulshettyacademy.book_tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.pojo_classes.book_apis.request.AddBookRequest;
import rahulshettyacademy.pojo_classes.book_apis.request.DeleteBookRequest;
import rahulshettyacademy.pojo_classes.book_apis.response.Book;
import rahulshettyacademy.pojo_classes.book_apis.response.DeleteBookErrorResponse;
import rahulshettyacademy.pojo_classes.book_apis.response.DeleteBookResponse;
import rahulshettyacademy.utilities.JSONUtility;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BookTests2 {

    private  String ID;

    @Test(dataProvider = "getAddBookData",priority = 1)
    public void addBookTest(HashMap<String,String> testData){

        RestAssured.baseURI ="http://216.10.245.166/";
        AddBookRequest addBookRequest = new AddBookRequest(testData.get("name"),testData.get("isbn"),testData.get("aisle"),testData.get("author"));
        RequestSpecification request = given().log().all().body(addBookRequest);
        Response response = request.when().post("Library/Addbook.php").then().extract().response();

        ID = JSONUtility.getJsonValueStringFromPath(response.asString(),"ID");

        Assert.assertEquals(JSONUtility.getJsonValueStringFromPath(response.asString(),"Msg"),"successfully added");
        Assert.assertEquals(JSONUtility.getJsonValueStringFromPath(response.asString(),"ID") , testData.get("isbn")+testData.get("aisle"));


    }

    @Test(dataProvider = "getAddBookData",priority = 2)
    public void getBookByAuthorNameTest(HashMap<String,String> testData){

        RestAssured.baseURI ="http://216.10.245.166/";
        RequestSpecification request = given().log().all().queryParams("AuthorName",testData.get("author"));
        Book[] books = request.when().get("Library/GetBook.php").then().log().all().extract().response().as(Book[].class);
        for(int i = 0; i < books.length;i++){

            if(ID.equalsIgnoreCase( books[i].getIsbn()+books[i].getAisle() )){

                if(books[i].getBook_name().equalsIgnoreCase(testData.get("author"))){
                    Assert.assertTrue(true);
                    break;
                }

            }
        }

    }


    @Test(priority = 3)
    public void deleteBookTest(){

        RestAssured.baseURI ="http://216.10.245.166/";

        DeleteBookRequest deleteBookRequest = new DeleteBookRequest(ID);
        RequestSpecification request = given().log().all().body(deleteBookRequest);
        DeleteBookResponse response = request.when().post("Library/DeleteBook.php").then().log().all().extract().response().as(DeleteBookResponse.class);

        Assert.assertEquals(response.getMsg(),"book is successfully deleted");

    }


    @Test(dataProvider = "getAddBookData",priority = 4)
    public void getBookByAuthorNameTest2(HashMap<String,String> testData){

        RestAssured.baseURI ="http://216.10.245.166/";
        RequestSpecification request = given().log().all().queryParams("AuthorName",testData.get("author"));
        DeleteBookErrorResponse response = request.when().get("Library/GetBook.php").then().log().all().extract().response().as(DeleteBookErrorResponse.class);

        Assert.assertEquals(response.getMsg(),"The book by requested bookid / author name does not exists!");

    }



    @DataProvider
    public Object[][] getAddBookData() throws IOException {

     List<HashMap<String,String>> listOfHashMapDatas =  JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/AddBookTestdata.json");

        return new Object[][]{{listOfHashMapDatas.get(0)}};

    }

}
