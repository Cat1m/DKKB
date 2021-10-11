package com.hungduy.honghunghospital.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getTinTuc {
    @SerializedName("ma")
    @Expose
    private Integer ma;
    @SerializedName("ten")
    @Expose
    private String ten;
    @SerializedName("mota")
    @Expose
    private String mota;
    @SerializedName("url")
    @Expose
    private String url;

    public getTinTuc(Integer ma, String ten, String mota, String url) {
        this.ma = ma;
        this.ten = ten;
        this.mota = mota;
        this.url = url;
    }

    public getTinTuc() {
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
