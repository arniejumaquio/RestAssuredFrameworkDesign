package rahulshettyacademy.ecommerce_end_to_end_demo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.utilities.JSONUtility;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PurchaseEndToEndTest {

    String token;
    String userId;
    String productId;
    String orderId;

    @Test(priority = 1,dataProvider = "Login Test Data")
    public void loginTest(HashMap<String, String> testData) throws IOException {

        RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail(testData.get("email"));
        loginRequest.setUserPassword(testData.get("password"));

       RequestSpecification request = given().log().all().spec(requestSpecification).body(loginRequest);
       LoginResponse response = request.when().post("/api/ecom/auth/login").as(LoginResponse.class);

       token = response.getToken();
       userId = response.getUserId();

       Assert.assertEquals(response.getUserId(),testData.get("userId"));
       Assert.assertEquals(response.getMessage(),testData.get("message"));


    }

    @DataProvider(name ="Login Test Data")
    public Object[][] getLoginTestData() throws IOException {


        List<HashMap<String, String>> testData = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/LoginTestData.json");

        return new Object[][]{{testData.get(0)}};


    }

    @Test(priority = 2)
    public void createProductTest(){

      RequestSpecification requestSpecification =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization",token).build();
      ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(201).expectContentType(ContentType.JSON).build();

      Map<String,Object> formParams = new HashMap<String,Object>();
      formParams.put("productName","Laptop");
      formParams.put("productAddedBy",userId);
      formParams.put("productCategory","fashion");
      formParams.put("productSubCategory","shirts");
      formParams.put("productPrice",10000000);
      formParams.put("productDescription","Gadget");
      formParams.put("productFor","Unisex");



     RequestSpecification request = given().log().all().spec(requestSpecification).params(formParams).multiPart("productImage",new File("src/main/resources/attachments/attachment.png"));
     String response = request.post("/api/ecom/product/add-product").then().spec(responseSpecification).extract().response().asString();

      productId =JSONUtility.getJsonValueStringFromPath(response,"productId");
      String message = JSONUtility.getJsonValueStringFromPath(response,"message");

      Assert.assertEquals(message,"Product Added Successfully");

      //productId = 6954e731c941646b7a72f8b2

    }


    @Test(priority = 3)
    public void placeOrderTest(){

        RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON)
                .addHeader("Authorization",token).build();
        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest();
        Orders orders = new Orders();
        orders.setProductOrderedId(productId);
        orders.setCountry("Firipin");
        placeOrderRequest.setOrders(Arrays.asList(orders));

        RequestSpecification request = given().log().all().spec(requestSpecification).body(placeOrderRequest);
        PlaceOrderResponse response = request.when().log().all().post("/api/ecom/order/create-order").as(PlaceOrderResponse.class);



        List<String> orderIds = response.getOrders();
        orderId = orderIds.get(0);

        List<String> productIds = response.getProductOrderId();
        String actualProductId = productIds.get(0);

        String message = response.getMessage();

        Assert.assertEquals(actualProductId,productId);
        Assert.assertEquals(message,"Order Placed Successfully");

        }

    @Test(priority = 4)
    public void getOrderDetails(){

        RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization",token)
                .addQueryParam("id",orderId).build();
        RequestSpecification request =given().spec(requestSpecification);
        OrderDetailsResponse response = request.when().get("/api/ecom/order/get-orders-details").as(OrderDetailsResponse.class);

        Assert.assertEquals(response.getMessage(),"Orders fetched for customer Successfully");

    }


    @Test(priority = 5)
    public void deleteOrderTest(){

        RequestSpecification requestSpecification =   new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization",token).build();

        ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();

        RequestSpecification request = given().spec(requestSpecification).pathParam("orderId",orderId);
        String response = request.when().delete("/api/ecom/order/delete-order/{orderId}").then().spec(responseSpecification).extract().response().asString();

        String actualMessage = JSONUtility.getJsonValueStringFromPath(response,"message");
        Assert.assertEquals(actualMessage,"Orders Deleted Successfully");

    }

    @Test(priority = 6)
    public void deleteProductTest(){

     RequestSpecification requestSpecification =   new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
             .addHeader("Authorization",token).build();

     ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200)
             .expectContentType(ContentType.JSON).build();

     RequestSpecification request = given().spec(requestSpecification).pathParam("productId",productId);
     String response = request.when().delete("/api/ecom/product/delete-product/{productId}").then().spec(responseSpecification).extract().response().asString();

     String actualMessage = JSONUtility.getJsonValueStringFromPath(response,"message");
     Assert.assertEquals(actualMessage,"Product Deleted Successfully");

    }


}












