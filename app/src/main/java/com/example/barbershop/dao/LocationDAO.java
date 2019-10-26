package com.example.barbershop.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.barbershop.Location;
import com.example.barbershop.Schedule;
import com.example.barbershop.database.Database;

import java.util.ArrayList;
import java.util.List;

import static com.example.barbershop.database.Constants.LOCATION_DETAIL;
import static com.example.barbershop.database.Constants.LOCATION_DISTANCE;
import static com.example.barbershop.database.Constants.LOCATION_NAME;
import static com.example.barbershop.database.Constants.LOCATION_TABLE;


public class LocationDAO {
    private Database database;

    public LocationDAO(Context context) {
        this.database = new Database(context);
    }

    public long insertLocation(Location location) {
        long result = -1;
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_NAME, location.LOCATION_NAME);
        contentValues.put(LOCATION_DETAIL, location.LOCATION_DETAIL);
        contentValues.put(LOCATION_DISTANCE, location.LOCATION_DISTANCE);

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        result = sqLiteDatabase.insert(LOCATION_TABLE, null, contentValues);
        sqLiteDatabase.close();

        return result;
    }


    public List<Location> getAllLocation() {
        List<Location> locationList = new ArrayList<>();
        String sSQL = "SELECT * FROM DiaDiem ";
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sSQL, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                //di chuyen toi vi tri dau tien cua con tro
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String LOCATION_NAME_ = cursor.getString(cursor.getColumnIndex(LOCATION_NAME));
                    String LOCATION_DETAIL_ = cursor.getString(cursor.getColumnIndex(LOCATION_DETAIL));
                    String LOCATION_DISTANCE_ = cursor.getString(cursor.getColumnIndex(LOCATION_DISTANCE));

                    Location location = new Location(LOCATION_NAME_, LOCATION_DETAIL_, LOCATION_DISTANCE_);
                    //add user vao array users;
                    locationList.add(location);
                    //di chuyen toi vi tri tiep theo
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return locationList;
    }

}
