package com.hungduy.honghunghospital.Model.extModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hungduy.honghunghospital.Model.getModel.getLichLamViecBS;

import java.util.ArrayList;

public class getListLichLamViecBS {
    @SerializedName("tuannay")
    @Expose
    private ArrayList<getLichLamViecBS> tuannay;

    @SerializedName("tuanSau")
    @Expose
    private ArrayList<getLichLamViecBS> tuanSau;

    public getListLichLamViecBS(ArrayList<getLichLamViecBS> tuannay, ArrayList<getLichLamViecBS> tuanSau) {
        this.tuannay = tuannay;
        this.tuanSau = tuanSau;
    }

    public getListLichLamViecBS() {
    }

    public ArrayList<getLichLamViecBS> getTuannay() {
        return tuannay;
    }

    public void setTuannay(ArrayList<getLichLamViecBS> tuannay) {
        this.tuannay = tuannay;
    }

    public ArrayList<getLichLamViecBS> getTuanSau() {
        return tuanSau;
    }

    public void setTuanSau(ArrayList<getLichLamViecBS> tuanSau) {
        this.tuanSau = tuanSau;
    }
}
