package rahulshettyacademy.graphql_demo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.pojo_classes.place_apis.response.graphql.GraphQLQueryResponse;
import rahulshettyacademy.utilities.AddBodyUtility;
import rahulshettyacademy.utilities.JSONUtility;

import static io.restassured.RestAssured.given;

public class GraphQLTest {

    RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/gq/graphql").setContentType("application/json").build();
    ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType("application/json").build();


   @Test
    public void graphQLQueryTest(){

       RequestSpecification request = given().log().all().spec(requestSpecification).body(AddBodyUtility.getGraphQLQueryBody("19668","rahul"));
       GraphQLQueryResponse graphQLQueryResponse =request.when().post().then().spec(responseSpecification).extract().as(GraphQLQueryResponse.class);
       Assert.assertEquals(graphQLQueryResponse.getData().getCharacter().getName(),"test");

   }


    @Test
    public void graphQLMutationTest(){

       int[] locationIds = {17846,17847};

       RequestSpecification request = given().log().all().spec(requestSpecification).body(AddBodyUtility.getGraphQLMutationBody("Testing Episode","1990-12-12","69",
               "Just gaming laptop my location sir","Riverside","Parisukat","test","typer",
               "test","animal","unisex",".jpeg",71,71,
               locationIds));

       String graphQLMutationResponse = request.when().post().then().spec(responseSpecification).extract().response().asString();

        int actualCreateEpisodeId = JSONUtility.getJsonValueIntFromPath(graphQLMutationResponse,"data.createEpisode.id");
        int actualCreateLocationId = JSONUtility.getJsonValueIntFromPath(graphQLMutationResponse,"data.createLocation.id");
        int actualCreateChracterId = JSONUtility.getJsonValueIntFromPath(graphQLMutationResponse,"data.createCharacter.id");
        int actualDeleteLocationLocationsDeleted =JSONUtility.getJsonValueIntFromPath(graphQLMutationResponse,"data.deleteLocations.locationsDeleted");

        Assert.assertNotNull(actualCreateEpisodeId);
        Assert.assertNotNull(actualCreateLocationId);
        Assert.assertNotNull(actualCreateChracterId);
        Assert.assertNotNull(actualDeleteLocationLocationsDeleted);


    }


}
