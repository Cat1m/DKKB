package com.hungduy.honghunghospitalapp.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "KhuPho")
public class KhuPho {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "Ma")
    private int Ma;

    @ColumnInfo(name = "MaPhuongXa")
    private int MaPhuongXa;

    @ColumnInfo(name = "Ten")
    private String Ten;

    public KhuPho() {
    }

    public KhuPho(int ma, int maPhuongXa, String ten) {
        Ma = ma;
        MaPhuongXa = maPhuongXa;
        Ten = ten;
    }

    public int getMa() {
        return Ma;
    }

    public void setMa(int ma) {
        Ma = ma;
    }

    public int getMaPhuongXa() {
        return MaPhuongXa;
    }

    public void setMaPhuongXa(int maPhuongXa) {
        MaPhuongXa = maPhuongXa;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }
}
