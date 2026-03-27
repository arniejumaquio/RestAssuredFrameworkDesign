package rahulshettyacademy.book_tests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.base.BaseTest;
import rahulshettyacademy.pojo_classes.book_apis.response.AddBookResponse;
import rahulshettyacademy.utilities.JSONUtility;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import static io.restassured.RestAssured.given;

public class AddBookTest3 extends BaseTest {

    ObjectMapper objectMapper = new ObjectMapper();
    private String ID;
    private HashMap<String,Object> tcIdAisleMap = new HashMap<String,Object>();

    @Test(dataProvider = "getAddBookData",priority = 1)
    public void addBookFunctionalTest(String testCaseId,String testCaseDescription,String headers,String payload,String expectedResponse){

        HashMap<String,Object>  payLoadMap = objectMapper.readValue(payload, new TypeReference<HashMap<String, Object>>() {});
        if(!testCaseId.equalsIgnoreCase("TC3")) {
            payLoadMap.put("aisle", JSONUtility.generateRandomNumber() );
        }

        tcIdAisleMap.put(testCaseId,payLoadMap.get("aisle"));
        ID = (String)payLoadMap.get("isbn")+payLoadMap.get("aisle");

        RequestSpecification request = given().log().all().spec(requestSpecification).body(payLoadMap);
        Response response =request.when().post("Library/Addbook.php").then().log().all().extract().response();
        AddBookResponse addBookResponse = response.as(AddBookResponse.class);

        Assert.assertEquals(response.getStatusCode(),JSONUtility.getJsonValueIntFromPath(expectedResponse,"statusCode"));
        Assert.assertEquals(addBookResponse.getMsg(),JSONUtility.getJsonValueStringFromPath(expectedResponse,"Msg"));
        Assert.assertEquals(addBookResponse.getID(),ID); //isbn+aisle

    }

    @Test(dataProvider = "getAddBookData",dependsOnMethods = "addBookFunctionalTest")
    public void getBookByAuthorNameFunctionalTest(String testCaseId,String testCaseDescription,String headers,String payload,String expectedResponse){

     HashMap<String,Object> payloadMap =  objectMapper.readValue(payload, new TypeReference<HashMap<String,Object>>() {});
     RequestSpecification request = given().spec(requestSpecification).queryParam("AuthorName",payloadMap.get("author"));
     Response response = request.when().get("Library/GetBook.php").then().log().all().extract().response();

     Assert.assertEquals(response.getStatusCode(),200);
     int bookSize =JSONUtility.getJsonValueIntFromPath(response.asString(),"size()");
     for(int i = 0; i < bookSize; i++){

         String actualAisle = JSONUtility.getJsonValueStringFromPath(response.asString(),"["+i+"].aisle");
         String actualIsbn = JSONUtility.getJsonValueStringFromPath(response.asString(),"["+i+"].isbn");
         String actualBookName = JSONUtility.getJsonValueStringFromPath(response.asString(),"["+i+"].book_name");

         if( actualAisle.equalsIgnoreCase((String)tcIdAisleMap.get(testCaseId)) ){
             if(actualIsbn.equalsIgnoreCase((String)payloadMap.get("isbn"))){
                 if(actualBookName.equalsIgnoreCase((String)payloadMap.get("name"))){
                     Assert.assertTrue(true);
                 }
             }
         }

     }

    }



    @Test(dataProvider = "getAddBookFieldValidationPositiveData",priority = 3)
    public void addBookFieldValidationPositiveTest(String testCaseId,String testCaseDescription,String headers,String payload,String expectedResponse){

            HashMap<String,Object> payloadMap =  objectMapper.readValue(payload, new TypeReference<HashMap<String,Object>>() {});
            payloadMap.put("aisle",JSONUtility.generateRandomNumber());
            ID = (String)payloadMap.get("isbn") + (String)payloadMap.get("aisle");
            RequestSpecification request = given().log().all().spec(requestSpecification).body(payloadMap);
            AddBookResponse addBookResponse =   request.when().post("Library/Addbook.php").then().log().all().spec(responseSpecification).extract().response().as(AddBookResponse.class);

            Assert.assertEquals(addBookResponse.getMsg(),"successfully added");
            Assert.assertEquals(addBookResponse.getID(),ID);

    }

