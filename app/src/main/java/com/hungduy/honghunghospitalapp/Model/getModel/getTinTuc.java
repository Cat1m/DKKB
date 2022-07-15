package com.hungduy.honghunghospitalapp.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getTinTuc {
    @SerializedName("ma")
    @Expose
    private Integer ma;
    @SerializedName("loai")
    @Expose
    private Integer loai;
    @SerializedName("ten")
    @Expose
    private String ten;
    @SerializedName("mota")
    @Expose
    private String mota;
    @SerializedName("url")
    @Expose
    private String url;


    public getTinTuc() {
    }

    public getTinTuc(Integer ma, Integer loai, String ten, String mota, String url) {
        this.ma = ma;
        this.loai = loai;
        this.ten = ten;
        this.mota = mota;
        this.url = url;
    }

    public Integer getLoai() {
        return loai;
    }

    public void setLoai(Integer loai) {
        this.loai = loai;
    }

    public Integer getMa() {
        return ma;
    }

    public void setMa(Integer ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
