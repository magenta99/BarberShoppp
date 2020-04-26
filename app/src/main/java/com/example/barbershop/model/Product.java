
package com.example.barbershop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("imageProduct")
    @Expose
    private String imageProduct;
    @SerializedName("nameProduct")
    @Expose
    private String nameProduct;
    @SerializedName("priceProduct")
    @Expose
    private String priceProduct;
    @SerializedName("typeProduct")
    @Expose
    private String typeProduct;
    @SerializedName("descriptionProduct")
    @Expose
    private String descriptionProduct;
    @SerializedName("ratingProduct")
    @Expose
    private String ratingProduct;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(String priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(String typeProduct) {
        this.typeProduct = typeProduct;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public String getRatingProduct() {
        return ratingProduct;
    }

    public void setRatingProduct(String ratingProduct) {
        this.ratingProduct = ratingProduct;
    }

    public Product(String id, String imageProduct, String nameProduct, String priceProduct, String typeProduct, String descriptionProduct, String ratingProduct) {
        this.id = id;
        this.imageProduct = imageProduct;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.typeProduct = typeProduct;
        this.descriptionProduct = descriptionProduct;
        this.ratingProduct = ratingProduct;
    }
}
