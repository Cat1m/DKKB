package com.hungduy.honghunghospital.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "DanToc")
public class DanToc {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "Ma")
    private int Ma;

    @ColumnInfo(name = "Ten")
    private String Ten;

    public DanToc() {
    }

    public DanToc(@NonNull int ma, String ten) {
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
