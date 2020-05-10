package com.example.barbershop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {
    @SerializedName("locationHistory")
    @Expose
    public  String locationHistory ;

    @SerializedName("dateHistory")
    @Expose
    public  String dateHistory ;

    @SerializedName("timeHistory")
    @Expose
    public  String timeHistory ;

    @SerializedName("stylistHistory")
    @Expose
    public  String stylistHistory ;

    @SerializedName("serviceHistory")
    @Expose
    public  String serviceHistory ;

    @SerializedName("imageHistory")
    @Expose
    public  String imageHistory ;

    public String getLocationHistory() {
        return locationHistory;
    }

    public void setLocationHistory(String locationHistory) {
        this.locationHistory = locationHistory;
    }

    public String getDateHistory() {
        return dateHistory;
    }

    public void setDateHistory(String dateHistory) {
        this.dateHistory = dateHistory;
    }

    public String getTimeHistory() {
        return timeHistory;
    }

    public void setTimeHistory(String timeHistory) {
        this.timeHistory = timeHistory;
    }

    public String getStylistHistory() {
        return stylistHistory;
    }

    public void setStylistHistory(String stylistHistory) {
        this.stylistHistory = stylistHistory;
    }

    public String getServiceHistory() {
        return serviceHistory;
    }

    public void setServiceHistory(String serviceHistory) {
        this.serviceHistory = serviceHistory;
    }

    public String getImageHistory() {
        return imageHistory;
    }

    public void setImageHistory(String imageHistory) {
        this.imageHistory = imageHistory;
    }

    public History(String locationHistory, String dateHistory, String timeHistory, String stylistHistory, String serviceHistory, String imageHistory) {
        this.locationHistory = locationHistory;
        this.dateHistory = dateHistory;
        this.timeHistory = timeHistory;
        this.stylistHistory = stylistHistory;
        this.serviceHistory = serviceHistory;
        this.imageHistory = imageHistory;
    }
}
