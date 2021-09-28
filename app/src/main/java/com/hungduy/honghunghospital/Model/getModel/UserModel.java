package com.hungduy.honghunghospital.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("FullName")
    @Expose
    private String FullName;

    @SerializedName("urlImage")
    @Expose
    private String urlImage;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("appVer")
    @Expose
    private String appVer;

    public UserModel() {
    }

    public UserModel(String fullName, String urlImage,String token) {
        FullName = fullName;
        this.urlImage = urlImage;
        this.token = token;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppVer() {
        return appVer;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer;
    }
}
