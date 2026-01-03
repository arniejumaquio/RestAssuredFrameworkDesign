package rahulshettyacademy.utilities;

import io.restassured.path.json.JsonPath;
import org.apache.commons.io.FileUtils;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class JSONUtility {

    private static JsonPath getJsonPathObj(String response){

        JsonPath jsonPath = new JsonPath(response);
        return jsonPath;

    }

    public static String getJsonValueStringFromPath(String response,String jsonPath){

       JsonPath jsonPathObj = getJsonPathObj(response);
       return jsonPathObj.getString(jsonPath);

    }


    public static int getJsonValueIntFromPath(String response,String jsonPath){

        JsonPath jsonPathObj = getJsonPathObj(response);
        return jsonPathObj.getInt(jsonPath);

    }


    public static List<HashMap<String, String>> getDataFromJsonFile(String filePath) throws IOException {

        //convert json file to string
        String data = FileUtils.readFileToString(new File(System.getProperty("user.dir") + filePath));
        //convert json string to hashmap
        ObjectMapper objectMapper = new ObjectMapper();
        List<HashMap<String, String>> listOfData = objectMapper.readValue(data, new TypeReference<List<HashMap<String, String>>>() {
        });

        return listOfData;

    }

}
