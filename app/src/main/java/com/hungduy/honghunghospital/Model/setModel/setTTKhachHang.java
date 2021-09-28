package com.hungduy.honghunghospital.Model.setModel;

public class setTTKhachHang extends BaseSetClass{
    private String MaKH;
    private String MaLoaiKH;
    private String ma;
    private String nhomGD1;
    private String nhomGD2;
    private String nhomGD3;
    private String tenCongTy;
    private String quocGia;
    private String tinhThanh;
    private String huyenThi;
    private String phuongXa;
    private String tenDuong;
    private String quocGiaGiaoHang;
    private String tinhThanhGiaoHang;
    private String huyenThiGiaoHang;
    private String phuongXaGiaoHang;
    private String tenDuongGiaoHang;
    private String maSoThue;
    private String dienThoai;
    private String daiDienPL;
    private String chucVuNguoiDaiDien;
    private String email;
    private String fax;
    private String tenNganHang;
    private String stk;
    private String latitude;
    private String longitude;
    private String tenNLH;
    private String chucVuNLH;
    private String emailNLH;
    private String faxNLH;
    private String dienThoaiNLH;
    private String tenKT;
    private String chucVuKT;
    private String emailKT;
    private String faxKT;
    private String dienThoaiKT;
    private String hanMucNo;
    private String ngayCongNo;
    private String maNguonKH;

    public setTTKhachHang() {
    }

