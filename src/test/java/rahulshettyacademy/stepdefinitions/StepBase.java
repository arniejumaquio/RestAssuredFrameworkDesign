package rahulshettyacademy.stepdefinitions;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.*;
import java.util.Properties;

public class StepBase {

    protected static RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    public RequestSpecification requestSpecification() throws IOException {

        if(requestSpecification == null) {
            PrintStream logStream = new PrintStream(new FileOutputStream("src/test/resources/log.txt"));
            requestSpecification = new RequestSpecBuilder().setBaseUri(getPropertyValue("baseurl")).setContentType(ContentType.JSON)
                    .addQueryParam("key", "qaclick123").addFilter(RequestLoggingFilter.logRequestTo(logStream))
                    .addFilter(ResponseLoggingFilter.logResponseTo(logStream)).build();
        }

         return requestSpecification;

    }


    public ResponseSpecification responseSpecification(){

        responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        return responseSpecification;
    }

    protected String getPropertyValue(String key) throws IOException {

        FileInputStream propStream = new FileInputStream("src/test/resources/Global.properties");
        Properties config = getConfig();
        config.load(propStream);

        String value =  config.getProperty(key);
        return value;


    }

    private Properties getConfig(){

        Properties config = new Properties();

        return config;

    }



}
