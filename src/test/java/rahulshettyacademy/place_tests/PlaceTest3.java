package rahulshettyacademy.place_tests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.base.BaseTest;
import rahulshettyacademy.pojo_classes.place_apis.response.AddPlaceResponse;
import rahulshettyacademy.pojo_classes.place_apis.response.GetPlaceResponse;
import rahulshettyacademy.utilities.JSONUtility;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import static io.restassured.RestAssured.given;

public class PlaceTest3 extends BaseTest {

    private RequestSpecification request;
    private AddPlaceResponse addPlaceResponse;
    private GetPlaceResponse getPlaceResponse;
    private Response response;
    private HashMap<String,String> placeIds = new HashMap<String, String>();

    @Test(dataProvider = "getAddPlaceTestData",priority = 1)
    public void addPlaceTest(String tcId,String tcDescription,String headers,String payLoad){

         request = given().log().all().spec(requestSpecification).body(payLoad);
         addPlaceResponse =   request.when().post("maps/api/place/add/json").then().log().all().spec(responseSpecification).extract().response().as(AddPlaceResponse.class);
         placeIds.put(tcId,addPlaceResponse.getPlace_id());

        Assert.assertEquals(addPlaceResponse.getStatus(),"OK");
        Assert.assertNotNull(placeIds);
        Assert.assertEquals(addPlaceResponse.getScope(),"APP");
        Assert.assertNotNull(addPlaceResponse.getReference());
        Assert.assertNotNull(addPlaceResponse.getId());

    }

    @Test(dataProvider = "getAddPlaceTestData",priority = 2)
    public void getPlaceTest(String tcId,String tcDescription,String headers,String payLoad){

            String placeId = placeIds.get(tcId);
            request = given().log().all().spec(requestSpecification).queryParam("place_id",placeId);
            getPlaceResponse =  request.when().get("maps/api/place/get/json").then().log().all().spec(responseSpecification).extract().response().as(GetPlaceResponse.class);

            Assert.assertEquals(getPlaceResponse.getLocation().getLatitude(),JSONUtility.getJsonValueStringFromPath(payLoad,"location.lat"));
            Assert.assertEquals(getPlaceResponse.getLocation().getLongitude(),JSONUtility.getJsonValueStringFromPath(payLoad,"location.lng"));
            Assert.assertEquals(getPlaceResponse.getAccuracy(),(int)JSONUtility.getJsonValueFromPath(payLoad,"accuracy"));
            Assert.assertEquals(getPlaceResponse.getName(),JSONUtility.getJsonValueStringFromPath(payLoad,"name"));
            Assert.assertEquals(getPlaceResponse.getPhone_number(),JSONUtility.getJsonValueStringFromPath(payLoad,"phone_number"));
            Assert.assertEquals(getPlaceResponse.getAddress(),JSONUtility.getJsonValueStringFromPath(payLoad,"address"));
            Assert.assertEquals(getPlaceResponse.getTypes(),JSONUtility.getJsonValueStringFromPath(payLoad,"types[0]"));
            Assert.assertEquals(getPlaceResponse.getWebsite(),JSONUtility.getJsonValueStringFromPath(payLoad,"website"));
            Assert.assertEquals(getPlaceResponse.getLanguage(),JSONUtility.getJsonValueStringFromPath(payLoad,"language"));

    }

    @Test(dataProvider = "getAddPlaceTestDataNegative",priority = 3)
    public void addPlaceNegativeTest(String tcId,String tcDescription,String msg,String headers,String payload){

        request = given().log().all().spec(requestSpecification).body(payload);
        response = request.when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(400).extract().response();

        Assert.assertEquals(JSONUtility.getJsonValueStringFromPath(response.asString(),"msg"),msg);

    }



    @DataProvider
    public Object[][] getAddPlaceTestData(){

        try{
         List<HashMap<String,Object>> addPlaceTestDatas =   JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/place_apis/add/AddPlaceFunctional.json");
         Object[][]    addPlaceTestDataArr = new Object[addPlaceTestDatas.size()][4];
         for(int i = 0 ; i < addPlaceTestDatas.size(); i++){
                ObjectMapper objectMapper = new ObjectMapper();
                HashMap<String,Object> addPlaceTestDataMap = addPlaceTestDatas.get(i);
                addPlaceTestDataArr[i][0] = addPlaceTestDataMap.get("tcId");
                addPlaceTestDataArr[i][1] = addPlaceTestDataMap.get("tc_description");
                addPlaceTestDataArr[i][2] = objectMapper.writeValueAsString(addPlaceTestDataMap.get("headers"));
                addPlaceTestDataArr[i][3] = objectMapper.writeValueAsString(addPlaceTestDataMap.get("request"));
         }
        return addPlaceTestDataArr;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


    @DataProvider
    public Object[][] getAddPlaceTestDataNegative(){

        try {
            List<HashMap<String, Object>> addPlaceTestDatas= JSONUtility.getDataFromJsonFile("/src/main/resources/test_data/place_apis/add/AddPlaceNegativeTestData.json");
            Object[][]   addPlaceTestDatasArr = new Object[addPlaceTestDatas.size()][5];
            for(int i = 0; i < addPlaceTestDatas.size(); i++){

              ObjectMapper objectMapper = new ObjectMapper();
              HashMap<String,Object> addPlaceTestDataMap =  addPlaceTestDatas.get(i);
              addPlaceTestDatasArr[i][0] = addPlaceTestDataMap.get("tcId");
              addPlaceTestDatasArr[i][1] = addPlaceTestDataMap.get("tcDescription");
              addPlaceTestDatasArr[i][2] = addPlaceTestDataMap.get("msg");
              addPlaceTestDatasArr[i][3] = objectMapper.writeValueAsString(addPlaceTestDataMap.get("headers"));
              addPlaceTestDatasArr[i][4] = objectMapper.writeValueAsString(addPlaceTestDataMap.get("request"));
            }

            return addPlaceTestDatasArr;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }





}
