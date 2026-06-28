package restassuredframeworkdesign.models.ecommerce_apis.response;

import restassuredframeworkdesign.models.ecommerce_apis.request.Data;

public class GetOrderDetailsResponse {

    private Data data;
    private String message;

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



}
