package com.hungduy.honghunghospitalapp.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ChiTietBacSi")
public class BacSiDetail {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "Ma")
    private int Ma;

    @NonNull
    @ColumnInfo(name = "MaBS")
    private int MaBS;

    @ColumnInfo(name = "ChiTiet")
    private String ChiTiet;

    @ColumnInfo(name = "LichLamViec")
    private String LichLamViec;


    public BacSiDetail() {
    }

    public BacSiDetail(int ma, int maBS, String chiTiet, String lichLamViec) {
        Ma = ma;
        MaBS = maBS;
        ChiTiet = chiTiet;
        LichLamViec = lichLamViec;
    }

    public int getMa() {
        return Ma;
    }

    public void setMa(int ma) {
        Ma = ma;
    }

    public int getMaBS() {
        return MaBS;
    }

    public void setMaBS(int maBS) {
        MaBS = maBS;
    }

    public String getChiTiet() {
        return ChiTiet;
    }

    public void setChiTiet(String chiTiet) {
        ChiTiet = chiTiet;
    }

    public String getLichLamViec() {
        return LichLamViec;
    }

    public void setLichLamViec(String lichLamViec) {
        LichLamViec = lichLamViec;
    }
}
