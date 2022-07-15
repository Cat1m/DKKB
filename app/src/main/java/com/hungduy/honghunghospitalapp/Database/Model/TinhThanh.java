package com.hungduy.honghunghospitalapp.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TinhThanh")
public class TinhThanh {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "Ma")
    private int Ma;

    @ColumnInfo(name = "Ten")
    private String Ten;

    public TinhThanh() {
    }

    public TinhThanh(@NonNull int ma, String ten) {
        Ma = ma;
        Ten = ten;
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
