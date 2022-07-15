package com.hungduy.honghunghospitalapp.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getThongTinBS {

    @SerializedName("Ma_Nhan_Vien")
    @Expose
    private Integer maNhanVien;
    @SerializedName("Ten_Nhan_Vien")
    @Expose
    private String tenNhanVien;
    @SerializedName("Ten_Chuc_Danh")
    @Expose
    private String tenChucDanh;
    @SerializedName("Ten_CHUC_VU")
    @Expose
    private String tenCHUCVU;
    @SerializedName("HinhAnh")
    @Expose
    private String hinhAnh;
    @SerializedName("ThongTin")
    @Expose
    private String thongTin;

    public getThongTinBS() {
    }

    public getThongTinBS(Integer maNhanVien, String tenNhanVien, String tenChucDanh, String tenCHUCVU, String hinhAnh, String thongTin) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.tenChucDanh = tenChucDanh;
        this.tenCHUCVU = tenCHUCVU;
        this.hinhAnh = hinhAnh;
        this.thongTin = thongTin;
    }

    public Integer getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(Integer maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getTenChucDanh() {
        return tenChucDanh;
    }

    public void setTenChucDanh(String tenChucDanh) {
        this.tenChucDanh = tenChucDanh;
    }

    public String getTenCHUCVU() {
        return tenCHUCVU;
    }

    public void setTenCHUCVU(String tenCHUCVU) {
        this.tenCHUCVU = tenCHUCVU;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getThongTin() {
        return thongTin;
    }

    public void setThongTin(String thongTin) {
        this.thongTin = thongTin;
    }
}
