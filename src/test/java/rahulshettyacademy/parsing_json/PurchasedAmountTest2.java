package rahulshettyacademy;

import io.restassured.path.json.JsonPath;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.utilities.GetResponseUtility;

import java.util.HashMap;

public class PurchasedAmountTest2 {

    @Test
    public void purchasedAmountTest2(){

     String response =   GetResponseUtility.getResponseBody();
     JsonPath jsonPath = new JsonPath(response);

      int arraySize = jsonPath.getInt("courses.size()");
      for(int i = 0; i < arraySize; i++){

       String courseTitle = jsonPath.get("courses["+i+"].title");
       int coursePrice =jsonPath.get("courses["+i+"].price");
       int courseCopies =jsonPath.get("courses["+i+"].copies");

       System.out.println(courseTitle);
       System.out.println(coursePrice);
       System.out.println(courseCopies);

       int purchaseAmount = jsonPath.get("dashboard.purchaseAmount");
       String website = jsonPath.get("dashboard.website");

       System.out.println(purchaseAmount);
       System.out.println(website);

      }


    }

}
