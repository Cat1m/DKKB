package com.hungduy.honghunghospital.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getNhomGiaoDich {
    @SerializedName("ma")
    @Expose
    private String ma;
    @SerializedName("nhom")
    @Expose
    private String nhom;
    @SerializedName("ten")
    @Expose
    private String ten;

    public getNhomGiaoDich() {
    }

    public getNhomGiaoDich(String ma, String nhom, String ten) {
        this.ma = ma;
        this.nhom = nhom;
        this.ten = ten;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getNhom() {
        return nhom;
    }

    public void setNhom(String nhom) {
        this.nhom = nhom;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
