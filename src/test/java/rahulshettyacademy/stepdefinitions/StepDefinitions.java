package rahulshettyacademy.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


import rahulshettyacademy.PlaceAPIResources;
import rahulshettyacademy.builder.TestDataBuilder;
import rahulshettyacademy.pojo_classes.place_apis.response.GetPlaceResponse;
import rahulshettyacademy.utilities.JSONUtility;

import java.io.IOException;


import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StepDefinitions extends StepBase {

    RequestSpecification request;
    ResponseSpecification responseSpecification;
    Response response;
    TestDataBuilder testDataBuilder = new TestDataBuilder();
    public static String placeId;


    @Given("add place payload {string} {string} {string} {string} {string} {string} {string} {string} {string}")
    public void add_place_payload(String lat, String lng, String accuracy, String name, String phone_number, String address, String types, String website, String language) throws IOException {

        //convert variables to correct data type before passing the arguments to  the testdata builder method

        Double latDouble = Double.parseDouble(lat);
        Double lngDouble = Double.parseDouble(lng);
        String[] typesArr = types.split(",");
         request = given().spec(requestSpecification()).body(testDataBuilder.getAddPlacePayload( latDouble,lngDouble,  accuracy,  name,  phone_number,  address,  typesArr,  website,  language));


    }


    @Given("add place payload")
    public void add_place_payload() throws IOException {

        //convert variables to correct data type before passing the arguments to  the testdata builder method

        request = given().spec(requestSpecification()).body(testDataBuilder.getAddPlacePayload( ));


    }


    @When("user calls {string} with {string}  request")
    public void user_calls_with_request(String resource,String httpMethod) {

          PlaceAPIResources placeApiResources = PlaceAPIResources.valueOf(resource);
          String resourceUrl = placeApiResources.getResource();

          if(httpMethod.equalsIgnoreCase("post")) {
              response = request.when().post(resourceUrl).then().extract().response();
          }else if(httpMethod.equalsIgnoreCase("put")){
              response = request.when().put(resourceUrl).then().extract().response();
          }else if(httpMethod.equalsIgnoreCase("delete")){
              response = request.when().delete(resourceUrl).then().extract().response();
          }else if(httpMethod.equalsIgnoreCase("get")){
              response = request.when().get(resourceUrl).then().extract().response();
          }else{
              System.out.println("Invalid http method");
          }
    }


    @Then("the  status code is {string}")
    public void the_status_code_is(String statusCode) {

       int actualStatusCode = response.getStatusCode();
       assertEquals(String.valueOf(actualStatusCode),statusCode);

    }


    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String value) {

      String actualValue = JSONUtility.getJsonValueStringFromPath(response.asString(),key);
       assertEquals(actualValue,value);
    }


    @Then("response body contains {string}")
    public void response_body_contains(String key) {

        String value =JSONUtility.getJsonValueStringFromPath(response.asString(),key);
        assertNotNull(value);

        if(key.equalsIgnoreCase("place_id")){
            placeId = value;
        }

    }


    @Then("verify all details {string} {string} {string} {string} {string} {string} {string} {string} {string} are added in {string} with {string}  request")
    public void verify_all_details_are_added_in_with_request(String lat, String lng, String accuracy, String name, String phone_number, String address, String types, String website, String language,
            String resource, String httpMethod) {


        request = given().spec(requestSpecification).queryParam("place_id",placeId);
        user_calls_with_request(resource,httpMethod);

        GetPlaceResponse getPlaceResponse =  response.as(GetPlaceResponse.class);

        assertEquals(getPlaceResponse.getLocation().getLatitude(), lat);
        assertEquals(getPlaceResponse.getLocation().getLongitude(), lng);
        assertEquals(getPlaceResponse.getAccuracy().toString(),accuracy);
        assertEquals(getPlaceResponse.getName(),name);
        assertEquals(getPlaceResponse.getPhone_number(),phone_number);
        assertEquals(getPlaceResponse.getAddress(),address);
        assertEquals(getPlaceResponse.getTypes(),types);
        assertEquals(getPlaceResponse.getWebsite(),website);
        assertEquals(getPlaceResponse.getLanguage(),language);


    }

    @Given("delete place payload")
    public void delete_place_payload(){


            request = given().spec(requestSpecification).body(testDataBuilder.getDeletePlacePayload(placeId));



    }


}
