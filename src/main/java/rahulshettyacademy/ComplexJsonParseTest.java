package rahulshettyacademy;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import rahulshettyacademy.utilities.GetResponseUtility;

public class ComplexJsonParseTest {

    public static void main(String[] args) {

        JsonPath jsonPath = new JsonPath(GetResponseUtility.getResponseBody());
        int coursesCount = jsonPath.getInt("courses.size()");
        int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
        String firstCourseTitle =jsonPath.getString("courses[0].title");

        System.out.println("coursesCount = "+coursesCount);
        System.out.println("purchaseAmount = "+purchaseAmount);
        System.out.println("firstCourseTitle = "+firstCourseTitle);

        for(int i = 0; i < coursesCount; i++){

            String courseTitle = jsonPath.getString("courses["+i+"].title");
            int coursePrice = jsonPath.getInt("courses["+i+"].price");
            int courseCopies = jsonPath.getInt("courses["+i+"].copies");

            System.out.println(courseTitle);
            System.out.println(coursePrice);
            System.out.println(courseCopies);

        }


        //print copies sold by RPA
        for(int i =0; i < 3; i++){

           String courseTitle = jsonPath.getString("courses["+i+"].title");
           if(courseTitle.equalsIgnoreCase("Cypress")){
               int copies =jsonPath.getInt("courses["+i+"].copies");
               System.out.println("RPA copies "+copies);
               break;
           }

        }

        //compare purchase amount to price * copies
        int sum = 0;
        int count = jsonPath.getInt("courses.size()");
        for(int j = 0; j < count; j++){

           int price =  jsonPath.getInt("courses["+j+"].price");
           int copies =  jsonPath.getInt("courses["+j+"].copies");

           sum += (price * copies);

        }

        Assert.assertEquals(purchaseAmount,sum);


        for(int i = 0; i < coursesCount; i++){

           String courseTitle = jsonPath.getString("courses["+i+"].title");
           int coursePrice = jsonPath.getInt("courses["+i+"].price");

            System.out.println(courseTitle);
            System.out.println(coursePrice);

        }


        for(int i = 0; i < coursesCount; i++){

            String courseTitle = jsonPath.getString("courses["+i+"].title");
            if(courseTitle.equalsIgnoreCase("RPA")){
               int copiesRPA =  jsonPath.getInt("courses["+i+"].copies");
                System.out.println("copiesRPA = "+copiesRPA);
            }
        }


        int total = 0;
        for(int i = 0; i < coursesCount; i++){


            int coursePrice = jsonPath.getInt("courses["+i+"].price");
            int courseCopies = jsonPath.getInt("courses["+i+"].copies");

            total = total + ( coursePrice * courseCopies);

        }


        Assert.assertEquals(total,purchaseAmount);


    }

}
