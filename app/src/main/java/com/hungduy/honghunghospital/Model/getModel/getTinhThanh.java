package com.hungduy.honghunghospital.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getTinhThanh {
    @SerializedName("ma")
    @Expose
    private String ma;
    @SerializedName("ten")
    @Expose
    private String ten;

    public getTinhThanh() {
    }

    public getTinhThanh(String ma, String ten) {
        this.ma = ma;
        this.ten = ten;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
