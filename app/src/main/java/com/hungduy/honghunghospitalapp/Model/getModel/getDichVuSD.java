package com.hungduy.honghunghospitalapp.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getDichVuSD {
    @SerializedName("stt")
    @Expose
    private String stt;
    @SerializedName("ten")
    @Expose
    private String ten;
    @SerializedName("soluong")
    @Expose
    private String soluong;
    @SerializedName("dongia")
    @Expose
    private String dongia;
    @SerializedName("thanhtien")
    @Expose
    private String thanhtien;
    @SerializedName("ghichu")
    @Expose
    private String ghichu;

    public getDichVuSD() {
    }

    public getDichVuSD(String stt, String ten, String soluong, String dongia, String thanhtien, String ghichu) {
        this.stt = stt;
        this.ten = ten;
        this.soluong = soluong;
        this.dongia = dongia;
        this.thanhtien = thanhtien;
        this.ghichu = ghichu;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getDongia() {
        return dongia;
    }

    public void setDongia(String dongia) {
        this.dongia = dongia;
    }

    public String getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(String thanhtien) {
        this.thanhtien = thanhtien;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}
