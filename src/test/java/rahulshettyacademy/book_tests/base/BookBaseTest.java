package rahulshettyacademy.book_tests.base;

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

import static io.restassured.RestAssured.given;

public class BookBaseTest {

    protected  RequestSpecification requestSpecification;
    protected ResponseSpecification responseSpecification;

    @BeforeClass
    protected void setup(){

        try {

            //create log fileP
            PrintStream log = new PrintStream(new FileOutputStream("logs/logs.txt"));
            requestSpecification = new RequestSpecBuilder()
                    .setBaseUri(getConfig("baseurl"))
                    .setContentType(ContentType.JSON)
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .build();

            responseSpecification = new ResponseSpecBuilder()
                    .expectStatusCode(200)
                    .expectContentType(ContentType.JSON)
                    .build();

        }catch (FileNotFoundException f){
            f.printStackTrace();
        }



    }




    protected String getConfig(String key){

        try {

            FileInputStream propertiesFileInStream = new FileInputStream(System.getProperty("user.dir")+"/src/main/resources/Global.properties");
            Properties properties = new Properties();
            try {
                properties.load(propertiesFileInStream);
                 return   properties.getProperty(key);
            }catch (IOException i){
                System.out.println("IO Exception occur");
            }


        }catch (FileNotFoundException f){
            System.out.println("File not found please check file path");
        }

        return "";

    }


}
