package com.hungduy.honghunghospitalapp.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "QuanHuyen")
public class QuanHuyen {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "Ma")
    private int Ma;

    @ColumnInfo(name = "MaTinhThanh")
    private int MaTinhThanh;

    @ColumnInfo(name = "Ten")
    private String Ten;

    public QuanHuyen(int ma, int maTinhThanh, String ten) {
        Ma = ma;
        MaTinhThanh = maTinhThanh;
        Ten = ten;
    }

    public QuanHuyen() {
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

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }
}
