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

    //SẢN PHẨM TRONG GIỎ HÀNG
    public static final String LOCATION_TABLE = "DiaDiem";

    public static final String LOCATION_NAME = "TenDiaDiem";
    public static final String LOCATION_DETAIL = "ChiTiet";
    public static final String LOCATION_DISTANCE = "KhoangCach";

    public static final String CREATE_LOCATION_TABLE = "CREATE TABLE " + LOCATION_TABLE + "(" +
            "" + LOCATION_NAME + " NVARCHAR(100) PRIMARY KEY," +
            "" + LOCATION_DETAIL + " NVARCHAR(100)," +
            "" + LOCATION_DISTANCE + " NVARCHAR(100)" +
            ")";



    public static final String SCHEDULE_TABLE = "LichCat";

    public static final String SCHEDULE_TIME = "ThoiGian";
    public static final String SCHEDULE_INFO = "ThongTin";

    public static final String CREATE_SCHEDULE_TABLE = "CREATE TABLE " + SCHEDULE_TABLE + "(" +
            "" + SCHEDULE_TIME + " NVARCHAR(100) PRIMARY KEY," +
            "" + SCHEDULE_INFO + " NVARCHAR(100)" +
            ")";


}
