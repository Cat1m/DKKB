package com.hungduy.honghunghospitalapp.Model.extModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hungduy.honghunghospitalapp.Model.getModel.getMaTen;

public class getMaBN extends getMaTen {
    @SerializedName("nam")
    @Expose
    private String nam;

    public getMaBN() {
    }

    public getMaBN(String ma, String ten, String nam) {
        super(ma, ten);
        this.nam = nam;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }
}
