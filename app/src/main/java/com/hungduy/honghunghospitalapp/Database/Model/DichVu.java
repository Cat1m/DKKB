package com.hungduy.honghunghospitalapp.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "DichVu")
public class DichVu {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "Ma")
    private int Ma;

    @ColumnInfo(name = "MaNhom")
    private int MaNhom;

    @ColumnInfo(name = "Ten")
    private String Ten;

    @ColumnInfo(name = "Gia")
    private String Gia;

    public DichVu() {
    }

    public DichVu(int ma, int maNhom, String ten, String gia) {
        Ma = ma;
        MaNhom = maNhom;
        Ten = ten;
        Gia = gia;
    }

    public String getGia() {
        return Gia;
    }

    public void setGia(String gia) {
        Gia = gia;
    }

    public int getMaNhom() {
        return MaNhom;
    }

    public void setMaNhom(int maNhom) {
        MaNhom = maNhom;
    }

    public int getMa() {
        return Ma;
    }

    public void setMa(int ma) {
        Ma = ma;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }
}
