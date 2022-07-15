package com.hungduy.honghunghospitalapp.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getLichSuKham {
    @SerializedName("stt_pcs")
    @Expose
    private String sttPcs;
    @SerializedName("mabn")
    @Expose
    private String mabn;
    @SerializedName("ten")
    @Expose
    private String ten;
    @SerializedName("tenpk")
    @Expose
    private String tenpk;
    @SerializedName("ngaysinh")
    @Expose
    private String ngaysinh;
    @SerializedName("ngaykham")
    @Expose
    private String ngaykham;
    @SerializedName("chuandoan")
    @Expose
    private String chuandoan;
    @SerializedName("cotoathuoc")
    @Expose
    private String cotoathuoc;
    @SerializedName("codv")
    @Expose
    private String codv;

    public getLichSuKham() {
    }

    public getLichSuKham(String sttPcs, String mabn, String ten, String tenpk, String ngaysinh, String ngaykham, String chuandoan, String cotoathuoc, String codv) {
        this.sttPcs = sttPcs;
        this.mabn = mabn;
        this.ten = ten;
        this.tenpk = tenpk;
        this.ngaysinh = ngaysinh;
        this.ngaykham = ngaykham;
        this.chuandoan = chuandoan;
        this.cotoathuoc = cotoathuoc;
        this.codv = codv;
    }

    public String getCodv() {
        return codv;
    }

    public void setCodv(String codv) {
        this.codv = codv;
    }

    public String getCotoathuoc() {
        return cotoathuoc;
    }

    public void setCotoathuoc(String cotoathuoc) {
        this.cotoathuoc = cotoathuoc;
    }

    public String getSttPcs() {
        return sttPcs;
    }

    public void setSttPcs(String sttPcs) {
        this.sttPcs = sttPcs;
    }

    public String getMabn() {
        return mabn;
    }

    public void setMabn(String mabn) {
        this.mabn = mabn;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTenpk() {
        return tenpk;
    }

    public void setTenpk(String tenpk) {
        this.tenpk = tenpk;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getNgaykham() {
        return ngaykham;
    }

    public void setNgaykham(String ngaykham) {
        this.ngaykham = ngaykham;
    }

    public String getChuandoan() {
        return chuandoan;
    }

    public void setChuandoan(String chuandoan) {
        this.chuandoan = chuandoan;
    }
}
