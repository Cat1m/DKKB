package com.hungduy.honghunghospitalapp.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PhuongXa")
public class PhuongXa {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "Ma")
    private int Ma;

    @ColumnInfo(name = "MaTinhThanh")
    private int MaTinhThanh;

    @ColumnInfo(name = "MaQuanHuyen")
    private int MaQuanHuyen;

    @ColumnInfo(name = "Ten")
    private String Ten;

    public PhuongXa(int ma, int maTinhThanh, int maQuanHuyen, String ten) {
        Ma = ma;
        MaTinhThanh = maTinhThanh;
        MaQuanHuyen = maQuanHuyen;
        Ten = ten;
    }

    public PhuongXa() {
    }

    public int getMa() {
        return Ma;
    }

    public void setMa(int ma) {
        Ma = ma;
    }

    public int getMaTinhThanh() {
        return MaTinhThanh;
    }

    public void setMaTinhThanh(int maTinhThanh) {
        MaTinhThanh = maTinhThanh;
    }

    public int getMaQuanHuyen() {
        return MaQuanHuyen;
    }

    public void setMaQuanHuyen(int maQuanHuyen) {
        MaQuanHuyen = maQuanHuyen;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }
}
