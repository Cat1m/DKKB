package com.hungduy.honghunghospitalapp.Model.getModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getLichLamViecBS {
    @SerializedName("thu")
    @Expose
    private int thu;
    @SerializedName("sang")
    @Expose
    private boolean sang;
    @SerializedName("chieu")
    @Expose
    private boolean chieu;

    public getLichLamViecBS() {
    }

    public getLichLamViecBS(int thu, boolean sang, boolean chieu) {
        this.thu = thu;
        this.sang = sang;
        this.chieu = chieu;
    }

    public int getThu() {
        return thu;
    }

    public void setThu(int thu) {
        this.thu = thu;
    }

    public boolean isSang() {
        return sang;
    }

    public void setSang(boolean sang) {
        this.sang = sang;
    }

    public boolean isChieu() {
        return chieu;
    }

    public void setChieu(boolean chieu) {
        this.chieu = chieu;
    }
}
