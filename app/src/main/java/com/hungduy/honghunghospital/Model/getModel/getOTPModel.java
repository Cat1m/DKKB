package com.hungduy.honghunghospital.Model.getModel;

public class getOTPModel extends  baseGetClass{
    private String phonenumber;

    public getOTPModel(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public getOTPModel(String token, String phonenumber) {
        super(token);
        this.phonenumber = phonenumber;
    }
}
