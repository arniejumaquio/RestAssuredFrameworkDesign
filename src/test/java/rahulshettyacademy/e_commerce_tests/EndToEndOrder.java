package rahulshettyacademy.e_commerce_tests;

import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.base.BaseTest;
import rahulshettyacademy.pojo_classes.ecommerce_apis.request.*;
import rahulshettyacademy.pojo_classes.ecommerce_apis.response.*;
import rahulshettyacademy.utilities.JSONUtility;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import static io.restassured.RestAssured.given;

public class EndToEndOrder extends BaseTest {

    RequestSpecification request;
    LoginResponse loginResponse;
    AddToCartResponse addToCartResponse;
    PlaceOrderResponse placeOrderResponse;
    GetOrderDetailsResponse getOrderDetailsResponse;
    DeleteOrderResponse deleteOrderResponse;
    String token;
    String userId;
    String orderId;

    //pojo
    @Test(dataProvider = "getLoginTestData",priority = 1)
    public void loginTest(HashMap<String,String> loginTestData){

        LoginRequest loginRequest = new LoginRequest(loginTestData.get("email"),loginTestData.get("password"));

        request = given().log().all().spec(requestSpecification).body(loginRequest);

        loginResponse = request.when().post("/api/ecom/auth/login").then().log().all().assertThat().statusCode(200).extract().response().as(LoginResponse.class);
        token = loginResponse.getToken();
        userId = loginResponse.getUserId();

        //assert
        Assert.assertNotNull(token);
        Assert.assertEquals(loginResponse.getUserId(),loginTestData.get("userId"));
        Assert.assertEquals(loginResponse.getMessage(),"Login Successfully");

    }

    @Test(dataProvider = "getAddToCartTestData",priority = 2)
    public void addToCartTest(String _id,String productId,String productName,String productCategory,String productSubCategory,
                               int productPrice,String productDescription,String productImage,String productRating,
                               String productTotalOrders,boolean productStatus,String productFor,
                               String productAddedBy,int __v){


        AddToCartRequest addToCartRequest = new AddToCartRequest(_id,new Product(productId,productName,productCategory,productSubCategory,productPrice,productDescription,productImage,productRating,productTotalOrders,productStatus,productFor,productAddedBy,__v));
        request = given().log().all().spec(requestSpecification).header("Authorization",token).body(addToCartRequest);
        addToCartResponse = request.when().post("/api/ecom/user/add-to-cart").then().log().all().assertThat().statusCode(200).extract().as(AddToCartResponse.class);

        Assert.assertEquals(addToCartResponse.getMessage(),"Product Added To Cart");

    }

    @Test(dataProvider = "getPlaceOrderTestData",priority = 3)
    public void placeOrderTest(String country,String productId){

        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest(Arrays.asList(new Orders(country,productId)));
        request = given().log().all().spec(requestSpecification).header("Authorization",token).body(placeOrderRequest);
        placeOrderResponse = request.when().post("/api/ecom/order/create-order").then().log().all().assertThat().statusCode(201).extract().as(PlaceOrderResponse.class);

        orderId = placeOrderResponse.getOrders().get(0);

        Assert.assertNotNull(orderId);
        Assert.assertEquals(placeOrderResponse.getProductOrderId().get(0),productId);
        Assert.assertEquals(placeOrderResponse.getMessage(),"Order Placed Successfully");

    }

