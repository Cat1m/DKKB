package com.hungduy.honghunghospital.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getNgayLamViecDKK {
    @SerializedName("ngay")
    @Expose
    private String ngay;

    public getNgayLamViecDKK() {
    }

    public getNgayLamViecDKK(String ngay) {
        this.ngay = ngay;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
