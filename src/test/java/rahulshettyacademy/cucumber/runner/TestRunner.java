package rahulshettyacademy.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/rahulshettyacademy/features/AddPlace", glue ="rahulshettyacademy.stepdefinitions")
public class TestRunner {



}
