package rahulshettyacademy.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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


    public static String getAddPlaceBody(String lat,String lng,String accuracy,String name,String phoneNumber,String address,String website,String language){

        String body = "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": "+lat+",\n" +
                "    \"lng\": "+lng+"\n" +
                "  },\n" +
                "  \"accuracy\": "+accuracy+",\n" +
                "  \"name\": \""+name+"\",\n" +
                "  \"phone_number\": \""+phoneNumber+"\",\n" +
                "  \"address\": \""+address+"\",\n" +
                "  \"types\": [\n" +
                "    \"shoe park\",\n" +
                "    \"shop\"\n" +
                "  ],\n" +
                "  \"website\": \""+website+"\",\n" +
                "  \"language\": \""+language+"\"\n" +
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


    public static  HashMap<String,Object> getAddBookBody(ArrayList<String> data){

        HashMap<String,Object> body = new HashMap<String,Object>();
        body.put("name",data.get(1));
        body.put("isbn",data.get(2));
        body.put("aisle",data.get(3));
        body.put("author",data.get(4));

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


    //GraphQL
    public static String getGraphQLQueryBody(String characterId,String episode){

        //19668
        String body = "{\"query\":\"query{\\n  \\n character(characterId:"+characterId+")\\n  {\\n    name\\n    status\\n    location\\n    {\\n      dimension\\n      type\\n    }\\n    \\n    episodes\\n    {\\n      air_date\\n    }\\n  }\\n  \\n  episodes(filters:{episode:\\\""+episode+"\\\"})\\n  {\\n    result\\n    {\\n      name\\n      air_date\\n      episode\\n      created\\n    }\\n   \\n  }\\n  \\n\\n  \\n  \\n \\n  \\n}\",\"variables\":null}";
        return body;
    }

    public static String getGraphQLMutationBody(String episodeName,String episodeAirDate,String episodeNumber,
                                                String locationName,String locationType,String locationDimension,String characterName,String characterType,
                                                String characterStatus,String characterSpecies,String characterGender,String characterImage,int originId,int locationId,
                                                int[] locationIds){

        //19668
        String body = "{\"query\":\"mutation{\\n  \\n  createEpisode(episode:{name:\\\""+episodeName+"\\\",air_date:\\\""+episodeAirDate+"\\\",episode:\\\""+episodeNumber+"\\\"})\\n  {\\n    id\\n  }\\n  \\n  createLocation(location:{name:\\\""+locationName+"\\\",type:\\\""+locationType+"\\\",dimension:\\\""+locationDimension+"\\\"})\\n  {\\n    id\\n  }\\n  \\n  createCharacter(character:{name:\\\""+characterName+"\\\",type:\\\""+characterType+"\\\",status:\\\""+characterStatus+"\\\",species:\\\""+characterSpecies+"\\\",gender:\\\""+characterGender+"\\\",image:\\\""+characterImage+"\\\",originId:"+originId+",locationId:"+locationId+"})\\n  {\\n    id\\n  }\\n  \\n  deleteLocations(locationIds:"+ Arrays.toString(locationIds)+")\\n  {\\n    locationsDeleted\\n  }\\n  \\n}\",\"variables\":null}";
        return body;
    }


}



