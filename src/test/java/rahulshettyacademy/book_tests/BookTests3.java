package rahulshettyacademy.book_tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.BookAPIResources;
import rahulshettyacademy.utilities.JSONUtility;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class BookTests3 {

    @Test(dataProvider = "getEmptyAddBookData")
    public void emptyAddBookBodyTest(String testCase,String payload){

        Map<String,Object> header = new HashMap<String, Object>();
        header.put("Content-Type","application/json");


        RestAssured.baseURI = "http://216.10.245.166/";
        RequestSpecification request =  given().log().all().headers(header).body(payload);

        Response response =request.when().post("Library/Addbook.php").then().log().all().extract().response();

        //assert response status code and response body
        JsonPath jsonPath = new JsonPath(response.asString());
        String actualErrorMessage = jsonPath.getString("error_msg");
        String expectedErrorMessage = "field is required and cannot be empty";

        response.then().assertThat().statusCode(400);
        Assert.assertEquals(actualErrorMessage,expectedErrorMessage);

    }

    @Test(dataProvider = "getMissingAddBookBody")
    public void missingAddBookBodyTest(String testCase,String payload){

        RestAssured.baseURI = "http://216.10.245.166/";
        RequestSpecification request = given().log().all().header("Content-Type","application/json").body(payload);
        Response response = request.when().post(BookAPIResources.ADD.getUrl()).then().log().all().extract().response();

        //assert
        response.then().assertThat().statusCode(400);
        JsonPath jsonPath = new JsonPath(response.asString());
        String actualErrorMsg = jsonPath.getString("error_msg");
        String expectedErrorMsg = "field is required and cannot be missing";
        Assert.assertEquals(actualErrorMsg,expectedErrorMsg);

    }


    @Test(dataProvider = "getInvalidAddBookBody")
    public void invalidAddBookBodyTest(String testCase,String payload){

          RestAssured.baseURI = "http://216.10.245.166/";
          RequestSpecification request = given().log().all().header("Content-Type","application/json").body(payload);
          Response response = request.when().post(BookAPIResources.ADD.getUrl()).then().log().all().extract().response();

          //assert
        response.then().assertThat().statusCode(400);
        JsonPath jsonPath = new JsonPath(response.asString());
        String actualErrorMsg = jsonPath.getString("error_msg");
        String expectedErrorMsg = "field is required and cannot be invalid";
        Assert.assertEquals(actualErrorMsg,expectedErrorMsg);


    }


    @DataProvider
    private Object[][] getEmptyAddBookData() throws IOException {

        List<HashMap<String, Object>> testData = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/book_apis/add/AddBookFieldValidationPositive.json");
        //create multi dimensional array of type object
        Object[][] testDataArr = new Object[testData.size()][2];
        for(int i = 0; i < testData.size(); i++){

           Map<String,Object> eachMap =  testData.get(i);
           testDataArr[i][0] = eachMap.get("testCase");
           eachMap.remove("testCase");

            ObjectMapper objectMapper = new ObjectMapper();
            testDataArr[i][1] = objectMapper.writeValueAsString(eachMap);


        }

        return testDataArr;

    }

    @DataProvider
    private Object[][] getMissingAddBookBody() throws IOException {

        List<HashMap<String, Object>> testData = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/book_apis/add/AddBookFieldValidationNegative.json");
        Object[][] testDataArr = new Object[testData.size()][2];
        ObjectMapper objectMapper = new ObjectMapper();

        for(int i = 0; i < testData.size(); i++){

            HashMap<String,Object> testDataInMap = testData.get(i);
            testDataArr[i][0] = testDataInMap.get("testCase");
            testDataInMap.remove("testCase");
            testDataArr[i][1] = objectMapper.writeValueAsString(testDataInMap);


        }

        return testDataArr;

    }


    @DataProvider
    private  Object[][] getInvalidAddBookBody() throws IOException {

      List<HashMap<String,Object>>  testData = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/book_apis/add/AddBookBoundaryAndEdgeCase.json");
      //pag may list of hashmap kana ilalagay mo sila sa loob ng multi dimensional array
      Object[][] testDataArr = new Object[testData.size()][2];
      ObjectMapper objectMapper = new ObjectMapper();

      for(int i = 0; i < testData.size(); i++){

          HashMap<String,Object> eachMap = testData.get(i);
          testDataArr[i][0] = eachMap.get("testCase");
          eachMap.remove("testCase");
          testDataArr[i][1] = objectMapper.writeValueAsString(eachMap);

      }

      return testDataArr;

    }


}
