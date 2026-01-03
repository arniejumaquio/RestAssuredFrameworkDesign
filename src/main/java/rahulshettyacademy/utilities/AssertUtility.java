package rahulshettyacademy.utilities;

import org.testng.Assert;

public class AssertUtility {


    public static void verifyCourseTitleAndPrice(String response,String jsonPathOfArray,String expectedCourseTitle,String expectedPrice){

        int arraySize = JSONUtility.getJsonValueIntFromPath(response,jsonPathOfArray+".size()");

        for(int i = 0; i < arraySize; i++){

            String courseTitle = JSONUtility.getJsonValueStringFromPath(response,jsonPathOfArray+"["+i+"].courseTitle");
            String price = JSONUtility.getJsonValueStringFromPath(response,jsonPathOfArray+"["+i+"].price");


            if(courseTitle.equalsIgnoreCase(expectedCourseTitle)){

                if(price.equalsIgnoreCase(expectedPrice)){
                    Assert.assertTrue(true);
                }else {
                    System.out.println("Invalid  course price");
                    Assert.assertTrue(false);
                }

            }else{
                System.out.println("Invalid course title");
                Assert.assertTrue(false);
            }

        }

    }

}
