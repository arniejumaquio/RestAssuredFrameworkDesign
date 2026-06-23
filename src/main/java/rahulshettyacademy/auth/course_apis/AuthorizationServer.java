package rahulshettyacademy.auth.course_apis;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import rahulshettyacademy.utilities.JSONUtility;


import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class AuthorizationServer {

    private static String accessToken;

    public static String getAccessToken(){

        if(accessToken == null){
            //fetch token
            fetchToken();
        }

        return accessToken;

    }

    public static String fetchToken(){

        HashMap<String,String> formParams = new HashMap<String,String>();
        formParams.put("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
        formParams.put("client_secret","erZOWM9g3UtwNRj340YYaK_W");
        formParams.put("grant_type","client_credentials");
        formParams.put("scope","trust");

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        RequestSpecification request = given().log().all().formParams(formParams);
        Response response = request.when().post("/oauthapi/oauth2/resourceOwner/token").then().log().all().extract().response();
        accessToken = JSONUtility.getJsonValueStringFromPath(response.asString(),"access_token");

        return accessToken;
    }


    //for teardown
    public void clearToken(){
        accessToken = null;
    }



}
