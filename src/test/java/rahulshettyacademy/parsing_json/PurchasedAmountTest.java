package rahulshettyacademy.parsing_json;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.utilities.GetResponseUtility;

public class PurchasedAmountTest {

    @Test
    public void purchasedAmountTest(){

        JsonPath jsonPath = new JsonPath(GetResponseUtility.getResponseBody());
        int total = 0;
        int coursesSize = jsonPath.getInt("courses.size()");
        for(int i = 0 ; i < coursesSize; i++){

          int coursePrice =  jsonPath.getInt("courses["+i+"].price");
          int courseCopies =  jsonPath.getInt("courses["+i+"].copies");

          total += (coursePrice * courseCopies);

        }

        int purchasedAmount = jsonPath.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(total,purchasedAmount);


    }

}