    public setTTKhachHang(String token, String code, int branch, String maKH, String maLoaiKH, String ma, String nhomGD1, String nhomGD2, String nhomGD3, String tenCongTy, String quocGia, String tinhThanh, String huyenThi, String phuongXa, String tenDuong, String quocGiaGiaoHang, String tinhThanhGiaoHang, String huyenThiGiaoHang, String phuongXaGiaoHang, String tenDuongGiaoHang, String maSoThue, String dienThoai, String daiDienPL, String chucVuNguoiDaiDien, String email, String fax, String tenNganHang, String stk, String latitude, String longitude, String tenNLH, String chucVuNLH, String emailNLH, String faxNLH, String dienThoaiNLH, String tenKT, String chucVuKT, String emailKT, String faxKT, String dienThoaiKT, String hanMucNo, String ngayCongNo, String maNguonKH) {
        super(token, code, branch);
        MaKH = maKH;
        MaLoaiKH = maLoaiKH;
        this.ma = ma;
        this.nhomGD1 = nhomGD1;
        this.nhomGD2 = nhomGD2;
        this.nhomGD3 = nhomGD3;
        this.tenCongTy = tenCongTy;
        this.quocGia = quocGia;
        this.tinhThanh = tinhThanh;
        this.huyenThi = huyenThi;
        this.phuongXa = phuongXa;
        this.tenDuong = tenDuong;
        this.quocGiaGiaoHang = quocGiaGiaoHang;
        this.tinhThanhGiaoHang = tinhThanhGiaoHang;
        this.huyenThiGiaoHang = huyenThiGiaoHang;
        this.phuongXaGiaoHang = phuongXaGiaoHang;
        this.tenDuongGiaoHang = tenDuongGiaoHang;
        this.maSoThue = maSoThue;
        this.dienThoai = dienThoai;
        this.daiDienPL = daiDienPL;
        this.chucVuNguoiDaiDien = chucVuNguoiDaiDien;
        this.email = email;
        this.fax = fax;
        this.tenNganHang = tenNganHang;
        this.stk = stk;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tenNLH = tenNLH;
        this.chucVuNLH = chucVuNLH;
        this.emailNLH = emailNLH;
        this.faxNLH = faxNLH;
        this.dienThoaiNLH = dienThoaiNLH;
        this.tenKT = tenKT;
        this.chucVuKT = chucVuKT;
        this.emailKT = emailKT;
        this.faxKT = faxKT;
        this.dienThoaiKT = dienThoaiKT;
        this.hanMucNo = hanMucNo;
        this.ngayCongNo = ngayCongNo;
        this.maNguonKH = maNguonKH;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    public String getMaLoaiKH() {
        return MaLoaiKH;
    }

    public void setMaLoaiKH(String maLoaiKH) {
        MaLoaiKH = maLoaiKH;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getNhomGD1() {
        return nhomGD1;
    }

    public void setNhomGD1(String nhomGD1) {
        this.nhomGD1 = nhomGD1;
    }

    public String getNhomGD2() {
        return nhomGD2;
    }

    public void setNhomGD2(String nhomGD2) {
        this.nhomGD2 = nhomGD2;
    }

    public String getNhomGD3() {
        return nhomGD3;
    }

    public void setNhomGD3(String nhomGD3) {
        this.nhomGD3 = nhomGD3;
    }

    public String getTenCongTy() {
        return tenCongTy;
    }

    public void setTenCongTy(String tenCongTy) {
        this.tenCongTy = tenCongTy;
    }

    public String getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }

    public String getTinhThanh() {
        return tinhThanh;
    }

    public void setTinhThanh(String tinhThanh) {
        this.tinhThanh = tinhThanh;
    }

    public String getHuyenThi() {
        return huyenThi;
    }

    public void setHuyenThi(String huyenThi) {
        this.huyenThi = huyenThi;
    }

    public String getPhuongXa() {
        return phuongXa;
    }

    public void setPhuongXa(String phuongXa) {
        this.phuongXa = phuongXa;
    }

    public String getTenDuong() {
        return tenDuong;
    }

    public void setTenDuong(String tenDuong) {
        this.tenDuong = tenDuong;
    }

    public String getQuocGiaGiaoHang() {
        return quocGiaGiaoHang;
    }

    public void setQuocGiaGiaoHang(String quocGiaGiaoHang) {
        this.quocGiaGiaoHang = quocGiaGiaoHang;
    }

    public String getTinhThanhGiaoHang() {
        return tinhThanhGiaoHang;
    }

    public void setTinhThanhGiaoHang(String tinhThanhGiaoHang) {
        this.tinhThanhGiaoHang = tinhThanhGiaoHang;
    }

    public String getHuyenThiGiaoHang() {
        return huyenThiGiaoHang;
    }

    public void setHuyenThiGiaoHang(String huyenThiGiaoHang) {
        this.huyenThiGiaoHang = huyenThiGiaoHang;
    }

    public String getPhuongXaGiaoHang() {
        return phuongXaGiaoHang;
    }

    public void setPhuongXaGiaoHang(String phuongXaGiaoHang) {
        this.phuongXaGiaoHang = phuongXaGiaoHang;
    }

    public String getTenDuongGiaoHang() {
        return tenDuongGiaoHang;
    }

    public void setTenDuongGiaoHang(String tenDuongGiaoHang) {
        this.tenDuongGiaoHang = tenDuongGiaoHang;
    }

    public String getMaSoThue() {
        return maSoThue;
    }

    public void setMaSoThue(String maSoThue) {
        this.maSoThue = maSoThue;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getDaiDienPL() {
        return daiDienPL;
    }

    public void setDaiDienPL(String daiDienPL) {
        this.daiDienPL = daiDienPL;
    }

    public String getChucVuNguoiDaiDien() {
        return chucVuNguoiDaiDien;
    }

    public void setChucVuNguoiDaiDien(String chucVuNguoiDaiDien) {
        this.chucVuNguoiDaiDien = chucVuNguoiDaiDien;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTenNganHang() {
        return tenNganHang;
    }

    public void setTenNganHang(String tenNganHang) {
        this.tenNganHang = tenNganHang;
    }

    public String getStk() {
        return stk;
    }

    public void setStk(String stk) {
        this.stk = stk;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTenNLH() {
        return tenNLH;
    }

    public void setTenNLH(String tenNLH) {
        this.tenNLH = tenNLH;
    }

    public String getChucVuNLH() {
        return chucVuNLH;
    }

    public void setChucVuNLH(String chucVuNLH) {
        this.chucVuNLH = chucVuNLH;
    }

    public String getEmailNLH() {
        return emailNLH;
    }

    public void setEmailNLH(String emailNLH) {
        this.emailNLH = emailNLH;
    }

    public String getFaxNLH() {
        return faxNLH;
    }

    public void setFaxNLH(String faxNLH) {
        this.faxNLH = faxNLH;
    }

    public String getDienThoaiNLH() {
        return dienThoaiNLH;
    }

    public void setDienThoaiNLH(String dienThoaiNLH) {
        this.dienThoaiNLH = dienThoaiNLH;
    }

    public String getTenKT() {
        return tenKT;
    }

    public void setTenKT(String tenKT) {
        this.tenKT = tenKT;
    }

    public String getChucVuKT() {
        return chucVuKT;
    }

    public void setChucVuKT(String chucVuKT) {
        this.chucVuKT = chucVuKT;
    }

    public String getEmailKT() {
        return emailKT;
    }

    public void setEmailKT(String emailKT) {
        this.emailKT = emailKT;
    }

    public String getFaxKT() {
        return faxKT;
    }

    public void setFaxKT(String faxKT) {
        this.faxKT = faxKT;
    }

    public String getDienThoaiKT() {
        return dienThoaiKT;
    }

    public void setDienThoaiKT(String dienThoaiKT) {
        this.dienThoaiKT = dienThoaiKT;
    }

    public String getHanMucNo() {
        return hanMucNo;
    }

    public void setHanMucNo(String hanMucNo) {
        this.hanMucNo = hanMucNo;
    }

    public String getNgayCongNo() {
        return ngayCongNo;
    }

    public void setNgayCongNo(String ngayCongNo) {
        this.ngayCongNo = ngayCongNo;
    }

    public String getMaNguonKH() {
        return maNguonKH;
    }

    public void setMaNguonKH(String maNguonKH) {
        this.maNguonKH = maNguonKH;
    }
}
