package com.example.barbershop.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Booking {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("exist")
    @Expose
    private boolean exist;

    @SerializedName("date")
    @Expose
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Booking(String id, String time, boolean exist, String date) {
        this.id = id;
        this.time = time;
        this.exist = exist;
        this.date = date;
    }
}
