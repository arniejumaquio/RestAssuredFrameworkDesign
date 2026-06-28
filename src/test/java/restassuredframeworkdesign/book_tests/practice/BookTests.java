package restassuredframeworkdesign.book_tests.practice;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import restassuredframeworkdesign.book_tests.base.BookBaseTest;
import restassuredframeworkdesign.utilities.JSONUtility;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BookTests extends BookBaseTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test(dataProvider = "getAddBookTestData" )
    public void validateAddBookTest(String testCase,String headers,String payload){

        /**

        HashMap<String,String> requestsMap = objectMapper.readValue(payload, new TypeReference<HashMap<String,String>>() {});
        AddBookRequest addBookRequest = BookRequestBuilder.getAddBookRequest(requestsMap.get("name"), requestsMap.get("isbn"), requestsMap.get("aisle"), requestsMap.get("author"));
        Response response = addBook(addBookRequest);

        Assert.assertEquals(JSONUtility.getJsonValueStringFromPath(response.asString(),"Msg"),"successfully added");
        Assert.assertEquals(JSONUtility.getJsonValueStringFromPath(response.asString(),"ID"),requestsMap.get("isbn")+requestsMap.get("aisle"));

        //clean up
        DeleteBookRequest deleteBookRequest = BookRequestBuilder.getDeleteBookRequest(JSONUtility.getJsonValueStringFromPath(response.asString(),"ID"));
        deleteBook(deleteBookRequest);

        **/
    }




    @DataProvider
    public Object[][] getAddBookTestData(){

        try {

             List<HashMap<String,Object>> testDatas = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/book_apis/add/EndToEndAddBookTestData.json");
             //create multi dimensional array of type object
            Object[][] testDatasArr = new Object[testDatas.size()][3];
            //iterate over the list of hashmap
            for(int i =0; i < testDatas.size(); i++){


                HashMap<String,Object> eachMap = testDatas.get(i);
                testDatasArr[i][0] = eachMap.get("testCase");
                testDatasArr[i][1] = objectMapper.writeValueAsString(eachMap.get("headers"));
                testDatasArr[i][2] = objectMapper.writeValueAsString(eachMap.get("request"));

                HashMap<String,Object> requestsMap = objectMapper.readValue(String.valueOf(testDatasArr[i][2]), new TypeReference<HashMap<String,Object>>() {});
                requestsMap.put("aisle",JSONUtility.generateRandomNumber());
                testDatasArr[i][2] = objectMapper.writeValueAsString(requestsMap);
            }



            return testDatasArr;


        }catch (IOException i){
            System.out.println("IOException occur");
        }

        return null;
    }





}
