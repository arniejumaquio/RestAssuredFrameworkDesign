package rahulshettyacademy.clients.book_apis;

import io.restassured.specification.RequestSpecification;
import rahulshettyacademy.models.book_apis.request.AddBookRequest;
import rahulshettyacademy.models.book_apis.request.DeleteBookRequest;
import rahulshettyacademy.models.book_apis.response.AddBookResponse;
import rahulshettyacademy.models.book_apis.response.DeleteBookResponse;
import rahulshettyacademy.models.book_apis.response.GetBookByIdResponse;


import static io.restassured.RestAssured.given;

public class BookClient {

    private  RequestSpecification requestSpecification;
    // RequestSpec is injected — same as injecting WebDriver into a Page Object

    public BookClient(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public AddBookResponse addBook(AddBookRequest addBookRequest ){
        RequestSpecification request = given().log().all().spec(requestSpecification).body(addBookRequest);
        AddBookResponse addBookResponse = request.when().post("/Library/Addbook.php").then().log().all().extract().response().as(AddBookResponse.class);

        return addBookResponse;
    }

    public DeleteBookResponse deleteBook(DeleteBookRequest deleteBookRequest ){
        RequestSpecification request = given().log().all().spec(requestSpecification).body(deleteBookRequest);
        DeleteBookResponse deleteBookResponse = request.when().post("/Library/DeleteBook.php").then().log().all().extract().response().as(DeleteBookResponse.class);

        return deleteBookResponse;
    }

    public GetBookByIdResponse getBookById(String ID){

        RequestSpecification request  = given().log().all().spec(requestSpecification).queryParam("ID",ID);
        GetBookByIdResponse getBookByIdResponse= request.when().get("/Library/GetBook.php").then().log().all().extract().response().as(GetBookByIdResponse.class);

        return getBookByIdResponse;

    }

}
