package com.hungduy.honghunghospital.Model.getModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getDichVu extends getMaTen {
    @SerializedName("maloai")
    @Expose
    private String maloai;

    @SerializedName("gia")
    @Expose
    private String gia;


    public getDichVu() {
    }

    public getDichVu(String ma, String ten, String maloai, String gia) {
        super(ma, ten);
        this.maloai = maloai;
        this.gia = gia;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getMaloai() {
        return maloai;
    }

    public void setMaloai(String maloai) {
        this.maloai = maloai;
    }
}
