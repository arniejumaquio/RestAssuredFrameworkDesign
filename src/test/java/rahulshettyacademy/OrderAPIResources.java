package rahulshettyacademy;

public enum OrderAPIResources {

    LOGIN("api/ecom/auth/login"),
    CREATE_PRODUCT("api/ecom/product/add-product"),
    PLACE_ORDER("api/ecom/order/create-order"),
    GET_ORDER_DETAILS("api/ecom/order/get-orders-details"),
    ADD_TO_CART("api/ecom/user/add-to-cart"),
    DELETE_PRODUCT("api/ecom/product/delete-product/"),
    DELETE_ORDER("api/ecom/order/delete-order/");

    public String resourceUrl;


    OrderAPIResources(String resourceUrl){
        this.resourceUrl = resourceUrl;
    }

    public String getResourceUrl(){
        return this.resourceUrl;
    }



}
