package com.hungduy.honghunghospital.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class getBranchModel implements Serializable {
    @SerializedName("ID")
    @Expose
    private int ID;

    @SerializedName("Code")
    @Expose
    private String Code;

    @SerializedName("Name")
    @Expose
    private String Name;

    @SerializedName("FullName")
    @Expose
    private String FullName;

    public getBranchModel(int ID, String code, String name, String fullName) {
        this.ID = ID;
        Code = code;
        Name = name;
        FullName = fullName;
    }

    public getBranchModel() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }
}
