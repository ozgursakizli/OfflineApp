package com.ozgursakizli.offlineapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ozgursakizli on 10.09.2017.
 */

public class ProductListModel {

    @SerializedName("products")
    private List<ProductModel> productList = new ArrayList<>();

    public List<ProductModel> getProductList() {
        return productList;
    }

}
