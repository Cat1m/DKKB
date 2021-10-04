package com.hungduy.honghunghospital.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserData")
public class UserData {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "ConfigName")
    private String ConfigName;

    @ColumnInfo(name = "ConfigInfo")
    private String ConfigInfo;

}
