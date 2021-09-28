package com.hungduy.honghunghospital.Model;

public class RequestThangNam {
    private String token;
    private String thang;
    private String nam;

    public RequestThangNam() {
    }

    public RequestThangNam(String token, String thang, String nam) {
        this.token = token;
        this.thang = thang;
        this.nam = nam;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }
}
