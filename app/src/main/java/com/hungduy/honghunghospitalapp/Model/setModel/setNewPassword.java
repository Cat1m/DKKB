package com.hungduy.honghunghospitalapp.Model.setModel;

import com.hungduy.honghunghospitalapp.Model.getModel.getOTPModel;

public class setNewPassword extends getOTPModel {
    private String otp;
    private String password;

    public setNewPassword(String phonenumber, String otp, String password) {
        super(phonenumber);
        this.otp = otp;
        this.password = password;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
