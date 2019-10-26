package com.example.barbershop.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.barbershop.Schedule;
import com.example.barbershop.database.Database;
import com.example.barbershop.model.ProductCart;

import java.util.ArrayList;
import java.util.List;

import static com.example.barbershop.database.Constants.PRODUCT_CART_IMAGE;
import static com.example.barbershop.database.Constants.PRODUCT_CART_NAME;
import static com.example.barbershop.database.Constants.PRODUCT_CART_NUMBER;
import static com.example.barbershop.database.Constants.PRODUCT_CART_PRICE;
import static com.example.barbershop.database.Constants.PRODUCT_CART_TABLE;
import static com.example.barbershop.database.Constants.SCHEDULE_INFO;
import static com.example.barbershop.database.Constants.SCHEDULE_TABLE;
import static com.example.barbershop.database.Constants.SCHEDULE_TIME;

public class ScheduleDAO {
    private Database database;

    public ScheduleDAO(Context context) {
        this.database = new Database(context);
    }

    public long insertSchedule(Schedule schedule) {
        long result = -1;
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHEDULE_TIME, schedule.SCHEDULE_TIME);
        contentValues.put(SCHEDULE_INFO, schedule.SCHEDULE_INFO);

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        result = sqLiteDatabase.insert(SCHEDULE_TABLE, null, contentValues);
        sqLiteDatabase.close();

        return result;
    }


    public List<Schedule> getAllSchedule() {
        List<Schedule> scheduleList = new ArrayList<>();
        String sSQL = "SELECT * FROM LichCat";
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sSQL, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                //di chuyen toi vi tri dau tien cua con tro
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String SCHEDULE_TIME_ = cursor.getString(cursor.getColumnIndex(SCHEDULE_TIME));
                    String SCHEDULE_INFO_ = cursor.getString(cursor.getColumnIndex(SCHEDULE_INFO));

                    Schedule schedule = new Schedule(SCHEDULE_TIME_,SCHEDULE_INFO_);
                    //add user vao array users;
                    scheduleList.add(schedule);
                    //di chuyen toi vi tri tiep theo
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return scheduleList;
    }

}
