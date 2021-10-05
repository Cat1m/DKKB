package com.hungduy.honghunghospital.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getPhuongXa extends getQuanHuyen{
    @SerializedName("maquanhuyen")
    @Expose
    private String maquanhuyen;

    public getPhuongXa() {
    }

    public String getMaquanhuyen() {
        return maquanhuyen;
    }

    public void setMaquanhuyen(String maquanhuyen) {
        this.maquanhuyen = maquanhuyen;
    }

    public getPhuongXa(String maquanhuyen) {
        this.maquanhuyen = maquanhuyen;
    }

    public getPhuongXa(String ma, String ten, String matinhthanh, String maquanhuyen) {
        super(ma, ten, matinhthanh);
        this.maquanhuyen = maquanhuyen;
    }

    public getPhuongXa(String matinhthanh, String maquanhuyen) {
        super(matinhthanh);
        this.maquanhuyen = maquanhuyen;
    }
}
