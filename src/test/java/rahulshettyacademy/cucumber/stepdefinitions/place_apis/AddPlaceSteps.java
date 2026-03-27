package rahulshettyacademy.cucumber.stepdefinitions.place_apis;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import rahulshettyacademy.cucumber.stepdefinitions.base.place_apis.StepBase;
import rahulshettyacademy.pojo_classes.place_apis.response.AddPlaceResponse;
import rahulshettyacademy.utilities.JSONUtility;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AddPlaceSteps extends StepBase {

    RequestSpecification request;
    Response response;
    ObjectMapper objectMapper;
    HashMap<String,Object> matchedTestData;
    HashMap<String,Object> payload;
    HashMap<String,Object> headers;
    AddPlaceResponse addPlaceResponse;
    String placeId;

    @Given("^add place payload from (.+) and (.+)$")
    public void add_place_payload(String tcId,String testData) throws IOException {

        setup();

        List<HashMap<String,Object>> addPlaceTestDatas;
        objectMapper = new ObjectMapper();

        if(testData.equalsIgnoreCase("functional")){

            addPlaceTestDatas = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/place_apis/add/AddPlaceFunctional.json");
            matchedTestData =   addPlaceTestDatas.stream().filter(addPlaceTestData -> addPlaceTestData.get("tcId").equals(tcId) ).findFirst().orElse(null);
            payload = (HashMap<String, Object>)   matchedTestData.get("request");
        }

        request = given()
                .log()
                .all()
                .spec(requestSpecification)
                .queryParam("key","qaclick123").body(payload);
    }

    @And("add place header")
    public void add_place_header(){

        headers = (HashMap<String, Object>) matchedTestData.get("headers");
        request.headers(headers);

    }

    @When("^(.+) is called with (.+) method$")
    public void is_called_with_method(String addPlaceEndpoint,String httpMethod){

        if(httpMethod.equalsIgnoreCase("POST")){
            response =  request.when().post("/maps/api/place/add/json").then().log().all().spec(responseSpecification).extract().response();
        }else if(httpMethod.equalsIgnoreCase("PUT")){
            response =  request.when().put("/maps/api/place/add/json").then().log().all().spec(responseSpecification).extract().response();
        }else if(httpMethod.equalsIgnoreCase("PATCH")){
            response =  request.when().patch("/maps/api/place/add/json").then().log().all().spec(responseSpecification).extract().response();
        }else if(httpMethod.equalsIgnoreCase("DELETE")){
            response =  request.when().delete("/maps/api/place/add/json").then().log().all().spec(responseSpecification).extract().response();
        }else if(httpMethod.equalsIgnoreCase("GET")){
            response =  request.when().get("/maps/api/place/add/json").then().log().all().spec(responseSpecification).extract().response();
        }else{
            System.out.println("invalid http method");
        }

    }

    @Then("^status code is (.+)$")
    public void status_code_is(int statusCode){
        Assert.assertEquals(response.getStatusCode(),statusCode);
    }

    @And("response is successful")
    public void response_is_successful(){

        addPlaceResponse = response.as(AddPlaceResponse.class);
        placeId = addPlaceResponse.getPlace_id();

        Assert.assertEquals(addPlaceResponse.getStatus(),"OK");
        Assert.assertNotNull(addPlaceResponse.getPlace_id());
        Assert.assertEquals(addPlaceResponse.getScope(),"APP");
        Assert.assertNotNull(addPlaceResponse.getReference());
        Assert.assertNotNull(addPlaceResponse.getId());

    }


}
