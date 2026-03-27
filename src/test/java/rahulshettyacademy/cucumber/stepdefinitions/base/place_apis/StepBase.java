package rahulshettyacademy.cucumber.stepdefinitions.base.place_apis;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import java.io.*;
import java.util.Properties;

public class StepBase {

    public RequestSpecification requestSpecification;
    public ResponseSpecification responseSpecification;

    @BeforeClass
    public void setup() throws FileNotFoundException {

        if (requestSpecification == null) {

            PrintStream logStream = new PrintStream(new FileOutputStream("src/test/resources/log.txt"));
            requestSpecification = new RequestSpecBuilder()
                    .setBaseUri(getConfig("baseurl"))
                    .addFilter(RequestLoggingFilter.logRequestTo(logStream))
                    .addFilter(ResponseLoggingFilter.logResponseTo(logStream))
                    .build();
        }

        if (responseSpecification == null) {
            responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        }

    }


    protected String getConfig(String key) {

        try {

            FileInputStream propertiesInStream = new FileInputStream("src/main/resources/Global.properties");
            Properties properties = new Properties();
            properties.load(propertiesInStream);
            return properties.getProperty(key);

        } catch (FileNotFoundException f) {
            System.out.println("Cannot find the file.Please check file path if correct");
        } catch (IOException i) {
            System.out.println("IO exception occur");

        }

        return null;

    }


}
