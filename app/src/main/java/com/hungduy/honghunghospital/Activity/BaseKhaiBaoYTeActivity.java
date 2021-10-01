package com.hungduy.honghunghospital.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.hungduy.honghunghospital.Model.extModel.CauHoiKhaiBaoYTeEXT;
import com.hungduy.honghunghospital.Model.getModel.getCauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.APIService;
import com.hungduy.honghunghospital.Utility.ApiUtils;
import com.hungduy.honghunghospital.Utility.UtilityHHH;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

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
