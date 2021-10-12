package com.hungduy.honghunghospital.Model.setModel;

public class setUserModel {
    private String hoten;
    private String ngaysinh;
    private String gioitinh;
    private String tinh;
    private String quanhuyen;
    private String madantoc;
    private String xaphuong;
    private String apkhupho;
    private String quoctich;
    private String sdt;
    private String password;
    private String sonha;
    private String hochieu;
    private String bhyt;
    private String imgUser;
    private String imgBHYT;

    public setUserModel() {
    }

    public setUserModel(String hoten, String ngaysinh, String gioitinh, String tinh, String quanhuyen, String xaphuong, String apkhupho, String quoctich, String sdt, String password, String sonha, String hochieu, String bhyt, String imgUser, String imgBHYT) {
        this.hoten = hoten;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.tinh = tinh;
        this.quanhuyen = quanhuyen;
        this.xaphuong = xaphuong;
        this.apkhupho = apkhupho;
        this.quoctich = quoctich;
        this.sdt = sdt;
        this.password = password;
        this.sonha = sonha;
        this.hochieu = hochieu;
        this.bhyt = bhyt;
        this.imgUser = imgUser;
        this.imgBHYT = imgBHYT;
    }

    public String getDantoc() {
        return madantoc;
    }

    public void setDantoc(String dantoc) {
        this.madantoc = dantoc;
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

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
