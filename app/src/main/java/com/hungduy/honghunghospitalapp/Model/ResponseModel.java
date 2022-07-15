package com.hungduy.honghunghospitalapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseModel {
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Messenge")
    @Expose
    private String messenge;

    @SerializedName("Data")
    @Expose
    private String Data;

    public ResponseModel(String status, String messenge, String data) {
        this.status = status;
        this.messenge = messenge;
        Data = data;
    }

    public ResponseModel(ResponseModel rm) {
        this.status = rm.status;
        this.messenge = rm.messenge;
        Data = rm.Data;
    }

    public ResponseModel() {
    }

    public String getData() {
        return Data;
    }
    public void setData(String data) {
        Data = data;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessenge() {
        return messenge;
    }

    public void setMessenge(String messenge) {
        this.messenge = messenge;
    }


}
