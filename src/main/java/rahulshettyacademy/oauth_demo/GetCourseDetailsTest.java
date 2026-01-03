package rahulshettyacademy.oauth_demo;

import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.utilities.AssertUtility;
import rahulshettyacademy.utilities.JSONUtility;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetCourseDetailsTest {


   public  String accessToken;

    @Test
    public void getAccessToken(){

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        Map<String,Object> formParams = new HashMap<String,Object>();
        formParams.put("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
        formParams.put("client_secret","erZOWM9g3UtwNRj340YYaK_W");
        formParams.put("grant_type","client_credentials");
        formParams.put("scope","trust");

        String response = given().formParams(formParams).
        when().post("/oauthapi/oauth2/resourceOwner/token").
        then().assertThat().statusCode(200).extract().response().asString();

        accessToken = JSONUtility.getJsonValueStringFromPath(response,"access_token");

    }


    @Test(dependsOnMethods = "getAccessToken")
    public void getCourseDetailsTest(){

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().queryParam("access_token",accessToken).
        when().get("/oauthapi/getCourseDetails").
        then().assertThat().statusCode(401).extract().response().asString();

        String actualInstructor = JSONUtility.getJsonValueStringFromPath(response,"instructor");
        String actualUrl = JSONUtility.getJsonValueStringFromPath(response,"url");
        String actualServices = JSONUtility.getJsonValueStringFromPath(response,"services");
        String actualExpertise = JSONUtility.getJsonValueStringFromPath(response,"expertise");
        String actualLinkedin = JSONUtility.getJsonValueStringFromPath(response,"linkedIn");

        Assert.assertEquals(actualInstructor,"RahulShetty");
        Assert.assertEquals(actualUrl,"rahulshettycademy.com");
        Assert.assertEquals(actualServices,"projectSupport");
        Assert.assertEquals(actualExpertise,"Automation");
        Assert.assertEquals(actualLinkedin,"https://www.linkedin.com/in/rahul-shetty-trainer/");


        int webAutomationSize = JSONUtility.getJsonValueIntFromPath(response,"courses.webAutomation.size()");

        for(int i = 0; i < webAutomationSize; i++){

           String webAutomationCourseTitle = JSONUtility.getJsonValueStringFromPath(response,"courses.webAutomation["+i+"].courseTitle");
           String webAutomationPrice = JSONUtility.getJsonValueStringFromPath(response,"courses.webAutomation["+i+"].price");

           if(webAutomationCourseTitle.equalsIgnoreCase("Selenium Webdriver Java")){

               if(webAutomationPrice.equalsIgnoreCase("50")){
                   Assert.assertTrue(true);
               }else {
                   Assert.assertTrue(false);
               }

           }else if(webAutomationCourseTitle.equalsIgnoreCase("Cypress")){

               if(webAutomationPrice.equalsIgnoreCase("40")){
                   Assert.assertTrue(true);
               }else {
                   Assert.assertTrue(false);
               }

           }else if(webAutomationCourseTitle.equalsIgnoreCase("Protractor")){

               if(webAutomationPrice.equalsIgnoreCase("40")){
                   Assert.assertTrue(true);
               }else {
                   Assert.assertTrue(false);
               }

           }else{
               System.out.println("Invalid course title or price");
           }

        }



        int apiAutomationSize = JSONUtility.getJsonValueIntFromPath(response,"courses.api.size()");

        for(int i = 0; i < apiAutomationSize; i++){

            String apiAutomationCourseTitle = JSONUtility.getJsonValueStringFromPath(response,"courses.api["+i+"].courseTitle");
            String apiAutomationPrice = JSONUtility.getJsonValueStringFromPath(response,"courses.api["+i+"].price");

            if(apiAutomationCourseTitle.equalsIgnoreCase("Rest Assured Automation using Java")){

                if(apiAutomationPrice.equalsIgnoreCase("50")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertTrue(false);
                }

            }else if(apiAutomationCourseTitle.equalsIgnoreCase("SoapUI Webservices testing")){

                if(apiAutomationPrice.equalsIgnoreCase("40")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertTrue(false);
                }

            }else{
                System.out.println("Invalid course title or price");
            }

        }


        /**int mobileAutomationSize = JSONUtility.getJsonValueIntFromPath(response,"courses.mobile.size()");

        for(int i = 0; i < mobileAutomationSize; i++){

            String mobileAutomationCourseTitle = JSONUtility.getJsonValueStringFromPath(response,"courses.mobile["+i+"].courseTitle");
            String mobileAutomationPrice = JSONUtility.getJsonValueStringFromPath(response,"courses.mobile["+i+"].price");

            if(mobileAutomationCourseTitle.equalsIgnoreCase("Appium-Mobile Automation using Java")){

                if(mobileAutomationPrice.equalsIgnoreCase("50")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertTrue(false);
                }

            }else{
                System.out.println("Invalid course title or price");
            }

        }**/

        String mobileCourseTitle = "Appium-Mobile Automation using Java";
        String mobileCoursePrice = "50";

        AssertUtility.verifyCourseTitleAndPrice(response,"courses.mobile",mobileCourseTitle,mobileCoursePrice);


    }


}
