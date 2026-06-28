package restassuredframeworkdesign.builder.book_apis;

import restassuredframeworkdesign.models.book_apis.request.AddBookRequest;
import restassuredframeworkdesign.models.book_apis.request.DeleteBookRequest;

public class BookRequestBuilder {

    public static AddBookRequest getAddBookRequest(String name, String isbn, String aisle, String author){
        AddBookRequest addBookRequest = new AddBookRequest(name, isbn, aisle, author);

        return addBookRequest;
    }

    public static DeleteBookRequest getDeleteBookRequest(String ID){

        DeleteBookRequest deleteBookRequest = new DeleteBookRequest(ID);
        return deleteBookRequest;
    }

}
