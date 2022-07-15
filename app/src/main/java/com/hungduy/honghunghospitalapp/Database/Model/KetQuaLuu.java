package com.hungduy.honghunghospitalapp.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "KetQuaLuu")
public class KetQuaLuu {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Ma")
    private int Ma;

    @ColumnInfo(name = "MaQR")
    private String MaQR;

    @ColumnInfo(name = "KetQua")
    private String KetQua;

    @ColumnInfo(name = "TestNhanh")
    private int TestNhanh;

    @ColumnInfo(name = "Loai")
    private int Loai;

    public KetQuaLuu() {
    }

    public KetQuaLuu(int ma, String maQR, String ketQua, int testNhanh, int loai) {
        Ma = ma;
        MaQR = maQR;
        KetQua = ketQua;
        TestNhanh = testNhanh;
        Loai = loai;
    }
    public KetQuaLuu(String maQR, String ketQua, int testNhanh, int loai) {
        MaQR = maQR;
        KetQua = ketQua;
        TestNhanh = testNhanh;
        Loai = loai;
    }

    public int getLoai() {
        return Loai;
    }

    public void setLoai(int loai) {
        Loai = loai;
    }

    public int getMa() {
        return Ma;
    }

    public void setMa(int ma) {
        Ma = ma;
    }

    public String getMaQR() {
        return MaQR;
    }

    public void setMaQR(String maQR) {
        MaQR = maQR;
    }

    public String getKetQua() {
        return KetQua;
    }

    public void setKetQua(String ketQua) {
        KetQua = ketQua;
    }

    public int getTestNhanh() {
        return TestNhanh;
    }

    public void setTestNhanh(int testNhanh) {
        TestNhanh = testNhanh;
    }
}
