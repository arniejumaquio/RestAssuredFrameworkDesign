package rahulshettyacademy.tests.book_apis.functional;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.base.BaseTest;
import rahulshettyacademy.builder.BookRequestBuilder;
import rahulshettyacademy.clients.book_apis.BookClient;
import rahulshettyacademy.models.book_apis.request.AddBookRequest;
import rahulshettyacademy.models.book_apis.response.AddBookResponse;
import rahulshettyacademy.utilities.JSONUtility;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AddBookTests extends BaseTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test(dataProvider = "getAddBookTestData" )
    public void addBookPositiveTests(String testCaseId,String testCaseDescription,String headers,String payload,String expectedResponse){

        HashMap<String,String> payloadMap = objectMapper.readValue(payload, new TypeReference<HashMap<String,String>>() {});
        BookClient bookClient= new BookClient(requestSpecification);
        AddBookRequest addBookRequest = BookRequestBuilder.getAddBookRequest(payloadMap.get("name"), payloadMap.get("isbn"), payloadMap.get("aisle"), payloadMap.get("author"));
        AddBookResponse addBookResponse = bookClient.addBook(addBookRequest);

        Assert.assertEquals(addBookResponse.getMsg(),"successfully added");
        Assert.assertEquals(addBookResponse.getID(),payloadMap.get("isbn")+payloadMap.get("aisle") );


    }




    @DataProvider
    public Object[][] getAddBookTestData(){

        try {

             List<HashMap<String,Object>> testDatas = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/book_apis/AddBookPositiveTestData.json");
             //create multi dimensional array of type object
            Object[][] testDatasArr = new Object[testDatas.size()][5];
            //iterate over the list of hashmap
            for(int i =0; i < testDatas.size(); i++){


                HashMap<String,Object> eachMap = testDatas.get(i);
                testDatasArr[i][0] = eachMap.get("testCaseId");
                testDatasArr[i][1] = eachMap.get("testCaseDescription");
                testDatasArr[i][2] = objectMapper.writeValueAsString(eachMap.get("headers"));
                testDatasArr[i][3] = objectMapper.writeValueAsString(eachMap.get("request"));
                testDatasArr[i][4] = objectMapper.writeValueAsString(eachMap.get("expectedResponse"));

                HashMap<String,Object> requestsMap = objectMapper.readValue(String.valueOf(testDatasArr[i][3]), new TypeReference<HashMap<String,Object>>() {});
                requestsMap.put("aisle",JSONUtility.generateRandomNumber());
                testDatasArr[i][3] = objectMapper.writeValueAsString(requestsMap);
            }



            return testDatasArr;


        }catch (IOException i){
            System.out.println("IOException occur");
        }

        return null;
    }





}
