package com.hungduy.honghunghospital.Model.extModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hungduy.honghunghospital.Model.getModel.getMaTen;

public class getBSCoHinh extends getMaTen {
    @SerializedName("url")
    @Expose
    private String url;

    public getBSCoHinh() {
    }

    public getBSCoHinh(String ma, String ten, String url) {
        super(ma, ten);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
