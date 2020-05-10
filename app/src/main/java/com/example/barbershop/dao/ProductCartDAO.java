package com.example.barbershop.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.barbershop.database.Database;
import com.example.barbershop.model.ProductCart;

import java.util.ArrayList;
import java.util.List;

import static com.example.barbershop.database.Constants.PRODUCT_CART_IMAGE;
import static com.example.barbershop.database.Constants.PRODUCT_CART_NAME;
import static com.example.barbershop.database.Constants.PRODUCT_CART_NUMBER;
import static com.example.barbershop.database.Constants.PRODUCT_CART_PRICE;
import static com.example.barbershop.database.Constants.PRODUCT_CART_TABLE;

public class ProductCartDAO {
    private Database database;

    public ProductCartDAO(Context context) {
        this.database = new Database(context);
    }

    public long insertProductCart(ProductCart productCart) {
        long result = -1;
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_CART_NAME, productCart.PRODUCT_CART_NAME);
        contentValues.put(PRODUCT_CART_NUMBER, productCart.PRODUCT_CART_NUMBER);
        contentValues.put(PRODUCT_CART_IMAGE, productCart.PRODUCT_CART_IMAGE);
        contentValues.put(PRODUCT_CART_PRICE, productCart.PRODUCT_CART_PRICE);

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        result = sqLiteDatabase.insert(PRODUCT_CART_TABLE, null, contentValues);
        sqLiteDatabase.close();

        return result;
    }

    public long updateProductCartAmount(ProductCart productCart, String nameProduct) {
        long result = -1;
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_CART_NUMBER, productCart.PRODUCT_CART_NUMBER);

        //xin quyen ghi vao bang
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        result = sqLiteDatabase.update(PRODUCT_CART_TABLE, contentValues, PRODUCT_CART_NAME + "=?",
                new String[]{nameProduct});

        return result;
    }

    public long deleteProductCart(String productcart_name) {
        long result = -1;
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        result = sqLiteDatabase.delete(PRODUCT_CART_TABLE, PRODUCT_CART_NAME + "=?",
                new String[]{productcart_name});

        return result;
    }

    public List<ProductCart> getAllProductCart() {
        List<ProductCart> productCartList = new ArrayList<>();
        String sSQL = "SELECT * FROM GioHang ";
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sSQL, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                //di chuyen toi vi tri dau tien cua con tro
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String PRODUCT_CART_NAME_ = cursor.getString(cursor.getColumnIndex(PRODUCT_CART_NAME));
                    int PRODUCT_CART_PRICE_ = cursor.getInt(cursor.getColumnIndex(PRODUCT_CART_PRICE));
                    int PRODUCT_CART_NUMBER_ = cursor.getInt(cursor.getColumnIndex(PRODUCT_CART_NUMBER));
                    String PRODUCT_CART_IMAGE_ = cursor.getString(cursor.getColumnIndex(PRODUCT_CART_IMAGE));

                    ProductCart productCart = new ProductCart( PRODUCT_CART_NAME_, PRODUCT_CART_NUMBER_, PRODUCT_CART_PRICE_, PRODUCT_CART_IMAGE_);
                    //add user vao array users;
                    productCartList.add(productCart);
                    //di chuyen toi vi tri tiep theo
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return productCartList;
    }

    public void deleteCart() {
        String QUERY = "DELETE FROM GioHang ";
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        sqLiteDatabase.execSQL(QUERY);
    }

    public double getTongTien() {
        double tongtien = 0;
        String QUERY = "SELECT SUM(GiaThanh*SoLuong) FROM GioHang ";
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(QUERY, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    tongtien = cursor.getDouble(0);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return tongtien;
    }

    public int getNumberInCart() {
        int soluong = 0;
        String QUERY = "SELECT COUNT(TenSanPham) FROM GioHang";
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(QUERY, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    soluong = cursor.getInt(0);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return soluong;
    }

    public boolean checkProduct(String nameProduct) {
        boolean result = false;
        String query = "select * from GioHang where TenSanPham = '" + nameProduct + "' ";
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.getCount() > 0) {
            result = true;
        }
        c.close();
        return result;
    }

}

