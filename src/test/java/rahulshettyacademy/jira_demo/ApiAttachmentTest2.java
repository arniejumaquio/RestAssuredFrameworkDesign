package rahulshettyacademy.jira_demo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ApiAttachmentTest2 {

    RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://mayaph.atlassian.net")
            .addHeader("X-Atlassian-Token","nocheck")
            .addHeader("Authorization","Basic YXJuaWUuanVtYXF1aW9AcGF5bWF5YS5jb206QVRBVFQzeEZmR0YwU1NVbnhOeXBPVWxOX0duSnZsRWRPNGFXeHVJeUItY2VDZ1JPUFk3d1VlNGdJLXNWRkR2bVhVSV90UFd4MHdjcUlDeEF2OHowLXM5d1hCQ2FVTjZSTGlXbHRfeVh6U2hLUllYYjhOMnk2bHJmWnFYVDFTckYzcG1tYUFsQnNuTFBiXzd4UjNIV2t0bWV2UU5YMkdZUGMydlZDcDNTV2JIMmp0bVpMQmZFNm93PTg0MDlFNUIy")
            .addPathParam("id",1564639).build();

    ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

    @Test
    public void attachmentTest(){

        File attachment = new File("src/main/resources/attachments/jira_apis/jira_bug_attachment.png");
        RequestSpecification request = given().spec(requestSpecification).multiPart("file",attachment);
        Response response =request.when().post("/rest/api/3/issue/{id}/attachments").then().log().all().spec(responseSpecification).extract().response();

        Assert.assertTrue(response.getStatusCode() == 200);
        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertTrue(jsonPath.getString("self").contains("https://mayaph.atlassian.net/rest/api/3/"));
        Assert.assertNotNull(jsonPath.get("id"));
        Assert.assertTrue(jsonPath.getString("author.self").contains("https://mayaph.atlassian.net/rest/api/3/"));
    }



}

