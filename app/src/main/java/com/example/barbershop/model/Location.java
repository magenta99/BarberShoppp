package com.example.barbershop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("idLocation")
    @Expose
    public  String idLocation;
    @SerializedName("addressLocation")
    @Expose
    public  String addressLocation ;
    @SerializedName("districtLocation")
    @Expose
    public  String districtLocation ;
    @SerializedName("districtDetailLocation")
    @Expose
    public  String districtDetailLocation ;
    @SerializedName("cityLocation")
    @Expose
    public  String cityLocation ;

    public String getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(String idLocation) {
        this.idLocation = idLocation;
    }

    public String getAddressLocation() {
        return addressLocation;
    }

    public void setAddressLocation(String addressLocation) {
        this.addressLocation = addressLocation;
    }

    public String getDistrictLocation() {
        return districtLocation;
    }

    public void setDistrictLocation(String districtLocation) {
        this.districtLocation = districtLocation;
    }

    public String getDistrictDetailLocation() {
        return districtDetailLocation;
    }

    public void setDistrictDetailLocation(String districtDetailLocation) {
        this.districtDetailLocation = districtDetailLocation;
    }

    public String getCityLocation() {
        return cityLocation;
    }

    public void setCityLocation(String cityLocation) {
        this.cityLocation = cityLocation;
    }

    public Location(String idLocation, String addressLocation, String districtLocation, String districtDetailLocation, String cityLocation) {
        this.idLocation = idLocation;
        this.addressLocation = addressLocation;
        this.districtLocation = districtLocation;
        this.districtDetailLocation = districtDetailLocation;
        this.cityLocation = cityLocation;
    }
}
