package rahulshettyacademy.place_tests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.base.BaseTest;
import rahulshettyacademy.utilities.JSONUtility;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import static io.restassured.RestAssured.given;

public class PlaceTest2 extends BaseTest {

    @Test(dataProvider = "getAddPlacePositiveTestData")
    public void addPlacePositiveTest(String testCase , String payLoad){

       RequestSpecification request = given().log().all().spec(requestSpecification).body(payLoad);
       Response response = request.when().post("maps/api/place/add/json").then().log().all().spec(responseSpecification).extract().response();

       //assertion

        //status code -already validated by spec

        //body
        String expectedStatus = "OK";
        String expectedScope = "APP";
        String actualStatus = (String) JSONUtility.getJsonValueFromPath(response.asString(),"status");
        String actualPlaceId = (String) JSONUtility.getJsonValueFromPath(response.asString(),"place_id");
        String actualScope = (String)JSONUtility.getJsonValueFromPath(response.asString(),"scope");
        String actualReference =  (String)JSONUtility.getJsonValueFromPath(response.asString(),"reference");
        String actualId = (String)JSONUtility.getJsonValueFromPath(response.asString(),"id");

       Assert.assertEquals(expectedStatus,actualStatus);
       Assert.assertNotNull(actualPlaceId);
       Assert.assertEquals(expectedScope,actualScope);
       Assert.assertNotNull(actualReference);
       Assert.assertNotNull(actualId);

    }

    @Test(dataProvider = "getAddPlaceNegativeTestData")
    public void addPlaceNegativeTest(String testCase,String msg,String payLoad){

       RequestSpecification request = given().log().all().spec(requestSpecification).body(payLoad);
       Response response = request.when().post("maps/api/place/add/json").then().log().all().spec(responseSpecification).extract().response();

       int expectedStatusCode = 400;
       int actualStatusCode = response.getStatusCode();
       Assert.assertEquals(actualStatusCode,expectedStatusCode);

       String expectedMsg = msg;
       String actualMsg = (String) JSONUtility.getJsonValueFromPath(response.asString(),"msg");
       Assert.assertEquals(actualMsg,expectedMsg);

    }


    @Test(dataProvider = "getAddPlaceEdgeTestData")
    public void addPlaceEdgeTest(String testCase,String msg,int expectedStatusCode,String payLoad){

        RequestSpecification request = given().log().all().spec(requestSpecification).body(payLoad);
        Response response = request.when().post("maps/api/place/add/json").then().log().all().spec(responseSpecification).extract().response();

        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode,expectedStatusCode);

        if(expectedStatusCode == 200){

            String expectedStatus = "OK";
            String expectedScope = "APP";


            String actualStatus = (String) JSONUtility.getJsonValueFromPath(response.asString(),"status");
            String actualPlaceId = (String) JSONUtility.getJsonValueFromPath(response.asString(),"place_id");
            String actualScope = (String)JSONUtility.getJsonValueFromPath(response.asString(),"scope");
            String actualReference =  (String)JSONUtility.getJsonValueFromPath(response.asString(),"reference");
            String actualId = (String)JSONUtility.getJsonValueFromPath(response.asString(),"id");

            Assert.assertEquals(expectedStatus,actualStatus);
            Assert.assertNotNull(actualPlaceId);
            Assert.assertEquals(expectedScope,actualScope);
            Assert.assertNotNull(actualReference);
            Assert.assertNotNull(actualId);


        }else if(expectedStatusCode == 400){

            String expectedMsg = msg;
            String actualMsg = (String) JSONUtility.getJsonValueFromPath(response.asString(),"msg");
            Assert.assertEquals(actualMsg,expectedMsg);

        }else {
            System.out.println("Invalid status code");
        }

    }


    @DataProvider
    public Object[][] getAddPlacePositiveTestData() throws IOException {

        List<HashMap<String, Object>> testData =JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/place_apis/AddPlacePositiveTestData.json");
        Object[][] testDataArr = new Object[testData.size()][2];

        for(int i = 0; i < testData.size(); i++){

            ObjectMapper objectMapper = new ObjectMapper();

          HashMap<String,Object> eachMap =  testData.get(i);
          testDataArr[i][0] = eachMap.get("testCase");
          eachMap.remove("testCase");
          testDataArr[i][1] = objectMapper.writeValueAsString(eachMap);

        }

        return testDataArr;

    }


    @DataProvider
    public Object[][] getAddPlaceNegativeTestData() throws IOException {

      List<HashMap<String,Object>> testData = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/place_apis/AddPlaceNegativeTestData.json");
      Object[][] testDataArr = new Object[testData.size()][3];

      for(int i = 0; i < testData.size(); i++){

         HashMap<String,Object> eachMap = testData.get(i);
         testDataArr[i][0] = eachMap.get("testCase");
         testDataArr[i][1] = eachMap.get("msg");
         eachMap.remove("testCase");
         eachMap.remove("msg");
         ObjectMapper objectMapper = new ObjectMapper();
         testDataArr[i][2] = objectMapper.writeValueAsString(eachMap);


      }


      return testDataArr;


    }

    @DataProvider
    public Object[][] getAddPlaceEdgeTestData() throws IOException {

      List<HashMap<String,Object>> testData = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/place_apis/AddPlaceEdgeTestData.json");
      Object[][] testDataArr = new Object[testData.size()][4];

      for(int i = 0; i < testData.size(); i++){

          HashMap<String,Object> eachMap = testData.get(i);
          testDataArr[i][0] = eachMap.get("testCase");
          testDataArr[i][1] = eachMap.get("msg");
          testDataArr[i][2] = eachMap.get("statusCode");
          eachMap.remove("testCase");
          eachMap.remove("msg");
          eachMap.remove("statusCode");

          ObjectMapper objectMapper = new ObjectMapper();
          testDataArr[i][3] = objectMapper.writeValueAsString(eachMap);

      }


      return testDataArr;


    }


}
