package com.hungduy.honghunghospital.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getCauHoiKhaiBaoYTe {
    @SerializedName("ma")
    @Expose
    private String ma;

    @SerializedName("cauhoi")
    @Expose
    private String cauhoi;
    @SerializedName("loai")
    @Expose
    private String loai;

    public getCauHoiKhaiBaoYTe(String ma, String cauhoi, String loai) {
        this.ma = ma;
        this.cauhoi = cauhoi;
        this.loai = loai;
    }

    public getCauHoiKhaiBaoYTe() {
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getCauhoi() {
        return cauhoi;
    }

    public void setCauhoi(String cauhoi) {
        this.cauhoi = cauhoi;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }
}
