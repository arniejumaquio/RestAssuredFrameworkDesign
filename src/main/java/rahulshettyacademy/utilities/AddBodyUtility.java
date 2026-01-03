package rahulshettyacademy.utilities;

public class AddBodyUtility {

    public static String getAddPlaceBody(){

        String body = "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -38.383494,\n" +
                "    \"lng\": 33.427362\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \"Frontline house\",\n" +
                "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                "  \"address\": \"29, side layout, cohen 09\",\n" +
                "  \"types\": [\n" +
                "    \"shoe park\",\n" +
                "    \"shop\"\n" +
                "  ],\n" +
                "  \"website\": \"http://google.com\",\n" +
                "  \"language\": \"French-IN\"\n" +
                "}\n" +
                "\n";


        return body;
    }



    public static String getUpdatePlaceBody(String placeId,String address){

        String body = "{\n" +
                "\"place_id\":\""+placeId+"\",\n" +
                "\"address\":\""+address+"\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}\n" +
                "\n";


        return body;
    }


    public static String getDeletePlaceBody(String placeId){

        String body = "{\n" +
                "    \"place_id\":\""+placeId+"\"\n" +
                "}\n";


        return body;
    }


    public static String getAddBookBody(){

        String body = "{\n" +
                "\n" +
                "\"name\":\"Learn Appium Automation with Java\",\n" +
                "\"isbn\":\"bcd\",\n" +
                "\"aisle\":\"227\",\n" +
                "\"author\":\"John foe\"\n" +
                "}\n" +
                " \n";


        return body;

    }


    public static String getAddBookBody(String name, String isbn, String aisle, String author){

        String body = "{\n" +
                "\"name\":\""+name+"\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\""+author+"\"\n" +
                "}\n";

        return body;
    }

    public static String getAddBookBodyMissingField(String fieldToSkip, String name, String isbn, String aisle, String author){
        StringBuilder body = new StringBuilder("{\n");
        
        if (!fieldToSkip.equals("name")) {
            body.append("\"name\":\"").append(name).append("\",\n");
        }
        if (!fieldToSkip.equals("isbn")) {
            body.append("\"isbn\":\"").append(isbn).append("\",\n");
        }
        if (!fieldToSkip.equals("aisle")) {
            body.append("\"aisle\":\"").append(aisle).append("\",\n");
        }
        if (!fieldToSkip.equals("author")) {
            body.append("\"author\":\"").append(author).append("\"\n");
        }
        
        String result = body.toString();
        if (result.endsWith(",\n")) {
            result = result.substring(0, result.length() - 2) + "\n";
        }
        result += "}\n";
        
        return result;
    }

    public static String getDeleteBookBody(String ID){


        String body = "{\n" +
                " \n" +
                "\"ID\" : \""+ID+"\"\n" +
                " \n" +
                "} \n";

        return body;

    }

    public static String getCreateBugBody(String key,String summary,String description){

        String body = "{\n" +
                "    \"fields\": {\n" +
                "       \"project\":\n" +
                "       {\n" +
                "          \"key\": \""+key+"\"\n" +
                "       },\n" +
                "       \"summary\": \""+summary+"\",\n" +
                "       \"description\": \""+description+"\",\n" +
                "       \"issuetype\": {\n" +
                "          \"name\": \"Bug\"\n" +
                "       }\n" +
                "   }\n" +
                "}\n";

        return body;

    }


}



