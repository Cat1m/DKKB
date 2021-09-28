package com.hungduy.honghunghospital.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getKhachHang {
    @SerializedName("ma")
    @Expose
    private String ma;
    @SerializedName("maKH")
    @Expose
    private String maKH;
    @SerializedName("ten")
    @Expose
    private String ten;
    @SerializedName("loai")
    @Expose
    private String loai;
    @SerializedName("masothue")
    @Expose
    private String masothue;

    public getKhachHang() {
    }

    public getKhachHang(String ma, String maKH, String ten, String loai, String masothue) {
        this.ma = ma;
        this.maKH = maKH;
        this.ten = ten;
        this.loai = loai;
        this.masothue = masothue;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
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

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getMasothue() {
        return masothue;
    }

    public void setMasothue(String masothue) {
        this.masothue = masothue;
    }
}
