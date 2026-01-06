package rahulshettyacademy.oauth_demo.authorization_code;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import rahulshettyacademy.pojo_classes_demo.deserialization.CourseDetails;

import static io.restassured.RestAssured.given;

public class GetCourseDetailsUsingAccessTokenTest {

    RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
    ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();


    @Test(dependsOnMethods = "getAccessTokenTest")
    public void getCourseDetailsUsingAccessTokenTest(){

        RequestSpecification request = given().log().all().spec(requestSpecification).queryParam("access_token",GetAccessTokenTest.accessToken);
        CourseDetails courseDetails =  request.when().get("/getCourse.php").then().spec(responseSpecification).extract().response().as(CourseDetails.class);

    }

}
