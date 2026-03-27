package rahulshettyacademy.oauth_clientcredentials_courses_tests.oauth_demo.authorization_code;

import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.cucumber.stepdefinitions.base.place_apis.StepBase;
import rahulshettyacademy.pojo_classes.course_details_apis.API;
import rahulshettyacademy.pojo_classes.course_details_apis.CourseDetailsResponse;
import rahulshettyacademy.pojo_classes.course_details_apis.Mobile;
import rahulshettyacademy.pojo_classes.course_details_apis.WebAutomation;
import rahulshettyacademy.pojo_classes.token_apis.OauthResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class CourseDetailsTest extends StepBase {


    @Test
    public String getAccessToken(){

        Map<String,Object> formParams = new HashMap<String, Object>();
        formParams.put("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
        formParams.put("client_secret","erZOWM9g3UtwNRj340YYaK_W");
        formParams.put("grant_type","client_credentials");
        formParams.put("scope","trust");

        RequestSpecification request = given().spec(requestSpecification).formParams(formParams);
        OauthResponse oauthResponse = request.when().post("/oauthapi/oauth2/resourceOwner/token").then().assertThat().statusCode(200).extract().response().as(OauthResponse.class);
        String accessToken = oauthResponse.getAccess_token();


     return accessToken;

    }

    @Test
    public void getCourseDetails(){

        String accessToken = getAccessToken();
        RequestSpecification request = given().spec(requestSpecification).queryParam("access_token",accessToken);
        CourseDetailsResponse courseDetailsResponse =  request.when().get("/oauthapi/getCourseDetails").then().assertThat().statusCode(401).extract().response().as(CourseDetailsResponse.class);
        Assert.assertEquals(courseDetailsResponse.getInstructor(),"RahulShetty");
        Assert.assertEquals(courseDetailsResponse.getUrl(),"rahulshettycademy.com");
        Assert.assertEquals(courseDetailsResponse.getServices(),"projectSupport");
        Assert.assertEquals(courseDetailsResponse.getExpertise(),"Automation");
        List<WebAutomation> webAutomationList =courseDetailsResponse.getCourses().getWebAutomation();
        for(WebAutomation webAutomation:webAutomationList){

            if(webAutomation.getCourseTitle().equalsIgnoreCase("Selenium Webdriver Java")){
                if(webAutomation.getPrice().equalsIgnoreCase("50")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertTrue(false);
                }
            }

            if(webAutomation.getCourseTitle().equalsIgnoreCase("Cypress")){
                if(webAutomation.getPrice().equalsIgnoreCase("40")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertTrue(false);
                }
            }

            if(webAutomation.getCourseTitle().equalsIgnoreCase("Protractor")){
                if(webAutomation.getPrice().equalsIgnoreCase("40")){
                    Assert.assertTrue(true);
                }else {
                    Assert.assertTrue(false);
                }
            }

        }

      List<API> apiList =  courseDetailsResponse.getCourses().getApi();
      for(API api:apiList){

          if(api.getCourseTitle().equalsIgnoreCase("Rest Assured Automation using Java")){
              if(api.getPrice().equalsIgnoreCase("50")){
                  Assert.assertTrue(true);
              }else {
                  Assert.assertTrue(false);
              }
          }

          if(api.getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
              if(api.getPrice().equalsIgnoreCase("40")){
                  Assert.assertTrue(true);
              }else{
                  Assert.assertTrue(false);
              }
          }


      }

     List<Mobile> mobileList = courseDetailsResponse.getCourses().getMobile();
     for(Mobile mobile:mobileList){
         if(mobile.getCourseTitle().equalsIgnoreCase("Appium-Mobile Automation using Java")){
            if(mobile.getPrice().equalsIgnoreCase("50")){
                Assert.assertTrue(true);
            }else {
                Assert.assertTrue(false);
            }
         }
     }


     Assert.assertEquals(courseDetailsResponse.getLinkedIn(),"https://www.linkedin.com/in/rahul-shetty-trainer/");

    }



}