    @Test(dataProvider = "getAddToCartTestData", priority = 4)
    public void getOrderDetailsTest(String _id,String productId,String productName,String productCategory,String productSubCategory,
                                    int productPrice,String productDescription,String productImage,String productRating,
                                    String productTotalOrders,boolean productStatus,String productFor,
                                    String productAddedBy,int __v){

        request = given().log().all().spec(requestSpecification).header("Authorization",token).queryParam("id",orderId);
        getOrderDetailsResponse = request.when().get("/api/ecom/order/get-orders-details").then().log().all().assertThat().statusCode(200).extract().response().as(GetOrderDetailsResponse.class);

        Assert.assertEquals( getOrderDetailsResponse.getData().get_id(),orderId);
        Assert.assertEquals(getOrderDetailsResponse.getData().getOrderById(),_id);
        Assert.assertEquals(getOrderDetailsResponse.getData().getProductOrderedId(),productId);
        Assert.assertEquals(getOrderDetailsResponse.getData().getProductName(),productName);
        Assert.assertEquals(getOrderDetailsResponse.getData().getProductDescription(),productDescription);
        Assert.assertEquals(getOrderDetailsResponse.getData().getProductImage(),productImage);
        Assert.assertEquals(getOrderDetailsResponse.getData().getOrderPrice(),String.valueOf(productPrice));
        Assert.assertEquals(getOrderDetailsResponse.getData().get__v(),__v);
        Assert.assertEquals(getOrderDetailsResponse.getMessage(),"Orders fetched for customer Successfully");

        //userid , country
    }

    @Test(priority = 5)
    public void deleteOrderTest(){

        request = given().log().all().spec(requestSpecification).header("Authorization",token).pathParam("orderId",orderId);
        deleteOrderResponse =  request.when().delete("/api/ecom/order/delete-order/{orderId}").then().log().all().assertThat().statusCode(200).extract().response().as(DeleteOrderResponse.class);

        Assert.assertEquals(deleteOrderResponse.getMessage(),"Orders Deleted Successfully");
    }


    @DataProvider
    public Object[][] getLoginTestData(){

        try {

          List<HashMap<String,Object>> loginTestData = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/e_commerce_apis/LoginTestData.json");
          return new Object[][]{ {loginTestData.get(0)} };

        }catch (IOException i){
            System.out.println("IOException occur");
        }

        return null;
    }


    @DataProvider
    public Object[][] getAddToCartTestData(){

        try {

            List<HashMap<String,Object>> addToCartTestData = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/e_commerce_apis/AddToCartTestData.json");
            Object[][] addToCartTestDataArr = new Object[addToCartTestData.size()][14];
            for(int i = 0; i < addToCartTestData.size(); i++){

                HashMap<String,Object> eachMap = addToCartTestData.get(i);
                addToCartTestDataArr[i][0] = eachMap.get("_id");
                HashMap<String,Object> productMap = (HashMap<String,Object>) eachMap.get("product");
                addToCartTestDataArr[i][1] = productMap.get("_id");
                addToCartTestDataArr[i][2] = productMap.get("productName");
                addToCartTestDataArr[i][3] = productMap.get("productCategory");
                addToCartTestDataArr[i][4] = productMap.get("productSubCategory");
                addToCartTestDataArr[i][5] = productMap.get("productPrice");
                addToCartTestDataArr[i][6] = productMap.get("productDescription");
                addToCartTestDataArr[i][7] = productMap.get("productImage");
                addToCartTestDataArr[i][8] = productMap.get("productRating");
                addToCartTestDataArr[i][9] = productMap.get("productTotalOrders");
                addToCartTestDataArr[i][10] = productMap.get("productStatus");
                addToCartTestDataArr[i][11] = productMap.get("productFor");
                addToCartTestDataArr[i][12] = productMap.get("productAddedBy");
                addToCartTestDataArr[i][13] = productMap.get("__v");

                return addToCartTestDataArr;

            }

        }catch (IOException i){
            System.out.println("IOException occur");
        }

        return null;

    }

    @DataProvider
    public Object[][] getPlaceOrderTestData(){

        try {

            List<HashMap<String, Object>> placeOrderTestData = JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/e_commerce_apis/PlaceOrderData.json");
            Object[][] placeOrderTestDataArr = new Object[placeOrderTestData.size()][2];
            ObjectMapper objectMapper = new ObjectMapper();
            placeOrderTestDataArr[0][0] = JSONUtility.getJsonValueFromPath( objectMapper.writeValueAsString(placeOrderTestData.get(0)),"orders[0].country");
            placeOrderTestDataArr[0][1] = JSONUtility.getJsonValueFromPath( objectMapper.writeValueAsString(placeOrderTestData.get(0)),"orders[0].productOrderedId");
            return placeOrderTestDataArr;

        }catch (IOException i){


        }

        return null;

    }


}
