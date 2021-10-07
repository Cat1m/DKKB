package com.hungduy.honghunghospital.Model.setModel;

public class updateUser {
    private String tinh;
    private String quanhuyen;
    private String xaphuong;
    private String apkhupho;
    private String quoctich;
    private String password;
    private String oldPassword;
    private String sonha;
    private String hochieu;
    private String bhyt;
    private String imgUser;
    private String imgBHYT;

    public updateUser() {
    }

    public updateUser(String tinh, String quanhuyen, String xaphuong, String apkhupho, String quoctich, String password, String oldPassword, String sonha, String hochieu, String bhyt, String imgUser, String imgBHYT) {
        this.tinh = tinh;
        this.quanhuyen = quanhuyen;
        this.xaphuong = xaphuong;
        this.apkhupho = apkhupho;
        this.quoctich = quoctich;
        this.password = password;
        this.oldPassword = oldPassword;
        this.sonha = sonha;
        this.hochieu = hochieu;
        this.bhyt = bhyt;
        this.imgUser = imgUser;
        this.imgBHYT = imgBHYT;
    }

    public String getTinh() {
        return tinh;
    }

    public void setTinh(String tinh) {
        this.tinh = tinh;
    }

    public String getQuanhuyen() {
        return quanhuyen;
    }

    public void setQuanhuyen(String quanhuyen) {
        this.quanhuyen = quanhuyen;
    }

    public String getXaphuong() {
        return xaphuong;
    }

    public void setXaphuong(String xaphuong) {
        this.xaphuong = xaphuong;
    }

    public String getApkhupho() {
        return apkhupho;
    }

    public void setApkhupho(String apkhupho) {
        this.apkhupho = apkhupho;
    }

    public String getQuoctich() {
        return quoctich;
    }

    public void setQuoctich(String quoctich) {
        this.quoctich = quoctich;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getSonha() {
        return sonha;
    }

    public void setSonha(String sonha) {
        this.sonha = sonha;
    }

    public String getHochieu() {
        return hochieu;
    }

    public void setHochieu(String hochieu) {
        this.hochieu = hochieu;
    }

    public String getBhyt() {
        return bhyt;
    }

    public void setBhyt(String bhyt) {
        this.bhyt = bhyt;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }

    public String getImgBHYT() {
        return imgBHYT;
    }

    public void setImgBHYT(String imgBHYT) {
        this.imgBHYT = imgBHYT;
    }
}
