package com.hungduy.honghunghospitalapp.Model.extModel;

import com.hungduy.honghunghospitalapp.Model.getModel.getCauHoiKhaiBaoYTe;

public class CauHoiKhaiBaoYTeEXT extends getCauHoiKhaiBaoYTe {
    private String cautraloi;

    public CauHoiKhaiBaoYTeEXT(String ma, String cauhoi, String loai, String cautraloi) {
        super(ma, cauhoi, loai);
        this.cautraloi = cautraloi;
    }

    public CauHoiKhaiBaoYTeEXT(getCauHoiKhaiBaoYTe a) {
        super(a.getMa(), a.getCauhoi(), a.getLoai());
    }
    public CauHoiKhaiBaoYTeEXT(getCauHoiKhaiBaoYTe a,String cautraloi) {
        super(a.getMa(), a.getCauhoi(), a.getLoai());
        this.cautraloi = cautraloi;
    }
    public CauHoiKhaiBaoYTeEXT(String cautraloi) {
        this.cautraloi = cautraloi;
    }

    public CauHoiKhaiBaoYTeEXT() {
    }

    public String getCautraloi() {
        return cautraloi;
    }

    public void setCautraloi(String cautraloi) {
        this.cautraloi = cautraloi;
    }
}
