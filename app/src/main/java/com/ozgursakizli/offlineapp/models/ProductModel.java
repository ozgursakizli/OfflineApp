package com.ozgursakizli.offlineapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ozgursakizli on 10.09.2017.
 */

public class ProductModel {

    @SerializedName("product_id")
    private String productId;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private String price;
    @SerializedName("image")
    private String image;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
