package rahulshettyacademy.pojo_classes.ecommerce_apis.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    @JsonProperty("_id")
    private String _id;
    private String productName;
    private String productCategory;
    private String productSubCategory;
    private int productPrice;
    private String productDescription;
    private String productImage;
    private String productRating;
    private String productTotalOrders;
    private boolean productStatus;
    private String productFor;
    private String productAddedBy;
    private int __v;

    Product(){

    }

    public Product(String _id, String productName, String productCategory, String productSubCategory, int productPrice, String productDescription, String productImage, String productRating, String productTotalOrders, boolean productStatus, String productFor, String productAddedBy, int __v) {
        this._id = _id;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productSubCategory = productSubCategory;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.productRating = productRating;
        this.productTotalOrders = productTotalOrders;
        this.productStatus = productStatus;
        this.productFor = productFor;
        this.productAddedBy = productAddedBy;
        this.__v = __v;
    }


    public String get_id() {
        return _id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getProductSubCategory() {
        return productSubCategory;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductRating() {
        return productRating;
    }

    public String getProductTotalOrders() {
        return productTotalOrders;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public String getProductFor() {
        return productFor;
    }

    public String getProductAddedBy() {
        return productAddedBy;
    }

    public int get__v() {
        return __v;
    }


    public void set_id(String _id) {
        this._id = _id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public void setProductSubCategory(String productSubCategory) {
        this.productSubCategory = productSubCategory;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setProductRating(String productRating) {
        this.productRating = productRating;
    }

    public void setProductTotalOrders(String productTotalOrders) {
        this.productTotalOrders = productTotalOrders;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    public void setProductFor(String productFor) {
        this.productFor = productFor;
    }

    public void setProductAddedBy(String productAddedBy) {
        this.productAddedBy = productAddedBy;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }


}
