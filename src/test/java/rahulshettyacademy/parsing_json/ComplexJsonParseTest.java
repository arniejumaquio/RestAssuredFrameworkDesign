package rahulshettyacademy.parsing_json;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import rahulshettyacademy.utilities.GetResponseUtility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplexJsonParseTest {

    public static void main(String[] args) {

        JsonPath jsonPath = new JsonPath(GetResponseUtility.getResponseBody());

        Map<String,Object> dashboardMap = jsonPath.getMap("dashboard");
        System.out.println(dashboardMap.get("purchaseAmount"));
        System.out.println(dashboardMap.get("website"));

        List<Map<String,Object>> coursesList = jsonPath.getList("courses");
        for(int i = 0; i < coursesList.size(); i++){
           Map<String,Object> coursesMap =  coursesList.get(i);
           System.out.println(coursesMap.get("title"));
           System.out.println(coursesMap.get("price"));
           System.out.println(coursesMap.get("copies"));
        }


    }

}
