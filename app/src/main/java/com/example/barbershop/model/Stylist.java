package com.example.barbershop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stylist {
    @SerializedName("idStylist")
    @Expose
    public  String idStylist;
    @SerializedName("nameStylist")
    @Expose
    public  String nameStylist ;
    @SerializedName("ratingStylist")
    @Expose
    public  String ratingStylist ;


    public String getIdStylist() {
        return idStylist;
    }

    public void setIdStylist(String idStylist) {
        this.idStylist = idStylist;
    }

    public String getNameStylist() {
        return nameStylist;
    }

    public void setNameStylist(String nameStylist) {
        this.nameStylist = nameStylist;
    }

    public String getRatingStylist() {
        return ratingStylist;
    }

    public void setRatingStylist(String ratingStylist) {
        this.ratingStylist = ratingStylist;
    }

    public Stylist(String idStylist, String nameStylist, String ratingStylist) {
        this.idStylist = idStylist;
        this.nameStylist = nameStylist;
        this.ratingStylist = ratingStylist;
    }
}