    @Test(dataProvider = "getAddBookFieldValidationPositiveData",priority = 4)
    public void getBookByAuthorNameFieldValidationPositiveTest(String testCaseId,String testCaseDescription,String headers,String payload,String expectedResponse){

        HashMap<String,Object> payloadMap = objectMapper.readValue(payload, new TypeReference<HashMap<String,Object>>() {});
        RequestSpecification request = given().spec(requestSpecification).queryParam("AuthorName",payloadMap.get("author"));
        Response response = request.when().get("Library/GetBook.php").then().log().all().spec(responseSpecification).extract().response();

        Assert.assertEquals(response.getStatusCode(),200);
        int bookSize = JSONUtility.getJsonValueIntFromPath(response.asString(),"size()");
        for(int i = 0; i < bookSize; i++){

           String actualAisle = JSONUtility.getJsonValueStringFromPath(response.asString(),"["+i+"].aisle");
           String actualIsbn = JSONUtility.getJsonValueStringFromPath(response.asString(),"["+i+"].isbn");
           String actualBookName = JSONUtility.getJsonValueStringFromPath(response.asString(),"["+i+"].book_name");

           if( actualAisle.equalsIgnoreCase((String)payloadMap.get("aisle"))
                   && actualIsbn.equalsIgnoreCase((String)payloadMap.get("isbn"))
                   && actualBookName.equalsIgnoreCase((String)payloadMap.get("name"))  ){
               Assert.assertTrue(true);
           }


        }

    }


    @Test(dataProvider = "getAddBookFieldValidationNegativeData",priority = 5)
    public void addBookFieldValidationNegativeTest(String testCaseId,String testCaseDescription,String headers,String payload,String expectedResponse){

        RequestSpecification request = given().log().all().spec(requestSpecification).body(payload);
        Response response = request.when().post("Library/Addbook.php").then().log().all().extract().response();

        Assert.assertEquals(response.getStatusCode(),400);
        Assert.assertEquals(JSONUtility.getJsonValueStringFromPath(response.asString(),"error"),JSONUtility.getJsonValueStringFromPath(expectedResponse,"error"));
        Assert.assertEquals(JSONUtility.getJsonValueStringFromPath(response.asString(),"message"),JSONUtility.getJsonValueStringFromPath(expectedResponse,"message"));

    }

    @Test(dataProvider = "getAddBookBoundaryAndEdgeCaseData",priority = 6)
    public void addPlaceBoundaryAndEdgeCaseTest(String testCaseId,String testCaseDescription,String headers,String payload,String expectedResponse){

        //convert the string payload to hashmap
        HashMap<String,Object> payloadMap =  objectMapper.readValue(payload, new TypeReference<HashMap<String,Object>>() {});
        ID = (String)payloadMap.get("isbn")+(String)payloadMap.get("aisle");

        if(!testCaseId.equalsIgnoreCase("TC5") && !testCaseId.equalsIgnoreCase("TC6")){
                payloadMap.put("aisle",JSONUtility.generateRandomNumber());
        }

        RequestSpecification request = given().log().all().spec(requestSpecification).body(payload);
        Response response  =  request.when().post("Library/Addbook.php").then().log().all().extract().response();
        AddBookResponse addBookResponse = response.as(AddBookResponse.class);

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(addBookResponse.getMsg(),JSONUtility.getJsonValueStringFromPath(expectedResponse,"Msg"));
        Assert.assertEquals(addBookResponse.getID(),ID);

    }


    @Test(dataProvider = "getAddBookBoundaryAndEdgeCaseData",priority = 7)
    public void getPlaceBoundaryAndEdgeCaseTest(String testCaseId,String testCaseDescription,String headers,String payload,String expectedResponse){

        HashMap<String,Object> payloadMap = objectMapper.readValue(payload, new TypeReference<HashMap<String,Object>>() {});
        RequestSpecification request  = given().log().all().spec(requestSpecification).queryParam("AuthorName",payloadMap.get("author"));
        Response response = request.when().get("Library/GetBook.php").then().log().all().extract().response();

        Assert.assertEquals(response.getStatusCode(),200);
        int bookCounts = JSONUtility.getJsonValueIntFromPath(response.asString(),"size()");
        for(int i = 0 ; i < bookCounts; i++){
            String actualAisle = JSONUtility.getJsonValueStringFromPath(response.asString(),"["+i+"].aisle");
            String actualIsbn = JSONUtility.getJsonValueStringFromPath(response.asString(),"["+i+"].isbn");
            String actualBookName = JSONUtility.getJsonValueStringFromPath(response.asString(),"["+i+"].book_name");

            if( actualAisle.equalsIgnoreCase((String)payloadMap.get("aisle"))
                    && actualIsbn.equalsIgnoreCase((String)payloadMap.get("isbn"))
                    && actualBookName.equalsIgnoreCase((String)payloadMap.get("name"))  ){
                Assert.assertTrue(true);
            }

        }

    }



