package com.hungduy.honghunghospital.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CauHoiKhaiBaoYTe")
public class CauHoiKhaiBaoYTe {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int ID;

    @ColumnInfo(name = "CauHoi")
    private String CauHoi;

    public CauHoiKhaiBaoYTe() {
    }

    public CauHoiKhaiBaoYTe(int ID, String cauHoi) {
        this.ID = ID;
        CauHoi = cauHoi;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCauHoi() {
        return CauHoi;
    }

    public void setCauHoi(String cauHoi) {
        CauHoi = cauHoi;
    }
}
