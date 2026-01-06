package rahulshettyacademy.oauth_demo.authorization_code;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetAuthorizationCodeTest {

    RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://accounts.google.com").build();


    @Test
    public void getAuthorizationCodeTest(){

        Map<String,Object> queryParaams = new HashMap<String,Object>();
        queryParaams.put("scope","https://www.googleapis.com/auth/userinfo.email");
        queryParaams.put("auth_url","https://accounts.google.com/o/oauth2/v2/auth");
        queryParaams.put("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
        queryParaams.put("response_type","code");
        queryParaams.put("redirect_uri","https://rahulshettyacademy.com/getCourse.php");

        RequestSpecification request = given().log().all().spec(requestSpecification).queryParams(queryParaams);
        String response =request.when().get("/o/oauth2/v2/auth").then().extract().response().asString();

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


        String url = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php";
        driver.get(url);


        WebElement emailField = driver.findElement(By.cssSelector("input#identifierId"));
        emailField.sendKeys("arniejumaquiomaya@gmail.com");

        WebElement nextButton = driver.findElement(By.xpath("//span[text()='Next']"));
        nextButton.click();





    }



}
