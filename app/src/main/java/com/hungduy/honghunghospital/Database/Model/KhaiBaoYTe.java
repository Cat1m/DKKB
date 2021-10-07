package com.hungduy.honghunghospital.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "KhaiBaoYTe")
public class KhaiBaoYTe {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int ID;

    @ColumnInfo(name = "CauTraLoi")
    private String CauTraLoi;

    public KhaiBaoYTe() {
    }

    public KhaiBaoYTe(int ID, String CauTraLoi) {
        this.ID = ID;
        this.CauTraLoi = CauTraLoi;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCauTraLoi() {
        return CauTraLoi;
    }

    public void setCauTraLoi(String cauTraLoi) {
        CauTraLoi = cauTraLoi;
    }
}
