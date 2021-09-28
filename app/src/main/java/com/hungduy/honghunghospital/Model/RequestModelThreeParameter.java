package com.hungduy.honghunghospital.Model;

public class RequestModelThreeParameter extends RequestModelTwoParameter {
    private String data3;

    public RequestModelThreeParameter() {
    }

    public RequestModelThreeParameter(String token, String data1, String data2, String data3) {
        super(token, data1, data2);
        this.data3 = data3;
    }

    public String getData3() {
        return data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }
}
