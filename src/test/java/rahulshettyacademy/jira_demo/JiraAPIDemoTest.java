package rahulshettyacademy.jira_demo;

import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.utilities.AddBodyUtility;
import rahulshettyacademy.utilities.JSONUtility;

import java.io.File;
import static io.restassured.RestAssured.given;

public class JiraAPIDemoTest {

    String responseId;

    @Test
    public void createBugTest(){

        String key = "SCRUM";
        String summary = "12/29/2015 Bug Ticket";
        String description = "Creating of an issue using project keys and issue type names using the REST API";

        RestAssured.baseURI = "https://mayaph.atlassian.net";
        String response = given().log().all().header("Content-Type","application/json").header("Authorization","Basic YXJuaWUuanVtYXF1aW9AcGF5bWF5YS5jb206QVRBVFQzeEZmR0YwU1NVbnhOeXBPVWxOX0duSnZsRWRPNGFXeHVJeUItY2VDZ1JPUFk3d1VlNGdJLXNWRkR2bVhVSV90UFd4MHdjcUlDeEF2OHowLXM5d1hCQ2FVTjZSTGlXbHRfeVh6U2hLUllYYjhOMnk2bHJmWnFYVDFTckYzcG1tYUFsQnNuTFBiXzd4UjNIV2t0bWV2UU5YMkdZUGMydlZDcDNTV2JIMmp0bVpMQmZFNm93PTg0MDlFNUIy")
                .body(AddBodyUtility.getCreateBugBody(key,summary,description)).
        when().post("/browse/LCI-48718").
        then().log().all().assertThat().statusCode(201).extract().response().asString();

        responseId = JSONUtility.getJsonValueStringFromPath(response,"id");
        String responseKey = JSONUtility.getJsonValueStringFromPath(response,"key");
        String responseSelf = JSONUtility.getJsonValueStringFromPath(response,"self");

        Assert.assertTrue(responseKey.contains(key));
        Assert.assertTrue(responseSelf.contains(RestAssured.baseURI));
        System.out.println(responseId);


    }


    @Test
    public void addAttachmentToBugTest(){

        responseId = "1564639";
        RestAssured.baseURI = "https://mayaph.atlassian.net";

        String response = given().header("X-Atlassian-Token","nocheck").header("Authorization","Basic YXJuaWUuanVtYXF1aW9AcGF5bWF5YS5jb206QVRBVFQzeEZmR0YwU1NVbnhOeXBPVWxOX0duSnZsRWRPNGFXeHVJeUItY2VDZ1JPUFk3d1VlNGdJLXNWRkR2bVhVSV90UFd4MHdjcUlDeEF2OHowLXM5d1hCQ2FVTjZSTGlXbHRfeVh6U2hLUllYYjhOMnk2bHJmWnFYVDFTckYzcG1tYUFsQnNuTFBiXzd4UjNIV2t0bWV2UU5YMkdZUGMydlZDcDNTV2JIMmp0bVpMQmZFNm93PTg0MDlFNUIy").
        pathParam("issueId",responseId).multiPart("file",new File("src/main/resources/attachments/jira_apis/jira_bug_attachment.png")).
        when().post("/rest/api/2/issue/rest/api/2/issue/{issueId}/attachments").
        then().log().all().statusCode(200).extract().response().asString();

        String fileName = JSONUtility.getJsonValueStringFromPath(response,"[0].filename");
        Assert.assertEquals(fileName,"jira_bug_attachment.png");


    }


}
