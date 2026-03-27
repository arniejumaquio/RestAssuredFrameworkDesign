package rahulshettyacademy.oauth_clientcredentials_courses_tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.pojo_classes.token_apis.OauthResponse;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AccessTokenTest {

    RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").build();
    public static String accessToken;

    @Test
    public void getAccessTokenTest(){

        Map<String,String> formParams = new HashMap<String,String>();
        formParams.put("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
        formParams.put("client_secret","erZOWM9g3UtwNRj340YYaK_W");
        formParams.put("grant_type","client_credentials");
        formParams.put("scope","trust");

       RequestSpecification request =  given().log().all().spec(requestSpecification).formParams(formParams);
       OauthResponse response = request.when().post("oauthapi/oauth2/resourceOwner/token").then().log().all().extract().response().as(OauthResponse.class);
       accessToken = response.getAccess_token();

       Assert.assertNotNull(response.getAccess_token());
       Assert.assertEquals(response.getToken_type(),"Bearer");
       Assert.assertEquals(response.getExpires_in(),3600);
       Assert.assertNotNull(response.getRefresh_token());
       Assert.assertEquals(response.getScope(),"create");

    }


}
