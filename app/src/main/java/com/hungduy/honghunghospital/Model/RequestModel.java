package com.hungduy.honghunghospital.Model;

public class RequestModel {
    private String token;
    private String branch;
    private String code;

    public RequestModel() {
    }

    public RequestModel(String token, String branch, String code) {
        this.token = token;
        this.branch = branch;
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
