package com.hungduy.honghunghospital.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hungduy.honghunghospital.Model.extModel.CauHoiKhaiBaoYTeEXT;
import com.hungduy.honghunghospital.Model.getModel.getCauHoiKhaiBaoYTe;

import java.util.ArrayList;

public abstract class BaseKhaiBaoYTeActivity extends BaseActivity {
    protected ArrayList<getCauHoiKhaiBaoYTe> cauHoiKhaiBaoYTes;
    protected ArrayList<CauHoiKhaiBaoYTeEXT> CauTL;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void updateCauTraLoi(CauHoiKhaiBaoYTeEXT tl){
        try{
            for (CauHoiKhaiBaoYTeEXT a : CauTL ) {
                if(a.getMa().equals(tl.getMa()) && !a.getCautraloi().equals(tl.getCautraloi())){
                    a.setCautraloi(tl.getCautraloi());
                    //Log.d(TAG,tl.getCauhoi() + " - "+tl.getCautraloi());
                    break;
                }
            }

        }catch (Exception ex){

        }
    }

}
