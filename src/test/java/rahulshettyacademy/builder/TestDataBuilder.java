package rahulshettyacademy.builder;

import rahulshettyacademy.pojo_classes.place_apis.request.AddPlaceRequest;
import rahulshettyacademy.pojo_classes.place_apis.request.Location;

import java.util.Arrays;

public class TestDataBuilder {

    public AddPlaceRequest getAddPlacePayload(Double lat, Double lng, String accuracy, String name, String phone_number, String address, String[] types, String website, String language){

        AddPlaceRequest addPlaceRequest = new AddPlaceRequest();
        Location location = new Location();
        location.setLat(lat);
        location.setLng(lng);
        addPlaceRequest.setLocation(location);
        addPlaceRequest.setAccuracy(Integer.parseInt(accuracy));
        addPlaceRequest.setName(name);
        addPlaceRequest.setPhone_number(phone_number);
        addPlaceRequest.setAddress(address);
        addPlaceRequest.setTypes(Arrays.asList(types));
        addPlaceRequest.setWebsite(website);
        addPlaceRequest.setLanguage(language);


        return addPlaceRequest;

    }


    public  String getAddPlacePayload(){

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



    public  String getDeletePlacePayload(String placeId){

        String body = "{\n" +
                "    \"place_id\":\""+placeId+"\"\n" +
                "}\n";


        return body;
    }


}
