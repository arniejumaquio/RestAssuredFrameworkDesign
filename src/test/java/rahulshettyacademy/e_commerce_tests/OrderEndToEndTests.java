package rahulshettyacademy.e_commerce_tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.OrderAPIResources;
import rahulshettyacademy.pojo_classes.ecommerce_apis.request.LoginRequest;
import rahulshettyacademy.pojo_classes.ecommerce_apis.request.Orders;
import rahulshettyacademy.pojo_classes.ecommerce_apis.request.PlaceOrderRequest;
import rahulshettyacademy.pojo_classes.ecommerce_apis.response.*;
import rahulshettyacademy.utilities.JSONUtility;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderEndToEndTests {

    RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").setContentType(ContentType.JSON).build();
    ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
    private String token;
    private String userId;
    private String email;
    private String productId;
    private String orderId;
    private String country;


    @Test(dataProvider = "getLoginCredentials",priority = 1)
    public void getTokenTest(HashMap<String,String> data){

        LoginRequest loginRequest = new LoginRequest(data.get("email"),data.get("password"));
        RequestSpecification request = given().log().all().spec(requestSpecification).body(loginRequest);
        LoginResponse response =  request.when().post(OrderAPIResources.LOGIN.getResourceUrl()).then().extract().response().as(LoginResponse.class);
        token = response.getToken();
        userId = response.getUserId();
        email = data.get("email");
    }

    @Test(dataProvider = "getCreateProductData",priority = 2)
    public void createProductTest(  HashMap<String,String> createProductData ){

        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        Map<String,Object> formData = new HashMap<String, Object>();
        formData.put("productName",createProductData.get("productName"));
        formData.put("productAddedBy",userId);
        formData.put("productCategory",createProductData.get("productCategory"));
        formData.put("productSubCategory",createProductData.get("productSubCategory"));
        formData.put("productPrice",Long.parseLong(createProductData.get("productPrice")));
        formData.put("productDescription",createProductData.get("productDescription"));
        formData.put("productFor",createProductData.get("productFor"));
        formData.put("Authorization",token);

        File image = new File(System.getProperty("user.dir")+"/src/main/resources/attachments/attachment.png");
        RequestSpecification request = given().log().all().header("Authorization",token).params(formData).multiPart("productImage",image);
        CreateProductResponse response =  request.post(OrderAPIResources.CREATE_PRODUCT.getResourceUrl()).then().assertThat().statusCode(201).extract().as(CreateProductResponse.class);
        productId = response.getProductId();
        Assert.assertEquals(response.getMessage(),"Product Added Successfully");

    }

    @Test(dataProvider = "getPlaceOrderData",priority = 3)
    public void placeOrderTest(HashMap<String,String> placeOrderData){

        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest(Arrays.asList(new Orders(placeOrderData.get("country"),productId)));
        RequestSpecification request = given().log().all().spec(requestSpecification).header("Authorization",token).body(placeOrderRequest);
        PlaceOrderResponse response = request.when().post(OrderAPIResources.PLACE_ORDER.getResourceUrl()).then().log().all().extract().as(PlaceOrderResponse.class);
        orderId =  response.getOrders().get(0);
        country = placeOrderData.get("country");

        Assert.assertEquals(response.getProductOrderId().get(0),productId);
        Assert.assertEquals(response.getMessage(),"Order Placed Successfully");


    }

    @Test(dataProvider = "getCreateProductData",priority = 4)
    public void getOrderDetailsTest(HashMap<String,String> createProductData){

     RestAssured.baseURI = "https://rahulshettyacademy.com/";
     RequestSpecification request = given().log().all().header("Authorization",token).queryParam("id",orderId);
     GetOrderDetailsResponse response = request.when().get(OrderAPIResources.GET_ORDER_DETAILS.getResourceUrl()).then().log().all().extract().response().as(GetOrderDetailsResponse.class);

     Assert.assertEquals(response.getData().get_id(),orderId);
     Assert.assertEquals(response.getData().getOrderById(),userId);
     Assert.assertEquals(response.getData().getOrderBy(),email);
     Assert.assertEquals(response.getData().getProductOrderedId(),productId);
     Assert.assertEquals(response.getData().getProductName(),createProductData.get("productName"));
     Assert.assertEquals(response.getData().getCountry(),country);
     Assert.assertEquals(response.getData().getProductDescription(),createProductData.get("productDescription"));
     Assert.assertEquals(response.getData().getOrderPrice(),createProductData.get("productPrice"));
     Assert.assertEquals(response.getData().get__v(),0);
     Assert.assertEquals(response.getMessage(),"Orders fetched for customer Successfully");

    }

    @Test(priority = 5)
    public void deleteOrderTest(){

       RequestSpecification request = given().log().all().spec(requestSpecification).header("Authorization",token);
       DeleteOrderResponse response = request.when().delete(OrderAPIResources.DELETE_ORDER.getResourceUrl()+orderId).then().extract().response().as(DeleteOrderResponse.class);

       Assert.assertEquals(response.getMessage(),"Orders Deleted Successfully");

    }


    @Test(priority = 6)
    public void getOrderDetailsTest(){

        RestAssured.baseURI = "https://rahulshettyacademy.com/";
       RequestSpecification request = given().log().all().header("Authorization",token).queryParam("id",orderId);
       GetOrderDetailsErrorResponse response = request.when().get(OrderAPIResources.GET_ORDER_DETAILS.getResourceUrl()).then().assertThat().statusCode(400).extract().response().as(GetOrderDetailsErrorResponse.class);

       Assert.assertEquals(response.getMessage(),"Order not found");

    }


    @Test(priority = 7)
    public void deleteProductTest(){


        RequestSpecification request =  given().log().all().spec(requestSpecification).header("Authorization",token);
        DeleteProductErrorResponse response =  request.when().delete(OrderAPIResources.DELETE_PRODUCT.getResourceUrl()+productId).then().extract().as(DeleteProductErrorResponse.class);

        Assert.assertEquals(response.getMessage(),"Product Deleted Successfully");

    }



    @DataProvider
    public Object[][]  getLoginCredentials() throws IOException {


        List<HashMap<String, String>> credentials =JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/LoginTestData.json");

        return new Object[][]{{credentials.get(0)}};

    }


    @DataProvider
    public Object[][] getCreateProductData()  {

        List<HashMap<String,String>> createProductDatas = null;
        try {

            createProductDatas =  JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/CreateProductData.json");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return  new Object[][] {{createProductDatas.get(0)}};
    }


    @DataProvider
    public Object[][] getPlaceOrderData() throws IOException {

     List<HashMap<String,String>> placeOrderDatas =   JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/PlaceOrderData.json");

     return new Object[][] { {placeOrderDatas.get(0)}};

    }


}
