package com.hungduy.honghunghospital.Model.setModel;

public class searchMaBN {
    private String hoten;
    private String ngaysinh;
    private String cmnd;
    private String sdt;
    private String bhyt;

    public searchMaBN() {
    }

    public searchMaBN(String hoten, String ngaysinh, String cmnd, String sdt, String bhyt) {
        this.hoten = hoten;
        this.ngaysinh = ngaysinh;
        this.cmnd = cmnd;
        this.sdt = sdt;
        this.bhyt = bhyt;
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

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getBhyt() {
        return bhyt;
    }

    public void setBhyt(String bhyt) {
        this.bhyt = bhyt;
    }
}
