package rahulshettyacademy.models.book_apis.request;

public class DeleteBookRequest {

    private String ID;

    public DeleteBookRequest(){

    }

    public DeleteBookRequest(String ID){
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }



}
