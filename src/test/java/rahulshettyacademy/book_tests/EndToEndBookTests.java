package rahulshettyacademy.book_tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.base.BaseTest;
import rahulshettyacademy.utilities.AddBodyUtility;
import rahulshettyacademy.utilities.JSONUtility;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EndToEndBookTests extends BaseTest {

    ObjectMapper objectMapper = new ObjectMapper();
    RequestSpecification request;
    Response response;
    String ID;

    @Test(dataProvider = "getAddBookTestData",priority = 1)
    public void addBookTest(String testCase,String payload){

        request = given().log().all().spec(requestSpecification).body(payload);
        response = request.when().post("/Library/Addbook.php").then().log().all().extract().response();
        ID = (String)JSONUtility.getJsonValueFromPath(response.asString(),"ID");


        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(JSONUtility.getJsonValueFromPath(response.asString(),"Msg"),"successfully added");
        HashMap<String,Object> payloadMap = objectMapper.readValue(payload, new TypeReference<HashMap<String,Object>>() {});
        Assert.assertEquals(ID,payloadMap.get("isbn").toString()+payloadMap.get("aisle").toString());


    }

    @Test(dataProvider = "getAddBookTestData",priority = 2)
    public void getBookByIdTest(String testCase,String payload){

        request = given().log().all().spec(requestSpecification).queryParam("ID",ID);
        response = request.when().get("/Library/GetBook.php").then().log().all().extract().response();

        HashMap<String,String> payloadMap = objectMapper.readValue(payload, new TypeReference<HashMap<String, String>>() {});
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(JSONUtility.getJsonValueFromPath(response.asString(),"[0].book_name"),payloadMap.get("name"));
        Assert.assertEquals(JSONUtility.getJsonValueFromPath(response.asString(),"[0].isbn"),payloadMap.get("isbn"));
        Assert.assertEquals(JSONUtility.getJsonValueFromPath(response.asString(),"[0].aisle"),payloadMap.get("aisle"));
        Assert.assertEquals(JSONUtility.getJsonValueFromPath(response.asString(),"[0].author"),payloadMap.get("author"));


    }


    @Test(priority = 3)
    public void deleteBookTest(){

        request = given().when().log().all().spec(requestSpecification).body(AddBodyUtility.getDeleteBookBody(ID));
        response = request.when().post("/Library/DeleteBook.php").then().log().all().extract().response();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(JSONUtility.getJsonValueFromPath(response.asString(),"msg"),"book is successfully deleted");

        response = request.when().post("/Library/DeleteBook.php").then().log().all().extract().response();

        Assert.assertEquals(response.getStatusCode(),404);
        Assert.assertEquals(JSONUtility.getJsonValueFromPath(response.asString(),"msg"),"Delete Book operation failed, looks like the book doesnt exists");


    }


    @Test(priority = 4)
    public void getBookByIdTest2(){

        request = given().log().all().spec(requestSpecification).queryParam("ID",ID);
        response = request.when().get("/Library/GetBook.php").then().log().all().extract().response();

       Assert.assertEquals(response.getStatusCode(),404);
       Assert.assertEquals(JSONUtility.getJsonValueFromPath(response.asString(),"msg"),"The book by requested bookid / author name does not exists!");


    }


    @DataProvider
    public Object[][] getAddBookTestData()  {

        try {

            List<HashMap<String,Object>> testData =   JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/book_apis/add/EndToEndAddBookTestData.json");
            Object[][] testDataArr = new Object[testData.size()][2];

            for(int i =0; i < testData.size(); i++){

                HashMap<String,Object> eachMap = testData.get(i);
                testDataArr[i][0] = eachMap.get("testCase");
                eachMap.remove("testCase");

                ObjectMapper objectMapper = new ObjectMapper();
                testDataArr[i][1] = objectMapper.writeValueAsString(eachMap);

            }

            return testDataArr;

        }catch (IOException i){
            System.out.println("IOException occur");
        }

        return null;
    }


}
