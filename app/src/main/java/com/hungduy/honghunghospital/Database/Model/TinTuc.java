package com.hungduy.honghunghospital.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TinTuc")
public class TinTuc {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int ID;

    @ColumnInfo(name = "Title")
    private String Title;

    @ColumnInfo(name = "URL")
    private String URL;

    @ColumnInfo(name = "MoTa")
    private String MoTa;

    public TinTuc() {
    }

    public TinTuc(int ID, String title, String URL, String moTa) {
        this.ID = ID;
        Title = title;
        this.URL = URL;
        MoTa = moTa;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }
}
