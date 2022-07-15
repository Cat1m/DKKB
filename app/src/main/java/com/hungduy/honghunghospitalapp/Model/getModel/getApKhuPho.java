package com.hungduy.honghunghospitalapp.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getApKhuPho extends getMaTen{

    @SerializedName("maphuongxa")
    @Expose
    private String maphuongxa;

    public getApKhuPho() {
    }

    public getApKhuPho(String ma, String ten, String maphuongxa) {
        super(ma, ten);
        this.maphuongxa = maphuongxa;
    }

    public getApKhuPho(String maphuongxa) {
        this.maphuongxa = maphuongxa;
    }


    public String getMatinhthanh() {
        return maphuongxa;
    }

    public void setMatinhthanh(String maphuongxa) {
        this.maphuongxa = maphuongxa;
    }
}
