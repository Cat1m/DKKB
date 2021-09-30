package com.hungduy.honghunghospital.Model.getModel;

public class baseGetClass {
    public String ma;

    public baseGetClass() {
    }

    public baseGetClass(String token) {
        this.ma = token;
    }

    public String getToken() {
        return ma;
    }

    public void setToken(String token) {
        this.ma = token;
    }
}
