package com.example.barbershop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service {
    @SerializedName("idService")
    @Expose
    public  String idService;
    @SerializedName("nameService")
    @Expose
    public  String nameService ;
    @SerializedName("detailService")
    @Expose
    public  String detailService ;
    @SerializedName("priceService")
    @Expose
    public  String priceService ;

    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public String getDetailService() {
        return detailService;
    }

    public void setDetailService(String detailService) {
        this.detailService = detailService;
    }

    public String getPriceService() {
        return priceService;
    }

    public void setPriceService(String priceService) {
        this.priceService = priceService;
    }

    public Service(String idService, String nameService, String detailService, String priceService) {
        this.idService = idService;
        this.nameService = nameService;
        this.detailService = detailService;
        this.priceService = priceService;
    }
}
