package com.example.barbershop.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.barbershop.database.Constants.CREATE_PRODUCT_CART_TABLE;
import static com.example.barbershop.database.Constants.PRODUCT_CART_TABLE;

public class Database extends SQLiteOpenHelper {
    public Database(Context context){
        super(context,"barber1.sql",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRODUCT_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_CART_TABLE);
    }
}
