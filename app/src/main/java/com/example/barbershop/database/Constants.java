package com.example.barbershop.database;

import java.text.DecimalFormat;

public class Constants {
    public static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###");


    //SẢN PHẨM TRONG GIỎ HÀNG
    public static final String PRODUCT_CART_TABLE = "GioHang";

    public static final String PRODUCT_CART_NAME = "TenSanPham";
    public static final String PRODUCT_CART_NUMBER = "SoLuong";
    public static final String PRODUCT_CART_PRICE = "GiaThanh";
    public static final String PRODUCT_CART_IMAGE = "HinhAnh";


    public static final String CREATE_PRODUCT_CART_TABLE = "CREATE TABLE " + PRODUCT_CART_TABLE + "(" +
            "" + PRODUCT_CART_NAME + " NVARCHAR(100)," +
            "" + PRODUCT_CART_PRICE + " INT," +
            "" + PRODUCT_CART_IMAGE + " NVARCHAR(2000)," +
            "" + PRODUCT_CART_NUMBER + " INT" +
            ")";

}
