package com.hungduy.honghunghospital.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.hungduy.honghunghospital.FancyGifDialog.FancyGifDialog;
import com.hungduy.honghunghospital.FancyGifDialog.FancyGifDialogListener;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.APIService;
import com.hungduy.honghunghospital.Utility.ApiUtils;

public abstract class BaseActivity extends AppCompatActivity {
    protected APIService mAPIService;
    private FirebaseAnalytics mFirebaseAnalytics;
    protected static String preferences = "preferences";
    protected String TAG;
    protected TextView txtTitle;
    protected ImageView btnBack;
    protected String username,password, branchID ,FullName,urlImage,branchName,token;
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
                .setPositiveBtnBackground(R.color.design_default_color_error)
                .setPositiveBtnText("Đồng ý")
                .setGifResource(gif)
                .OnPositiveClicked(okclick)
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
