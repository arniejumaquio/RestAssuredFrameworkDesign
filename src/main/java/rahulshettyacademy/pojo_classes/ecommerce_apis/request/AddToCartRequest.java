package rahulshettyacademy.pojo_classes.ecommerce_apis.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddToCartRequest {

    @JsonProperty("_id")
    private String _id;
    private Product product;

    public AddToCartRequest(){

    }

    public AddToCartRequest(String _id,Product product){
        this._id = _id;
        this.product = product;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


}
