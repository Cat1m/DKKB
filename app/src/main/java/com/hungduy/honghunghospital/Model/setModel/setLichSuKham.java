package com.hungduy.honghunghospital.Model.setModel;

public class setLichSuKham {
    private String tungay;
    private String denngay;
    private String mabenhnhan;
    private String sothebhyt ="";

    public setLichSuKham(String tungay, String denngay, String mabenhnhan) {
        this.tungay = tungay;
        this.denngay = denngay;
        this.mabenhnhan = mabenhnhan;
    }

    public String getTungay() {
        return tungay;
    }

    public void setTungay(String tungay) {
        this.tungay = tungay;
    }

    public String getDenngay() {
        return denngay;
    }

    public void setDenngay(String denngay) {
        this.denngay = denngay;
    }

    public String getMabenhnhan() {
        return mabenhnhan;
    }

    public void setMabenhnhan(String mabenhnhan) {
        this.mabenhnhan = mabenhnhan;
    }

}
