package rahulshettyacademy.ecommerce_end_to_end_demo;

public class OrderDetailsResponse {

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private  Data data;
    private String message;

}
