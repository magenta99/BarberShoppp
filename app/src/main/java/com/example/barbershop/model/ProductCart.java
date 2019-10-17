package com.example.barbershop.model;

public class ProductCart {
    public String PRODUCT_CART_NAME ;
    public int PRODUCT_CART_NUMBER ;
    public int PRODUCT_CART_PRICE ;
    public String PRODUCT_CART_IMAGE ;

    public ProductCart(String PRODUCT_CART_NAME, int PRODUCT_CART_NUMBER, int PRODUCT_CART_PRICE, String PRODUCT_CART_IMAGE) {
        this.PRODUCT_CART_NAME = PRODUCT_CART_NAME;
        this.PRODUCT_CART_NUMBER = PRODUCT_CART_NUMBER;
        this.PRODUCT_CART_PRICE = PRODUCT_CART_PRICE;
        this.PRODUCT_CART_IMAGE = PRODUCT_CART_IMAGE;
    }
}
