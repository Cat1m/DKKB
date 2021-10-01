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

public abstract class BaseActivity extends AppCompatActivity {
    protected APIService mAPIService;
    private FirebaseAnalytics mFirebaseAnalytics;
    protected static String preferences = "preferences";
    protected String TAG;
    protected TextView txtTitle;
    protected ImageView btnBack;
    protected String username,password, branchID ,FullName,urlImage,branchName,token;
    protected String APIKey;
    protected Drawable shape_edittext_error ;
    protected Drawable shape_edittext_have_focus ;
    private TextView txtUser;
    private CircleImageView imgUser;
    protected ImageView imgLogoBVHH;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAPIService = ApiUtils.getAPIService();// register API services
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        TAG = this.getClass().getSimpleName();
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Intent LoginIntent= getIntent();
        Bundle BundleLogin = LoginIntent.getExtras();
        if(BundleLogin!=null)
        {
            username =(String) BundleLogin.get("username");
            password =(String) BundleLogin.get("password");
            branchID =(String) BundleLogin.get("branchID");
            branchName =(String) BundleLogin.get("branchName");
            FullName =(String) BundleLogin.get("FullName");
            urlImage =(String) BundleLogin.get("urlImage");
            token =(String) BundleLogin.get("token");
        }
        String currentYearMonth = new SimpleDateFormat("yyyyMM", Locale.getDefault()).format(new Date());
        try {
            APIKey = UtilityHHH.getSha512fromString("HHHApp4P1"+currentYearMonth);
        }catch (Exception ex){
        }
        shape_edittext_error = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.shape_edittext_error);
        shape_edittext_have_focus = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.shape_edittext_have_focus);




    }

    @Override
    protected void onStart() {
        super.onStart();
        try{
           // txtTitle = findViewById(R.id.txtTitle);
         //   btnBack = findViewById(R.id.btnBack);
        //    btnBack.setOnClickListener(new View.OnClickListener() {
        //        @Override
       //         public void onClick(View v) {
     //               finish();
       //         }
       //     });
        }catch (Exception ex){
            txtTitle = null;
            btnBack = null;
        }
        try {
            txtUser = findViewById(R.id.txtUser);
            imgUser = findViewById(R.id.imgUser);

            if(urlImage != null && !urlImage.isEmpty()){
                Picasso.get().load(urlImage).placeholder(R.drawable.user).into(imgUser);
            }

            if(FullName != null && !FullName.isEmpty()){
                txtUser.setText(FullName);
            }else{
                txtUser.setText("*");
            }

        }catch (Exception x){
            txtUser = null;
            imgUser = null;
        }
        try {
            imgLogoBVHH = findViewById(R.id.imgLogoBVHH);
            imgLogoBVHH.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), LogoActivity.class);
                    i.putExtra("FullName",FullName);
                    i.putExtra("urlImage",urlImage);
                    i.putExtra("token",token);
                    startActivity(i);
                }
            });
        }catch (Exception ex){

        }


    }


    protected boolean getBooleanPreferences(String name, String key){
        SharedPreferences sharedPreferences = getSharedPreferences(name, MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }
    protected void setBooleanPreferences(String name,String key,boolean value){
        SharedPreferences.Editor edit = getSharedPreferences(name, MODE_PRIVATE).edit();
        edit.putBoolean(key,value);
        edit.commit();
    }
    protected String getStringPreferences(String name,String key){
        SharedPreferences sharedPreferences = getSharedPreferences(name, MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
    protected void setStringPreferences(String name,String key,String value){
        SharedPreferences.Editor edit = getSharedPreferences(name, MODE_PRIVATE).edit();
        edit.putString(key,value);
        edit.commit();
    }


    protected void ThongBao(Activity activity, String title, String Mes, int gif, FancyGifDialogListener okclick){
        new FancyGifDialog.Builder(activity)
                .setTitle(title)
                .setMessage(Mes)
                .setPositiveBtnBackground("#FF4081")
                .setPositiveBtnText("Đồng ý")
                .setGifResource(gif)
                .OnPositiveClicked(okclick)
                .build();
    }
    protected void ThongBao(Activity activity, String title, String Mes, int gif){
        new FancyGifDialog.Builder(activity)
                .setTitle(title)
                .setMessage(Mes)
                .setPositiveBtnBackground("#FF4081")
                .setPositiveBtnText("Đồng ý")
                .setGifResource(gif)
                .build();
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

}
