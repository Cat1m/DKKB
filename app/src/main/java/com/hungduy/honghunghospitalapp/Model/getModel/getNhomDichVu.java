package com.hungduy.honghunghospitalapp.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getNhomDichVu extends getMaTen {
    @SerializedName("donvi")
    @Expose
    private String donvi;

    public getNhomDichVu() {
    }

    public getNhomDichVu(String ma, String ten, String donvi) {
        super(ma, ten);
        this.donvi = donvi;
    }

    public String getDonvi() {
        return donvi;
    }

    public void setDonvi(String donvi) {
        this.donvi = donvi;
    }
}
