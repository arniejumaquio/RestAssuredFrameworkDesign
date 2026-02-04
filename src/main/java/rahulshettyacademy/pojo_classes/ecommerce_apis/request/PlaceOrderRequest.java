package rahulshettyacademy.pojo_classes.ecommerce_apis.request;

import java.util.List;

public class PlaceOrderRequest {

    private List<Orders> orders;

    public PlaceOrderRequest(){

    }

    public PlaceOrderRequest(List<Orders> orders){
        this.orders = orders;
    }


    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }


}
