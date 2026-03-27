package rahulshettyacademy.cucumber.runner.place_apis;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/rahulshettyacademy/cucumber/features/place_apis",glue = "rahulshettyacademy.cucumber.stepdefinitions.place_apis",
monochrome = true,plugin = {"html:target/cucumber-reports"})
public class AddPlaceRunner extends AbstractTestNGCucumberTests {

}
