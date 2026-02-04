package rahulshettyacademy;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import rahulshettyacademy.pojo_classes.place_apis.request.AddPlaceRequest;
import rahulshettyacademy.pojo_classes.place_apis.request.DeletePlaceRequest;
import rahulshettyacademy.pojo_classes.place_apis.request.Location;
import rahulshettyacademy.pojo_classes.place_apis.request.UpdatePlaceRequest;
import rahulshettyacademy.utilities.JSONUtility;
import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AddPlaceTest {

    public static void main(String[] args) throws IOException {

        RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").setContentType(ContentType.JSON)
                .addQueryParam("key","qaclick123").build();

        ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        //Add Place

        AddPlaceRequest addPlaceRequest = new AddPlaceRequest(new Location(-38.383494,33.427362),50,"Frontline house","(+91) 983 893 3937","29, side layout, cohen 09", Arrays.asList("shoe park","shop"),"http://google.com","French-IN" );

       String response = given().log().all().spec(requestSpecification).body(addPlaceRequest).
       when().post("maps/api/place/add/json").
       then().log().all().spec(responseSpecification).body("scope",equalTo("APP"))
               .extract().response().asString();

       String placeId = JSONUtility.getJsonValueStringFromPath(response,"place_id");


      //Update Place
        String address = "Purokyot 67,Kapitangan Phils.";
        UpdatePlaceRequest updatePlaceRequest = new UpdatePlaceRequest();
        updatePlaceRequest.setAddress(address);
        updatePlaceRequest.setPlace_id(placeId);
        updatePlaceRequest.setKey("qaclick123");

        given().log().all().spec(requestSpecification).body(updatePlaceRequest).
        when().put("maps/api/place/update/json").
        then().log().all().spec(responseSpecification).body("msg",equalTo("Address successfully updated"));


       //Get Place
        Map<String,Object> queryParams = new HashMap<String,Object>();
        queryParams.put("key","qaclick123");
        queryParams.put("place_id",placeId);
        response = given().log().all() .spec(requestSpecification).queryParams(queryParams).
        when().get("maps/api/place/get/json").
        then().log().all().spec(responseSpecification).extract().response().asString();


        String actualAddress =  JSONUtility.getJsonValueStringFromPath(response,"address");
        Assert.assertEquals(actualAddress,address);


       //Delete Place
        DeletePlaceRequest deletePlaceRequest = new DeletePlaceRequest();
        deletePlaceRequest.setPlace_id(placeId);

       given().log().all().spec(requestSpecification).body(deletePlaceRequest).
       when().delete("maps/api/place/delete/json").
       then().log().all().spec(responseSpecification).body("status",equalTo("OK"));


        //Get Place
        given().log().all() .spec(requestSpecification).queryParams(queryParams).
        when().get("maps/api/place/get/json").
        then().log().all().assertThat().statusCode(404).body("msg",equalTo("Get operation failed, looks like place_id  doesn't exists"));


    }

}
