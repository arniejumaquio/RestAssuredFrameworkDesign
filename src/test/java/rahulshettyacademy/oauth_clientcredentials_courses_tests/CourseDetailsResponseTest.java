package rahulshettyacademy.oauth_clientcredentials_courses_tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.pojo_classes.course_details_apis.API;
import rahulshettyacademy.pojo_classes.course_details_apis.CourseDetailsResponse;
import rahulshettyacademy.pojo_classes.course_details_apis.Mobile;
import rahulshettyacademy.pojo_classes.course_details_apis.WebAutomation;

import java.util.List;

import static io.restassured.RestAssured.given;

public class CourseDetailsResponseTest extends AccessTokenTest {

    RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").build();
    ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(401).expectContentType(ContentType.JSON).build();

    @Test(dependsOnMethods = "getAccessTokenTest")
    public void getCourseDetailsTest(){

        RequestSpecification request = given().log().all().spec(requestSpecification).queryParams("access_token",AccessTokenTest.accessToken);
        CourseDetailsResponse response =  request.when().get("oauthapi/getCourseDetails").then().spec(responseSpecification).extract().as(CourseDetailsResponse.class);

        Assert.assertEquals(response.getInstructor(),"RahulShetty");
        Assert.assertEquals(response.getUrl(),"rahulshettycademy.com");
        Assert.assertEquals(response.getServices(),"projectSupport");
        Assert.assertEquals(response.getExpertise(),"Automation");
        List<WebAutomation> webAutomationList = response.getCourses().getWebAutomation();
        for(int i =0; i < webAutomationList.size(); i++){

            if(webAutomationList.get(i).getCourseTitle().equalsIgnoreCase("Selenium Webdriver Java")){
                if(webAutomationList.get(i).getPrice().equalsIgnoreCase("50")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertTrue(false);
                }
            }

            if(webAutomationList.get(i).getCourseTitle().equalsIgnoreCase("Cypress")){
                if(webAutomationList.get(i).getPrice().equalsIgnoreCase("40")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertTrue(false);
                }
            }


            if(webAutomationList.get(i).getCourseTitle().equalsIgnoreCase("Protractor")){
                if(webAutomationList.get(i).getPrice().equalsIgnoreCase("40")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertTrue(false);
                }
            }

        }

        List<API> apiList = response.getCourses().getApi();
        for(int i =0; i < apiList.size(); i++){

            if(apiList.get(i).getCourseTitle().equalsIgnoreCase("Rest Assured Automation using Java")){
                if(apiList.get(i).getPrice().equalsIgnoreCase("50")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertTrue(false);
                }
            }

            if(apiList.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
                if(apiList.get(i).getPrice().equalsIgnoreCase("40")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertTrue(false);
                }
            }

        }


        List<Mobile> mobileList = response.getCourses().getMobile();
        for(int i =0; i < mobileList.size(); i++){

            if(mobileList.get(i).getCourseTitle().equalsIgnoreCase("Appium-Mobile Automation using Java")){
                if(mobileList.get(i).getPrice().equalsIgnoreCase("50")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertTrue(false);
                }
            }


        }

        Assert.assertEquals(response.getLinkedIn(),"https://www.linkedin.com/in/rahul-shetty-trainer/");


    }



}
