package rahulshettyacademy.utilities;

import io.restassured.path.json.JsonPath;
import org.apache.commons.io.FileUtils;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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

    public static double getJsonValueDoubleFromPath(String response,String jsonPath){

        JsonPath jsonPathObj = getJsonPathObj(response);
        return jsonPathObj.getDouble(jsonPath);

    }

    public static Object getJsonValueFromPath(String response,String jsonPath){

        JsonPath jsonPathObj = getJsonPathObj(response);
        return jsonPathObj.get(jsonPath);

    }

    public static Map<Object,Object> getJsonMapValueFromPath(String response, String jsonPath){

        JsonPath jsonPathObj = getJsonPathObj(response);
        return jsonPathObj.getMap(jsonPath);
    }

    public static Object convertMapToObject(Map<String,Object> map){

        ObjectMapper objectMapper = new ObjectMapper();
        Object obj = objectMapper.convertValue(map,Object.class);

        return obj;

    }

    public static Map<String, Object> convertToMapAndRemoveField(Object pojo, String fieldToRemove) {
        ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("unchecked")
        Map<String, Object> map = mapper.convertValue(pojo, Map.class);
        map.remove(fieldToRemove);
        return map;
    }


    public static String generateRandomNumber(){
        long randomNumber = ThreadLocalRandom.current().nextLong(100000000L, 1000000000L);
        return String.valueOf(randomNumber);
    }

    public static String convertStringValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "";
        }
        if ("null".equalsIgnoreCase(value.trim())) {
            return null;
        }
        return value;
    }


    public static List<HashMap<String, Object>> getDataFromJsonFile(String filePath) throws IOException {

        //convert json file to string
        String jsonString = FileUtils.readFileToString(new File(System.getProperty("user.dir") + filePath));
        //convert json string to hashmap
        ObjectMapper objectMapper = JsonMapper.builder()
                .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
                .build();
        List<HashMap<String, Object>> listOfData = objectMapper.readValue(jsonString, new TypeReference<List<HashMap<String, Object>>>() {
        });

        return listOfData;

    }




}
