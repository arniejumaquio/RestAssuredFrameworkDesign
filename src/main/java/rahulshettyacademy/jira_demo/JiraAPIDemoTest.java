package rahulshettyacademy.jira_demo;

import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.utilities.AddBodyUtility;
import rahulshettyacademy.utilities.JSONUtility;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class JiraAPIDemoTest {

    String responseId;

    @Test
    public void createBugTest(){

        String key = "SCRUM";
        String summary = "12/29/2015 Bug Ticket";
        String description = "Creating of an issue using project keys and issue type names using the REST API";

        RestAssured.baseURI = "https://arniejumaquio.atlassian.net";
        String response = given().log().all().header("Content-Type","application/json").header("Authorization","Basic YXJuaWVqdW1hcXVpb0BnbWFpbC5jb206QVRBVFQzeEZmR0YwcGtCN2VpYU4wQXNQcHlNV0xocUpRekVvaV9kSVVjOVp6TDJ1aVNzRXRNQXB6OXZYeGhSckZicWhCb1dkcTZYa21fa0hmamJ6VGZjN2NmVE9KbC1ZOUVqbzhiaGQtWkZDa2REM2kxNjRxRnFzVE41dEdWWnZlbWMzQ1BHR1hJMnh4TXVwWHNZd2VYY2Zfd1NsMnFfU3ZOMnVLa2lsTE9qNzJNNGxaRWVYc3N3PUZFRjhCMkYz")
                .body(AddBodyUtility.getCreateBugBody(key,summary,description)).
        when().post("/rest/api/2/issue/").
        then().log().all().assertThat().statusCode(201).extract().response().asString();

        responseId = JSONUtility.getJsonValueStringFromPath(response,"id");
        String responseKey = JSONUtility.getJsonValueStringFromPath(response,"key");
        String responseSelf = JSONUtility.getJsonValueStringFromPath(response,"self");

        Assert.assertTrue(responseKey.contains(key));
        Assert.assertTrue(responseSelf.contains(RestAssured.baseURI));
        System.out.println(responseId);


    }


    @Test(dependsOnMethods = "createBugTest")
    public void addAttachmentToBugTest(){

        RestAssured.baseURI = "https://arniejumaquio.atlassian.net";

        String response = given().log().all().header("X-Atlassian-Token","nocheck").header("Authorization","Basic YXJuaWVqdW1hcXVpb0BnbWFpbC5jb206QVRBVFQzeEZmR0YwcGtCN2VpYU4wQXNQcHlNV0xocUpRekVvaV9kSVVjOVp6TDJ1aVNzRXRNQXB6OXZYeGhSckZicWhCb1dkcTZYa21fa0hmamJ6VGZjN2NmVE9KbC1ZOUVqbzhiaGQtWkZDa2REM2kxNjRxRnFzVE41dEdWWnZlbWMzQ1BHR1hJMnh4TXVwWHNZd2VYY2Zfd1NsMnFfU3ZOMnVLa2lsTE9qNzJNNGxaRWVYc3N3PUZFRjhCMkYz").
        pathParam("issueId",responseId).multiPart("file",new File("src/main/resources/attachments/attachment.png")).
        when().post("/rest/api/2/issue/rest/api/2/issue/{issueId}/attachments").
        then().log().all().assertThat().statusCode(200).extract().response().asString();

        String fileName = JSONUtility.getJsonValueStringFromPath(response,"[0].filename");
        Assert.assertEquals(fileName,"attachment.png");


    }


}
