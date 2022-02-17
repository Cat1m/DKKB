package com.hungduy.honghunghospital.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getGiaDV extends getMaTen{
    @SerializedName("gia")
    @Expose
    private String gia;


    public getGiaDV() {
    }

    public getGiaDV(String ma, String ten, String gia) {
        super(ma, ten);
        this.gia = gia;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }
}
