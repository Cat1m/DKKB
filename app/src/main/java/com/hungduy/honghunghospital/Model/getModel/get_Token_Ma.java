package com.hungduy.honghunghospital.Model.getModel;

public class get_Token_Ma extends baseGetClass {
    public String ma;

    public get_Token_Ma(String ma) {
        this.ma = ma;
    }

    public get_Token_Ma(String token, String ma) {
        super(token);
        this.ma = ma;
    }
}
