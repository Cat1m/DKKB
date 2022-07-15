package com.hungduy.honghunghospitalapp.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getNgayLamViecDKK {
    @SerializedName("ngay")
    @Expose
    private String ngay;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("s")
    @Expose
    private String s;
    @SerializedName("c")
    @Expose
    private String c;

    public getNgayLamViecDKK() {
    }

    public getNgayLamViecDKK(String ngay, String date, String s, String c) {
        this.ngay = ngay;
        this.date = date;
        this.s = s;
        this.c = c;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
