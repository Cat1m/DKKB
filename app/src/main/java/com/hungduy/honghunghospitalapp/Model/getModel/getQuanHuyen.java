package com.hungduy.honghunghospitalapp.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getQuanHuyen extends getMaTen{

    @SerializedName("matinhthanh")
    @Expose
    private String matinhthanh;

    public getQuanHuyen() {
    }

    public getQuanHuyen(String ma, String ten, String matinhthanh) {
        super(ma, ten);
        this.matinhthanh = matinhthanh;
    }

    public getQuanHuyen(String matinhthanh) {
        this.matinhthanh = matinhthanh;
    }


    public String getMatinhthanh() {
        return matinhthanh;
    }

    public void setMatinhthanh(String matinhthanh) {
        this.matinhthanh = matinhthanh;
    }
}
