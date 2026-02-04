package rahulshettyacademy.excel_demo;

import org.testng.annotations.Test;
import rahulshettyacademy.utilities.ExcelUtility;
import java.io.IOException;
import java.util.ArrayList;

public class APIExcelTests {

   @Test
    public void apiExcelTests() throws IOException {

       ArrayList<String> testDatas = ExcelUtility.getAllRowDataFromExcel("Sample.xlsx","testdata");
       testDatas.stream().forEach(testData -> System.out.println(testData));
   }


}
