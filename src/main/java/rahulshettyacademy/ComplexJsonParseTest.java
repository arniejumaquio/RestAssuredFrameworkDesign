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


        System.out.println("--------");
        //print title,copies,sold of last courses in the array
        for(int i = 0; i < coursesCount; i++){

            if(i == coursesCount-1){

                //last index
               String title = jsonPath.getString("courses["+i+"].title");
               int price = jsonPath.getInt("courses["+i+"].price");
               int copies = jsonPath.getInt("courses["+i+"].copies");

                System.out.println("Title = "+title);
                System.out.println("Price = "+price);
                System.out.println("Copies = "+copies);


            }

        }


        System.out.println("--------");
        //print title,copies,sold of first courses in the array
        for(int i = 0; i < coursesCount; i++){

           if(i == 0){

               System.out.println(jsonPath.getString("courses["+i+"].title"));
               System.out.println(jsonPath.getInt("courses["+i+"].price"));
               System.out.println(jsonPath.getInt("courses["+i+"].copies"));

           }

        }



        //print copies sold by RPA
        System.out.println("--------");
        for(int i = 0; i < coursesCount; i++){

            if( jsonPath.getString("courses["+i+"].title").equalsIgnoreCase("RPA") ){
                Assert.assertTrue(jsonPath.getInt("courses["+i+"].copies") == 10);
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
