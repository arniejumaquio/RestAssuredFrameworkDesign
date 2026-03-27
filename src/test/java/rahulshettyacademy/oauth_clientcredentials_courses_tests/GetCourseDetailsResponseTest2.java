package rahulshettyacademy.oauth_clientcredentials_courses_tests;

import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.pojo_classes.course_details_apis.API;
import rahulshettyacademy.pojo_classes.course_details_apis.CourseDetailsResponse;
import rahulshettyacademy.pojo_classes.course_details_apis.Mobile;
import rahulshettyacademy.pojo_classes.course_details_apis.WebAutomation;
import rahulshettyacademy.utilities.JSONUtility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetCourseDetailsResponseTest2 {

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
    public void getCourseDetailsTest2(){

       CourseDetailsResponse courseDetailsResponse = given().queryParam("access_token", accessToken).
        when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(CourseDetailsResponse.class);


        //print the price of SoupUi course
       List<API> apis = courseDetailsResponse.getCourses().getApi();
       for(int i =0; i < apis.size(); i++){

           if(apis.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
               Assert.assertEquals(apis.get(i).getPrice(),"40");
               break;
           }

       }

       //print all course title and price of web automation
       List<WebAutomation> webAutomations =  courseDetailsResponse.getCourses().getWebAutomation();
       for(int i = 0; i < webAutomations.size(); i++){

          String courseTitle = webAutomations.get(i).getCourseTitle();
          String price = webAutomations.get(i).getPrice();
          System.out.println(courseTitle);
          System.out.println(price);

       }

       //print all the course and price
       List<WebAutomation> webList =  courseDetailsResponse.getCourses().getWebAutomation();
       List<API> apiList = courseDetailsResponse.getCourses().getApi();
       List<Mobile> mobileList =courseDetailsResponse.getCourses().getMobile();
       for(WebAutomation eachWebAutomation:webList){
           System.out.println("Course "+eachWebAutomation.getCourseTitle());
           System.out.println("Price "+eachWebAutomation.getPrice());
       }

       for(API eachApi:apiList){
           System.out.println("Course "+eachApi.getCourseTitle());
           System.out.println("Price "+eachApi.getPrice());
       }

       for(Mobile eachMobile:mobileList){
           System.out.println("Course "+eachMobile.getCourseTitle());
           System.out.println("Price "+eachMobile.getPrice());
       }


       //assert expected coursetitle match with the actual title same with price
        ArrayList<String> expectedCourseTitles = new ArrayList<String>();
        expectedCourseTitles.add("Selenium Webdriver Java");
        expectedCourseTitles.add("Cypress");
        expectedCourseTitles.add("Protractor");

        ArrayList<String> expectedCoursePrices = new ArrayList<String>();
        expectedCoursePrices.add("50");
        expectedCoursePrices.add("40");
        expectedCoursePrices.add("40");

        ArrayList<String> actualCourseTitles = new ArrayList<String>();
        ArrayList<String> actualCoursePrices = new ArrayList<String>();

        List<WebAutomation> webAutomations2 = courseDetailsResponse.getCourses().getWebAutomation();
        for(int i = 0; i < webAutomations2.size(); i++){
            actualCourseTitles.add(webAutomations2.get(i).getCourseTitle());
            actualCoursePrices.add(webAutomations2.get(i).getPrice());
        }


        Assert.assertTrue(actualCourseTitles.equals(expectedCourseTitles));
        Assert.assertTrue(actualCoursePrices.equals(expectedCoursePrices));


    }

}
