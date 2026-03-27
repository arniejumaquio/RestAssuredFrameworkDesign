package rahulshettyacademy.cucumber.runner.book_apis;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/rahulshettyacademy/cucumber/features/book_apis", glue = "rahulshettyacademy.cucumber.stepdefinitions.book_apis" , monochrome = true, plugin = {"html:target/cucumber-html-report.html", "pretty"})
public class AddBookRunner extends AbstractTestNGCucumberTests {


}