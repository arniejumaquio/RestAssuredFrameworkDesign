package rahulshettyacademy.place_tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.utilities.AddBodyUtility;
import rahulshettyacademy.utilities.JSONUtility;

import static io.restassured.RestAssured.given;

public class PlaceTest {

    RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
            .setContentType(ContentType.JSON).addQueryParam("key","qaclick123").build();
    Response response;
    String placeId;
    String newAddress;

    @Test(dataProvider = "getAddPlaceData",priority = 1)
    public void addPlaceTest(String lat,String lng,String accuracy,String name,String phoneNumber,String address,String website,String language){


        response =  given().log().all().spec(requestSpecification).body(AddBodyUtility.getAddPlaceBody(lat,lng,accuracy,name,phoneNumber,address,website,language)).
        when().post("maps/api/place/add/json").
        then().log().all().extract().response();

        placeId = JSONUtility.getJsonValueStringFromPath(response.asString(),"place_id");
        Assert.assertTrue(response.getStatusCode() == 200);

    }

    @Test(dataProvider = "getAddPlaceData",priority = 2)
    public void getPlaceTest(String lat,String lng,String accuracy,String name,String phoneNumber,String address,String website,String language){

        response = given().log().all().spec(requestSpecification).queryParam("place_id",placeId).
        when().get("maps/api/place/get/json").
        then().log().all().extract().response();

        String actualLatitude = JSONUtility.getJsonValueStringFromPath(response.asString(),"location.latitude");
        String actualLongitude = JSONUtility.getJsonValueStringFromPath(response.asString(),"location.longitude");
        String actualAccuracy = JSONUtility.getJsonValueStringFromPath(response.asString(),"accuracy");
        String actualName = JSONUtility.getJsonValueStringFromPath(response.asString(),"name");
        String actualPhoneNumber = JSONUtility.getJsonValueStringFromPath(response.asString(),"phone_number");
        String actualAddress = JSONUtility.getJsonValueStringFromPath(response.asString(),"address");
        String actualWebsite = JSONUtility.getJsonValueStringFromPath(response.asString(),"website");
        String actualLanguage = JSONUtility.getJsonValueStringFromPath(response.asString(),"language");

        Assert.assertEquals(actualLatitude,lat);
        Assert.assertEquals(actualLongitude,lng);
        Assert.assertEquals(actualAccuracy,accuracy);
        Assert.assertEquals(actualName,name);
        Assert.assertEquals(actualPhoneNumber,phoneNumber);
        Assert.assertEquals(actualAddress,address);
        Assert.assertEquals(actualWebsite,website);
        Assert.assertEquals(actualLanguage,language);
        Assert.assertTrue(response.getStatusCode() == 200);

    }

    @Test(priority = 3)
    public void updatePlaceTest(){

        newAddress = "Edited Address";
        String expectedMsg = "Address successfully updated";

        response = given().log().all().spec(requestSpecification).body(AddBodyUtility.getUpdatePlaceBody(placeId,newAddress)).
        when().put("maps/api/place/update/json").
        then().log().all().extract().response();

        String actualMsg = JSONUtility.getJsonValueStringFromPath(response.asString(),"msg");
        Assert.assertEquals(actualMsg,expectedMsg);
        Assert.assertTrue(response.getStatusCode() == 200);

    }

    @Test(dataProvider = "getAddPlaceData",priority = 4)
    public void getPlaceTest2(String lat,String lng,String accuracy,String name,String phoneNumber,String address,String website,String language){

        response = given().log().all().queryParam("place_id",placeId).spec(requestSpecification).
                when().get("maps/api/place/get/json").
                then().log().all().extract().response();

        String actualLatitude = JSONUtility.getJsonValueStringFromPath(response.asString(),"location.latitude");
        String actualLongitude = JSONUtility.getJsonValueStringFromPath(response.asString(),"location.longitude");
        String actualAccuracy = JSONUtility.getJsonValueStringFromPath(response.asString(),"accuracy");
        String actualName = JSONUtility.getJsonValueStringFromPath(response.asString(),"name");
        String actualPhoneNumber = JSONUtility.getJsonValueStringFromPath(response.asString(),"phone_number");
        String actualAddress = JSONUtility.getJsonValueStringFromPath(response.asString(),"address");
        String actualWebsite = JSONUtility.getJsonValueStringFromPath(response.asString(),"website");
        String actualLanguage = JSONUtility.getJsonValueStringFromPath(response.asString(),"language");

        Assert.assertEquals(actualLatitude,lat);
        Assert.assertEquals(actualLongitude,lng);
        Assert.assertEquals(actualAccuracy,accuracy);
        Assert.assertEquals(actualName,name);
        Assert.assertEquals(actualPhoneNumber,phoneNumber);
        Assert.assertEquals(actualAddress,newAddress);
        Assert.assertEquals(actualWebsite,website);
        Assert.assertEquals(actualLanguage,language);
        Assert.assertTrue(response.getStatusCode() == 200);

    }

    @Test(priority = 5)
    public void deletePlaceTest(){

          String actualStatus = "OK";

          response =  given().log().all().spec(requestSpecification).body(AddBodyUtility.getDeletePlaceBody(placeId)).
           when().delete("maps/api/place/delete/json").
           then().log().all().extract().response();

          String expectedStatus = JSONUtility.getJsonValueStringFromPath(response.asString(),"status");
          Assert.assertEquals(actualStatus,expectedStatus);
        Assert.assertTrue(response.getStatusCode() == 200);

    }

    @Test(priority = 6)
    public void getPlaceTest3(){

        String actualMsg = "Get operation failed, looks like place_id  doesn't exists";

        response = given().log().all().queryParam("place_id",placeId).spec(requestSpecification).
                when().get("maps/api/place/get/json").
                then().log().all().extract().response();

        String expectedMsg = JSONUtility.getJsonValueStringFromPath(response.asString(),"msg");
        Assert.assertEquals(actualMsg,expectedMsg);
        Assert.assertTrue(response.getStatusCode() == 404,"Actual status code "+response.getStatusCode());


    }



    @DataProvider
    public Object[][] getAddPlaceData(){


        return  new Object[][]{ {"-38.383494","33.427362","50","House Of Collab","(+91) 123 456 7890","Secret","http://test.com","en-AED"}};
    }



}
