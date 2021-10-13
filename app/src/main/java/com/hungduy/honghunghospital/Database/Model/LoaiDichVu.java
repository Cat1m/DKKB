package com.hungduy.honghunghospital.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LoaiDichVu")
public class LoaiDichVu {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "Ma")
    private int Ma;

    @ColumnInfo(name = "Ten")
    private String Ten;

    @ColumnInfo(name = "DonVi")
    private String DonVi;

    public LoaiDichVu() {
    }

    public LoaiDichVu(int ma, String ten, String donVi) {
        Ma = ma;
        Ten = ten;
        DonVi = donVi;
    }

    public String getDonVi() {
        return DonVi;
    }

    public void setDonVi(String donVi) {
        DonVi = donVi;
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
