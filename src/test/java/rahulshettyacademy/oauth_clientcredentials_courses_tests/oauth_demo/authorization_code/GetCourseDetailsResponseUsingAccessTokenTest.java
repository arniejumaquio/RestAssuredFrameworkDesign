package rahulshettyacademy.oauth_clientcredentials_courses_tests.oauth_demo.authorization_code;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.cucumber.stepdefinitions.base.place_apis.StepBase;
import rahulshettyacademy.pojo_classes.token_apis.OauthResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetCourseDetailsResponseUsingAccessTokenTest extends StepBase {

    private RequestSpecification request;
    private OauthResponse oauthResponse;
    private String accessToken;
    private Response response;

    @Test
    public void getAccessToken(){

        Map<String,Object> formParams = new HashMap<String,Object>();
        formParams.put("client_id",getConfig("client_id"));
        formParams.put("client_secret",getConfig("client_secret"));
        formParams.put("grant_type",getConfig("grant_type"));
        formParams.put("scope",getConfig("scope"));

        request = given().log().all().spec(requestSpecification).formParams(formParams);
        oauthResponse = request.when().post("/oauthapi/oauth2/resourceOwner/token").then().log().all().spec(responseSpecification).extract().as(OauthResponse.class);
        accessToken = oauthResponse.getAccess_token();

    }

    @Test(dependsOnMethods = "getAccessToken")
    public void getCourseDetails(){

        request = given().log().all().spec(requestSpecification).queryParam("access_token",accessToken);
        response = request.when().get("/oauthapi/getCourseDetails").then().log().all().extract().response();

        Assert.assertEquals(response.getStatusCode(),401);
        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(jsonPath.get("instructor"),"RahulShetty");
        Assert.assertEquals(jsonPath.get("url"),"rahulshettycademy.com");
        Assert.assertEquals(jsonPath.get("services"),"projectSupport");
        Assert.assertEquals(jsonPath.get("expertise"),"Automation");

        List<HashMap<String,Object>> coursesWebAutomationList = jsonPath.getList("courses.webAutomation");
        for(int i = 0; i < coursesWebAutomationList.size(); i++){
            Map<String,Object> courseWebAutomationMap = coursesWebAutomationList.get(i);
            if(courseWebAutomationMap.get("courseTitle").equals("Selenium Webdriver Java")){
                if(courseWebAutomationMap.get("price").equals("50")){
                    Assert.assertTrue(true);
               }else {
                   Assert.assertTrue(false);
               }
            }

            if(courseWebAutomationMap.get("courseTitle").equals("Cypress")){
                if(courseWebAutomationMap.get("price").equals("40")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertTrue(false);
                }
            }

            if(courseWebAutomationMap.get("courseTitle").equals("Protractor")){

                if(courseWebAutomationMap.get("price").equals("40")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertTrue(false);
                }
            }
        }

       List<HashMap<String,Object>>   coursesApiList = jsonPath.getList("courses.api");
        for(int i = 0; i < coursesApiList.size();i++){
            Map<String,Object> coursesApiMap = coursesApiList.get(i);
            if(coursesApiMap.get("courseTitle").equals("Rest Assured Automation using Java")){
                if(coursesApiMap.get("price").equals("50")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertFalse(false);
                }
            }
            if(coursesApiMap.get("courseTitle").equals("SoapUI Webservices testing")){
                if(coursesApiMap.get("price").equals("40")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertFalse(false);
                }
            }
        }

        List<HashMap<String,Object>> coursesMobileList = jsonPath.getList("courses.mobile");
        for(int i = 0; i < coursesMobileList.size(); i++){

            Map<String,Object> courseMobileMap = coursesMobileList.get(i);
            if(courseMobileMap.get("courseTitle").equals("Appium-Mobile Automation using Java")){
                if(courseMobileMap.get("price").equals("50")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertTrue(false);
                }
            }

        }

        Assert.assertEquals(jsonPath.get("linkedIn"),"https://www.linkedin.com/in/rahul-shetty-trainer/");


    }



}
