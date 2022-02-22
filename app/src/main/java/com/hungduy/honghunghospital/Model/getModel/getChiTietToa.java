package com.hungduy.honghunghospital.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getChiTietToa {
    @SerializedName("stt")
    @Expose
    private String stt;
    @SerializedName("ten")
    @Expose
    private String ten;
    @SerializedName("donvi")
    @Expose
    private String donvi;
    @SerializedName("soluong")
    @Expose
    private String soluong;
    @SerializedName("ngayuong")
    @Expose
    private String ngayuong;
    @SerializedName("ghichu")
    @Expose
    private String ghichu;

    public getChiTietToa() {
    }

    public getChiTietToa(String stt, String ten, String donvi, String soluong, String ngayuong, String ghichu) {
        this.stt = stt;
        this.ten = ten;
        this.donvi = donvi;
        this.soluong = soluong;
        this.ngayuong = ngayuong;
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

    public String getDonvi() {
        return donvi;
    }

    public void setDonvi(String donvi) {
        this.donvi = donvi;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getNgayuong() {
        return ngayuong;
    }

    public void setNgayuong(String ngayuong) {
        this.ngayuong = ngayuong;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}
