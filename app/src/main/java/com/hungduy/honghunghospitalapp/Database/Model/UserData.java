package com.hungduy.honghunghospitalapp.Database.Model;

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

    public UserData() {
    }

    public UserData(@NonNull String configName, String configInfo) {
        ConfigName = configName;
        ConfigInfo = configInfo;
    }

    @NonNull
    public String getConfigName() {
        return ConfigName;
    }

    public void setConfigName(@NonNull String configName) {
        ConfigName = configName;
    }

    public String getConfigInfo() {
        return ConfigInfo;
    }

    public void setConfigInfo(String configInfo) {
        ConfigInfo = configInfo;
    }
}