    @DataProvider
    public Object[][] getAddBookData(){

        try {

         List<HashMap<String,Object>> addBookTestData = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/book_apis/add/AddBookFunctional.json");
         Object[][] addBookTestDataArr = new Object[addBookTestData.size()][5];
         objectMapper = new ObjectMapper();
         for(int i = 0; i < addBookTestData.size(); i++){

           HashMap<String,Object> addBookTestDataMap =  addBookTestData.get(i);
           addBookTestDataArr[i][0] = addBookTestDataMap.get("testCaseId");
           addBookTestDataArr[i][1] = addBookTestDataMap.get("testCaseDescription");
           addBookTestDataArr[i][2] = objectMapper.writeValueAsString(addBookTestDataMap.get("headers"));
           addBookTestDataArr[i][3] = objectMapper.writeValueAsString(addBookTestDataMap.get("request"));
           addBookTestDataArr[i][4] = objectMapper.writeValueAsString(addBookTestDataMap.get("expectedResponse"));

         }

         return addBookTestDataArr;


        }catch (IOException i){
            i.printStackTrace();

            return null;
        }
    }


    @DataProvider
    public Object[][] getAddBookFieldValidationPositiveData(){

        try {

            List<HashMap<String, Object>> addBookDataList = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/book_apis/add/AddBookFieldValidationPositive.json");
            Object[][] addBookDataArr = new Object[addBookDataList.size()][5];
            for(int i = 0; i < addBookDataList.size(); i++){
                HashMap<String,Object> addBookMap = addBookDataList.get(i);
                addBookDataArr[i][0] = addBookMap.get("testCaseId");
                addBookDataArr[i][1] = addBookMap.get("testCaseDescription");
                addBookDataArr[i][2] = objectMapper.writeValueAsString(addBookMap.get("headers"));
                addBookDataArr[i][3] = objectMapper.writeValueAsString(addBookMap.get("request"));
                addBookDataArr[i][4] = objectMapper.writeValueAsString(addBookMap.get("expectedResponse"));
            }

            return addBookDataArr;

        }catch (IOException i){
            i.printStackTrace();

            return null;
        }
    }


    @DataProvider
    public Object[][] getAddBookFieldValidationNegativeData(){

        try {

            List<HashMap<String, Object>> addBookDataList = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/book_apis/add/AddBookInvalidTestData.json");
            Object[][] addBookDataArr = new Object[addBookDataList.size()][5];
            for(int i = 0; i < addBookDataList.size(); i++){


                HashMap<String,Object> addBookDataMap = addBookDataList.get(i);
                addBookDataArr[i][0] = addBookDataMap.get("testCaseId");
                addBookDataArr[i][1] = addBookDataMap.get("testCaseDescription");
                addBookDataArr[i][2] = objectMapper.writeValueAsString("headers");
                addBookDataArr[i][3] = objectMapper.writeValueAsString("request");
                addBookDataArr[i][4] = objectMapper.writeValueAsString("expectedResponse");

            }

            return addBookDataArr;


        }catch (IOException i){
             i.printStackTrace();
        }


        return null;
    }


    @DataProvider
    public Object[][] getAddBookBoundaryAndEdgeCaseData(){

        try {

            List<HashMap<String, Object>> addBookTestDatas = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/book_apis/add/AddBookBoundaryAndEdgeCase.json");
            Object[][] addBookArr = new Object[addBookTestDatas.size()][5];
            for(int i = 0; i < addBookTestDatas.size(); i++){

                ObjectMapper objectMapper = new ObjectMapper();

                HashMap<String,Object> addBookTestDataMap = addBookTestDatas.get(i);
                addBookArr[i][0] = addBookTestDataMap.get("testCaseId");
                addBookArr[i][1] = addBookTestDataMap.get("testCaseDescription");
                addBookArr[i][2] = objectMapper.writeValueAsString(addBookTestDataMap.get("headers"));
                addBookArr[i][3] = objectMapper.writeValueAsString(addBookTestDataMap.get("request"));
                addBookArr[i][4] = objectMapper.writeValueAsString(addBookTestDataMap.get("expectedResponse"));

            }

            return addBookArr;

        }catch (IOException i){

            i.printStackTrace();
        }

        return null;
    }


}
