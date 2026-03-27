package rahulshettyacademy.parsing_json;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import rahulshettyacademy.utilities.GetResponseUtility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchasedAmountTest2 {

    @Test
    public void purchasedAmountTest2(){

     String response =   GetResponseUtility.getResponseBody();
     JsonPath jsonPath = new JsonPath(response);
     Map<String,Object> dashboardMap =  jsonPath.getMap("dashboard");
     int purchaseAmount = (int)dashboardMap.get("purchaseAmount");
     String website = (String) dashboardMap.get("website");
     System.out.println(purchaseAmount);
     System.out.println(website);

     List<Map<String,Object>> coursesList =  jsonPath.getList("courses");
     for(int i = 0; i < coursesList.size(); i++){

       Map<String,Object> courseMap =  coursesList.get(i);
       String title = (String)courseMap.get("title");
       int price = (int)courseMap.get("price");
       int copies =  (int)courseMap.get("copies");

       System.out.println(title);
       System.out.println(price);
       System.out.println(copies);

     }

    }

}
