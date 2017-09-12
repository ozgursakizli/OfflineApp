package com.ozgursakizli.offlineapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ozgursakizli on 10.09.2017.
 */

public class ProductDetailModel implements Parcelable {

    @SerializedName("product_id")
    private String productId;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private String price;
    @SerializedName("image")
    private String image;
    @SerializedName("description")
    private String description;

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.image);
        dest.writeString(this.description);
    }

    public ProductDetailModel() {
    }

    protected ProductDetailModel(Parcel in) {
        this.productId = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.image = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<ProductDetailModel> CREATOR = new Parcelable.Creator<ProductDetailModel>() {
        @Override
        public ProductDetailModel createFromParcel(Parcel source) {
            return new ProductDetailModel(source);
        }

        @Override
        public ProductDetailModel[] newArray(int size) {
            return new ProductDetailModel[size];
        }
    };

}
