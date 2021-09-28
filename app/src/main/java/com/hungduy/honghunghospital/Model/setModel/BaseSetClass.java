package com.hungduy.honghunghospital.Model.setModel;

public class BaseSetClass {
    private String token;
    private String code;
    private int branch;

    public BaseSetClass() {
    }

    public BaseSetClass(String token, String code, int branch) {
        this.token = token;
        this.code = code;
        this.branch = branch;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }
}
