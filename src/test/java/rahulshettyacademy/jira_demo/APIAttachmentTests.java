package rahulshettyacademy.jira_demo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.pojo_classes.jira_apis.JiraBugAttachmentResponse;
import rahulshettyacademy.utilities.JSONUtility;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import static io.restassured.RestAssured.given;

public class APIAttachmentTests  {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test(dataProvider = "getApiAttachmentTestData")
    public void apiAttachmentTest(HashMap<String,Object> eachHashMap) {

         HashMap<String,Object>  test =  (HashMap<String, Object>)eachHashMap.get("test");
         HashMap<String,Object> headers = (HashMap<String, Object>) eachHashMap.get("headers");

         RestAssured.baseURI = "https://mayaph.atlassian.net";
         RequestSpecification request = given().log().all().headers(headers).pathParam("bugId",test.get("bugId")).multiPart("file",new File(test.get("file").toString()));
         Response response = request.when().post("/rest/api/3/issue/{bugId}/attachments").then().log().all().extract().response();

         //assert status code
         int actualStatusCode = response.getStatusCode();
         Assert.assertEquals(actualStatusCode,test.get("statusCode"));

         //assert response body
        JiraBugAttachmentResponse jiraBugAttachmentResponse = response.as(JiraBugAttachmentResponse.class);
        Assert.assertTrue(jiraBugAttachmentResponse.getSelf().contains(RestAssured.baseURI));
        Assert.assertNotNull(jiraBugAttachmentResponse.getId());
        //filename
        Assert.assertTrue(jiraBugAttachmentResponse.getAuthor().getSelf().contains(RestAssured.baseURI));
        Assert.assertEquals(jiraBugAttachmentResponse.getAuthor().getAccountId(),"6281bc41ba5c2500682c0dbd");


    }

    @DataProvider
    public Object[][] getApiAttachmentTestData(){
        try {
            List<HashMap<String, Object>> apiAttachmentData = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/jira_apis/JiraApiAttachmentTestData.json");
            return new Object[][]{ {apiAttachmentData.get(0)} };
        }catch (IOException e){
            System.out.println("IO exception occur");
        }
        return null;
    }

}
