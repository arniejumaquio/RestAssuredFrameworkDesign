package rahulshettyacademy.pojo_classes_demo.deserialization;

import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.utilities.JSONUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetCourseDetailsTest2 {

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

       CourseDetails courseDetails = given().queryParam("access_token", accessToken).
        when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(CourseDetails.class);


        //print the price of SoupUi course
       List<API> apis = courseDetails.getCourses().getApi();
       for(int i =0; i < apis.size(); i++){

           if(apis.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
               Assert.assertEquals(apis.get(i).getPrice(),"40");
               break;
           }

       }

       //print all course title and price of web automation
       List<WebAutomation> webAutomations =  courseDetails.getCourses().getWebAutomation();
       for(int i = 0; i < webAutomations.size(); i++){

          String courseTitle = webAutomations.get(i).getCourseTitle();
          String price = webAutomations.get(i).getPrice();
          System.out.println(courseTitle);
          System.out.println(price);

       }

       //assert expected coursetitle match with the actual title same with price
        ArrayList<String> expectedCourseTitles = new ArrayList<String>();
        expectedCourseTitles.add("Selenium Webdriver Java");
        expectedCourseTitles.add("Cypress");
        expectedCourseTitles.add("Protractor");

        ArrayList<String> actualCourseTitles = new ArrayList<String>();

        ArrayList<String> expectedCoursePrices = new ArrayList<String>();
        expectedCoursePrices.add("50");
        expectedCoursePrices.add("40");
        expectedCoursePrices.add("40");

        ArrayList<String> actualCoursePrices = new ArrayList<String>();

        List<WebAutomation> webAutomations2 = courseDetails.getCourses().getWebAutomation();
        for(int i = 0; i < webAutomations2.size(); i++){
            actualCoursePrices.add(webAutomations2.get(i).getPrice());
        }


        Assert.assertTrue(actualCoursePrices.equals(expectedCoursePrices));


    }

}
