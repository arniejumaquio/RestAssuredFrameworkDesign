package rahulshettyacademy.base;

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

public class BaseTest {

    protected RequestSpecification requestSpecification;
    protected ResponseSpecification responseSpecification;

    @BeforeClass
    public void setup() throws IOException {

        PrintStream logStream = new PrintStream(new FileOutputStream("logs/logs.txt"));
        requestSpecification = new RequestSpecBuilder().setBaseUri(getPropertyValue("baseUri")).setContentType(ContentType.JSON).addQueryParam("key","qaclick123")
                .addFilter(RequestLoggingFilter.logRequestTo(logStream))
                .addFilter(ResponseLoggingFilter.logResponseTo(logStream))
                .build();

        responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
    }


    private String getPropertyValue(String key) throws IOException {

        FileInputStream propertiesInStream = new FileInputStream("src/main/resources/test_data/book_apis/Place.properties");
        Properties properties = new Properties();
        properties.load(propertiesInStream);

        return   properties.getProperty(key);

    }




}
