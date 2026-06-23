package rahulshettyacademy.book_tests;

import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.book_tests.base.BookBaseTest;
import rahulshettyacademy.models.book_apis.response.AddBookResponse;
import rahulshettyacademy.models.book_apis.response.Book;
import rahulshettyacademy.utilities.JSONUtility;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.*;

public class AddBookTest2  extends BookBaseTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private String ID;
    HashMap<String,String> payloadMap;


    @Test(dataProvider = "getAddBookData")
    public void addBookTest(String testCase,String payload){

       payloadMap = objectMapper.readValue(payload, new TypeReference<HashMap<String,String>>() {});
       payloadMap.put("aisle",JSONUtility.generateRandomNumber());
       ID = payloadMap.get("isbn")+payloadMap.get("aisle");

       RequestSpecification request = given().log().all().spec(requestSpecification).body(payload);
       AddBookResponse addBookResponse = request.when().post("Library/Addbook.php").then().log().all().spec(responseSpecification).extract().response().as(AddBookResponse.class);


       Assert.assertEquals(addBookResponse.getMsg(),"successfully added");
       Assert.assertEquals(addBookResponse.getID(), ID);

    }


    @Test(dependsOnMethods = "addBookTest")
    public void getBookByIdTest(){

        RequestSpecification request = given().log().all().spec(requestSpecification).queryParam("ID",ID);
        Book book = request.when().get("/Library/GetBook.php").then().log().all().spec(responseSpecification).extract().as(Book.class);

        Assert.assertEquals(book.getBook_name(),payloadMap.get("name"));
        Assert.assertEquals(book.getAuthor(),payloadMap.get("author"));


    }

    @DataProvider
    public Object[][] getAddBookData(){

        try{

            List<HashMap<String,Object>> addBookDatas =    JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/book_apis/add/AddBookTestdata.json");
            Object[][] addBookDataArr = new Object[1][2];
            for(int i =0; i < addBookDatas.size(); i++){
              HashMap<String,Object> addBookDataMap = addBookDatas.get(i);
              addBookDataArr[i][0] = addBookDataMap.get("testCase");
              addBookDataMap.remove("testCase");
              addBookDataArr[i][1] = objectMapper.writeValueAsString(addBookDataMap);
            }

            return addBookDataArr;

        }catch (IOException i){
            i.printStackTrace();
        }

        return null;

    }



}
