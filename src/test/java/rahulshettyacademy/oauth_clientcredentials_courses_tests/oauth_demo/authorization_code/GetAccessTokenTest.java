package rahulshettyacademy.oauth_demo.authorization_code;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import rahulshettyacademy.utilities.JSONUtility;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetAccessTokenTest {

    RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://www.googleapis.com").build();
    ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
    String authorizationCode = "";
    static String accessToken ;


    @Test
    public void getAccessTokenTest(){

            Map<String,Object> queryParams = new HashMap<String,Object>();
            queryParams.put("code",authorizationCode);
            queryParams.put("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
            queryParams.put("client_secret","erZOWM9g3UtwNRj340YYaK_W");
            queryParams.put("redirect_uri","https://rahulshettyacademy.com/getCourse.php");
            queryParams.put("grant_type","authorization_code");

            RequestSpecification request = given().log().all().spec(requestSpecification).queryParams(queryParams);
            String response =request.when().post("/oauth2/v4/token").then().spec(responseSpecification).extract().response().asString();

            accessToken = JSONUtility.getJsonValueStringFromPath(response,"access_token");
            System.out.println(accessToken);

    }


}
