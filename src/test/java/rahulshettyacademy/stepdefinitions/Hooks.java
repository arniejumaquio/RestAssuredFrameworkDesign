package rahulshettyacademy.stepdefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {

        StepDefinitions stepDefinitions = new StepDefinitions();

        if(StepDefinitions.placeId == null) {
            stepDefinitions.add_place_payload();
            stepDefinitions.user_calls_with_request("AddPlaceAPI", "POST");
            stepDefinitions.response_body_contains("place_id");
        }

    }



}
