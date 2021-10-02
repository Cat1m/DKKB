package com.hungduy.honghunghospital.Model.setModel;

public class setDangKyKham {
    private String MaMucDich;
    private String KhaiBaoYTe;
    private String MaLoaiDangKy;
    private String MaDangKyKham;
    private String KhaiBaoYTeNguoiThan;
    private String KetLuan;

    public setDangKyKham() {
    }

    public setDangKyKham(String maMucDich, String khaiBaoYTe, String maLoaiDangKy, String maDangKyKham, String khaiBaoYTeNguoiThan, String ketLuan) {
        MaMucDich = maMucDich;
        KhaiBaoYTe = khaiBaoYTe;
        MaLoaiDangKy = maLoaiDangKy;
        MaDangKyKham = maDangKyKham;
        KhaiBaoYTeNguoiThan = khaiBaoYTeNguoiThan;
        KetLuan = ketLuan;
    }

    public String getMaMucDich() {
        return MaMucDich;
    }

    public void setMaMucDich(String maMucDich) {
        MaMucDich = maMucDich;
    }

    public String getKhaiBaoYTe() {
        return KhaiBaoYTe;
    }

    public void setKhaiBaoYTe(String khaiBaoYTe) {
        KhaiBaoYTe = khaiBaoYTe;
    }

    public String getMaLoaiDangKy() {
        return MaLoaiDangKy;
    }

    public void setMaLoaiDangKy(String maLoaiDangKy) {
        MaLoaiDangKy = maLoaiDangKy;
    }

    public String getMaDangKyKham() {
        return MaDangKyKham;
    }

    public void setMaDangKyKham(String maDangKyKham) {
        MaDangKyKham = maDangKyKham;
    }

    public String getKhaiBaoYTeNguoiThan() {
        return KhaiBaoYTeNguoiThan;
    }

    public void setKhaiBaoYTeNguoiThan(String khaiBaoYTeNguoiThan) {
        KhaiBaoYTeNguoiThan = khaiBaoYTeNguoiThan;
    }

    public String getKetLuan() {
        return KetLuan;
    }

    public void setKetLuan(String ketLuan) {
        KetLuan = ketLuan;
    }
}
