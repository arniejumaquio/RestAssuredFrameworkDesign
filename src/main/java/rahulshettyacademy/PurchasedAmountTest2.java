package rahulshettyacademy;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.utilities.GetResponseUtility;

public class PurchasedAmountTest2 {

    @Test
    public void purchasedAmountTest2(){

     String response =   GetResponseUtility.getResponseBody();
     JsonPath jsonPath = new JsonPath(response);
     String purchaseAmountValue = jsonPath.getString("dashboard.purchaseAmount");
     String websiteValue = jsonPath.getString("dashboard.website");

     System.out.println(purchaseAmountValue);
     System.out.println(websiteValue);

     String firstCourseTitle = jsonPath.getString("courses[0].title");
     String firstCoursePrice = jsonPath.getString("courses[0].price");
     String firstCourseCopies = jsonPath.getString("courses[0].copies");

        System.out.println(firstCourseTitle);
        System.out.println(firstCoursePrice);
        System.out.println(firstCourseCopies);


        String secondCourseTitle = jsonPath.getString("courses[1].title");
        String secondCoursePrice = jsonPath.getString("courses[1].price");
        String secondCourseCopies = jsonPath.getString("courses[1].copies");

        System.out.println(secondCourseTitle);
        System.out.println(secondCoursePrice);
        System.out.println(secondCourseCopies);


        String thirdCourseTitle = jsonPath.getString("courses[2].title");
        String thirdCoursePrice = jsonPath.getString("courses[2].price");
        String thirdCourseCopies = jsonPath.getString("courses[2].copies");

        System.out.println(thirdCourseTitle);
        System.out.println(thirdCoursePrice);
        System.out.println(thirdCourseCopies);

        System.out.println("--------");
        System.out.println( jsonPath.getString("courses[0]") );
        System.out.println( jsonPath.getString("courses[1]") );
        System.out.println( jsonPath.getString("courses[2]") );

    }

}
