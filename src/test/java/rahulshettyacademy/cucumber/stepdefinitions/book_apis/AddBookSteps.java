package rahulshettyacademy.cucumber.stepdefinitions.book_apis;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import rahulshettyacademy.cucumber.stepdefinitions.StepBase;
import rahulshettyacademy.utilities.JSONUtility;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class AddBookSteps extends StepBase {

    String baseUrl = RestAssured.baseURI = "https://rahulshettyacademy.com";
    RequestSpecification request;
    Response response;
    Map<String,Object> matchedTestData;
    String isbn;
    String aisle;

    @And("add book header")
    public void add_book_header(){
            Map<String,Object> headers = (Map<String, Object>) matchedTestData.get("headers");
            request.headers(headers);
    }

    @When("^call to (.+) with (.+) request$")
    public void call_to_with_request(String addBookEndpoint,String httpMethod){

        if(httpMethod.equalsIgnoreCase("POST")){
            response =  request.when().post(baseUrl+addBookEndpoint).then().log().all().extract().response();
           
        }else if(httpMethod.equalsIgnoreCase("GET")){
            response = request.when().get(baseUrl+addBookEndpoint).then().log().all().extract().response();
        }else if(httpMethod.equalsIgnoreCase("DELETE")){
             response = request.when().delete(baseUrl+addBookEndpoint).then().log().all().extract().response();
        }else if(httpMethod.equalsIgnoreCase("PUT")){
            response = request.when().put(baseUrl+addBookEndpoint).then().log().all().extract().response();
        }else if(httpMethod.equalsIgnoreCase("PATCH")){
            response = request.when().patch(baseUrl+addBookEndpoint).then().log().all().extract().response();
        }else {
            System.out.println("Invalid HTTP Method");
        }

    }

    @Then("^status code is (.+)$")
    public void status_code_is(int statusCode){
        Assert.assertEquals(response.getStatusCode(),statusCode);
    }

    @And("success response is displayed")
    public void success_response_is_displayed_and(){
        response_body_matches_expected();

        Map<String,Object> requestPayLoadInMap = (Map<String, Object>) matchedTestData.get("request");
        isbn = (String) requestPayLoadInMap.get("isbn");
        aisle = (String) requestPayLoadInMap.get("aisle");

        Assert.assertEquals( JSONUtility.getJsonValueFromPath(response.asString(),"ID"),isbn+aisle);
    }

    @Given("^add book payload for (.+) from (.+)$")
    public void add_book_payload_for_from(String testCaseId,String testData){

        try {

            List<HashMap<String, Object>> addBookTestDatas = JSONUtility.getDataFromJsonFile(testData);
            matchedTestData = addBookTestDatas.stream().filter(eachAddBookTestData -> eachAddBookTestData.get("testCaseId").equals(testCaseId)).findFirst().orElse(null);
            Map<String,Object> requestPayLoadInMap = (Map<String, Object>) matchedTestData.get("request");


            if( !(testCaseId.equalsIgnoreCase("TC2")) && !testCaseId.equalsIgnoreCase("TC7") && !testCaseId.equalsIgnoreCase("TC8") && !testCaseId.equalsIgnoreCase("TC9") && !testCaseId.equalsIgnoreCase("TC21")  && !testCaseId.equalsIgnoreCase("TC22")) {
                requestPayLoadInMap.put("isbn", JSONUtility.generateRandomNumber());
            }

            Object requestPayLoadInObject = JSONUtility.convertMapToObject(requestPayLoadInMap);
            request =  given().log().all().body(requestPayLoadInObject);

        }catch (IOException i){

        }

    }

    @Then("error response is displayed")
    public void error_response_is_displayed(){
        response_body_matches_expected();
    }

    public void response_body_matches_expected(){

        if (response.asString() == null || response.asString().isEmpty()) {
            return; //internal server error
        }

       Map<String,Object> expectedResponse = (Map<String,Object>) matchedTestData.get("expectedResponse");
       for(Map.Entry<String,Object> entry:expectedResponse.entrySet()){
          String jsonPath =  entry.getKey();
          Object expectedValue = entry.getValue();
          Object actualValue = JSONUtility.getJsonValueFromPath(response.asString(),jsonPath);
           Assert.assertEquals(actualValue,expectedValue,"Actual value does not match expected value");
       }

    }

}
