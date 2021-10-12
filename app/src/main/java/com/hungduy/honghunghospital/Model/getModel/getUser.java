package com.hungduy.honghunghospital.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getUser {
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("HinhAnh")
    @Expose
    private String hinhAnh;
    @SerializedName("HoTen")
    @Expose
    private String hoTen;
    @SerializedName("NgaySinh")
    @Expose
    private String ngaySinh;
    @SerializedName("GioiTinh")
    @Expose
    private Integer gioiTinh;
    @SerializedName("MaTinh")
    @Expose
    private Integer maTinh;
    @SerializedName("MaHuyen")
    @Expose
    private Integer maHuyen;
    @SerializedName("MaPhuongXa")
    @Expose
    private Integer maPhuongXa;
    @SerializedName("MaApKhuPho")
    @Expose
    private Integer maApKhuPho;
    @SerializedName("SoNha")
    @Expose
    private String soNha;
    @SerializedName("QuocTich")
    @Expose
    private Integer quocTich;
    @SerializedName("HoChieu")
    @Expose
    private String hoChieu;
    @SerializedName("MaTheBHYT")
    @Expose
    private String maTheBHYT;
    @SerializedName("HinhBHYT")
    @Expose
    private String hinhBHYT;

    @SerializedName("DanToc")
    @Expose
    private String DanToc;

    public getUser() {
    }

    public getUser(String username, String hinhAnh, String hoTen, String ngaySinh, Integer gioiTinh, Integer maTinh, Integer maHuyen, Integer maPhuongXa, Integer maApKhuPho, String soNha, Integer quocTich, String hoChieu, String maTheBHYT, String hinhBHYT, String danToc) {
        this.username = username;
        this.hinhAnh = hinhAnh;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.maTinh = maTinh;
        this.maHuyen = maHuyen;
        this.maPhuongXa = maPhuongXa;
        this.maApKhuPho = maApKhuPho;
        this.soNha = soNha;
        this.quocTich = quocTich;
        this.hoChieu = hoChieu;
        this.maTheBHYT = maTheBHYT;
        this.hinhBHYT = hinhBHYT;
        DanToc = danToc;
    }

    public String getDanToc() {
        return DanToc;
    }

    public void setDanToc(String danToc) {
        DanToc = danToc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Integer getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Integer gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Integer getMaTinh() {
        return maTinh;
    }

    public void setMaTinh(Integer maTinh) {
        this.maTinh = maTinh;
    }

    public Integer getMaHuyen() {
        return maHuyen;
    }

    public void setMaHuyen(Integer maHuyen) {
        this.maHuyen = maHuyen;
    }

    public Integer getMaPhuongXa() {
        return maPhuongXa;
    }

    public void setMaPhuongXa(Integer maPhuongXa) {
        this.maPhuongXa = maPhuongXa;
    }

    public Integer getMaApKhuPho() {
        return maApKhuPho;
    }

    public void setMaApKhuPho(Integer maApKhuPho) {
        this.maApKhuPho = maApKhuPho;
    }

    public String getSoNha() {
        return soNha;
    }

    public void setSoNha(String soNha) {
        this.soNha = soNha;
    }

    public Integer getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(Integer quocTich) {
        this.quocTich = quocTich;
    }

    public String getHoChieu() {
        return hoChieu;
    }

    public void setHoChieu(String hoChieu) {
        this.hoChieu = hoChieu;
    }

    public String getMaTheBHYT() {
        return maTheBHYT;
    }

    public void setMaTheBHYT(String maTheBHYT) {
        this.maTheBHYT = maTheBHYT;
    }

    public String getHinhBHYT() {
        return hinhBHYT;
    }

    public void setHinhBHYT(String hinhBHYT) {
        this.hinhBHYT = hinhBHYT;
    }
}
