package restassuredframeworkdesign.auth.ecom_apis;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import restassuredframeworkdesign.models.ecommerce_apis.request.LoginRequest;
import restassuredframeworkdesign.utilities.JSONUtility;


import static io.restassured.RestAssured.given;

public class Login {

    private static String token;


    public static String getToken(String userEmail,String userPassword){

        if(token ==null){
            //fetch token
            fetchToken(userEmail,userPassword);
        }

        return token;

    }


    public static void fetchToken(String userEmail,String userPassword){

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        LoginRequest loginRequest = new LoginRequest(userEmail, userPassword);
        RequestSpecification request = given().body(loginRequest);
        Response response = request.when().post("/api/ecom/auth/login").then().extract().response();

        Assert.assertTrue(response.getStatusCode()==200);
        token = JSONUtility.getJsonValueStringFromPath(response.asString(),"token");


    }

    public void clearToken(){
        token = null;
    }




}
