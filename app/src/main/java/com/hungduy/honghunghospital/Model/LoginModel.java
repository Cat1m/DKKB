package com.hungduy.honghunghospital.Model;

public class LoginModel {
    public String token,username,password;
    private int branchID;
    public LoginModel() {
    }
    public LoginModel(String token, String username, String password,int branchID) {
        this.token = token;
        this.username = username;
        this.password = password;
        this.branchID = branchID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "{ token:'" + token + "',username:'" + username + "',password:'" + password + "','branchID':'"+branchID+"'}";
    }
}
