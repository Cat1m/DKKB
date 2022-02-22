package com.hungduy.honghunghospital.Model.setModel;

public class linkHis {
    private String ma;
    private String otp;

    public linkHis() {
    }

    public linkHis(String ma, String otp) {
        this.ma = ma;
        this.otp = otp;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}

