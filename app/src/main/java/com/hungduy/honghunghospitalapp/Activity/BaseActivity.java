package com.hungduy.honghunghospitalapp.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hungduy.honghunghospitalapp.Database.DAO.BacSiDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.CauHoiKhaiBaoYTeDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.DanTocDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.KetQuaLuuDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.KhuPhoDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.PhuongXaDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.QuanHuyenDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.QuocGiaDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.TinTucDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.TinhThanhDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.UserDataDAO;
import com.hungduy.honghunghospitalapp.Database.LocalDB;
import com.hungduy.honghunghospitalapp.Model.ResponseModel;
import com.hungduy.honghunghospitalapp.R;
import com.hungduy.honghunghospitalapp.Utility.APIService;
import com.hungduy.honghunghospitalapp.Utility.ApiUtils;
import com.hungduy.honghunghospitalapp.Utility.AppConfigString;
import com.hungduy.honghunghospitalapp.Utility.ConnectivityStatusReceiver;
import com.hungduy.honghunghospitalapp.Utility.UtilityHHH;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    protected LocalDB database;
    protected TinhThanhDAO TTdao;
    protected QuocGiaDAO QGdao;
    protected QuanHuyenDAO QHdao;
    protected PhuongXaDAO PXdao;
    protected KhuPhoDAO KPdao;
    protected UserDataDAO USRdao;
    protected CauHoiKhaiBaoYTeDAO KBYTdao;
    protected TinTucDAO tinTucDAO;
    protected BacSiDAO bacSiDAO;
    protected DanTocDAO dantocDAO;
    protected KetQuaLuuDAO ketQuaLuuDAO;
    protected TextView txtOfflineMode;
    protected boolean isConnected;
    protected boolean noibo;
    protected Dialog dialog_loading;
    protected static MainActivity mContext;
    protected Bundle bundle;

    private ConnectivityStatusReceiver connectivityStatusReceiver;


    private LinearLayout btnHome,btnLichBS,btnDichVu,btnTinTuc;
    private ImageView imgHome;

    protected void showDialogLoading(int timedelay){
        dialog_loading.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HideDialogLoading();
            }
        },timedelay);
    }

    protected void HideDialogLoading() {
        if(dialog_loading.isShowing()){
            dialog_loading.dismiss();
        }
    }

    private void CreateDialogLoading() {
        dialog_loading = new Dialog(this);
        dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog_loading.getWindow() != null)
            dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.setCancelable(false);
        dialog_loading.setContentView(R.layout.item_loadding);
        ImageView img = dialog_loading.findViewById(R.id.imgLoading);
        Glide.with(this).load(getDrawable(R.drawable.loading_color)).placeholder(R.drawable.logo_hungduy).into(img);
    }

    public void Disconnect(){
        if(txtOfflineMode != null){
            txtOfflineMode.setVisibility(View.VISIBLE);
        }
        isConnected = false;

    }


    public String getFullname(){
        return FullName;
    }

    public String getToken(){
        return token;
    }

    public void Connected(){
        if(txtOfflineMode != null) {
            mAPIService.ping().enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if(response.isSuccessful() && response.body().getStatus().equals("OK")){
                        txtOfflineMode.setVisibility(View.GONE);
                        isConnected = true;
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {

                }
            });
        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        mAPIService = ApiUtils.getAPIService();// register API services
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        TAG = this.getClass().getSimpleName();
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Intent LoginIntent= getIntent();
        bundle = LoginIntent.getExtras();

        database = LocalDB.getInstance(getApplicationContext());
        TTdao = database.tinhThanhDAO();
        QGdao = database.quocGiaDAO();
        QHdao = database.quanHuyenDAO();
        PXdao = database.phuongXaDAO();
        KPdao = database.khuPhoDAO();
        USRdao = database.userDataDAO();
        KBYTdao = database.kbytdao();
        tinTucDAO = database.tinTucDAO();
        bacSiDAO = database.bacSiDAO();
        dantocDAO = database.danTocDAO();
        ketQuaLuuDAO = database.ketQuaLuuDAO();

        CreateDialogLoading();


        if(bundle!=null)
        {
            try{
                username =(String) bundle.get("username");
                password =(String) bundle.get("password");
                FullName =(String) bundle.get("FullName");
                urlImage =(String) bundle.get("urlImage");
                token =(String) bundle.get("token");
                isConnected =(boolean) bundle.get("connected");
              //  isConnected = (Boolean) BundleLogin.get("isConnected");
            }catch (Exception ex){

            }
        }
        noibo = getBooleanPreferences(preferences,"noibo");
        String currentYearMonth = new SimpleDateFormat("yyyyMM", Locale.getDefault()).format(new Date());
        try {
            APIKey = UtilityHHH.getSha512fromString("HHHApp4P1"+currentYearMonth);
        }catch (Exception ex){
        }
        shape_edittext_error = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.shape_edittext_error);
        shape_edittext_have_focus = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.shape_edittext_have_focus);

        connectivityStatusReceiver = new ConnectivityStatusReceiver();
        connectivityStatusReceiver.setActivity(this);
    }


    public void setMainActivity(MainActivity a){
        this.mContext = a;
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            txtUser = findViewById(R.id.txtUser);
            imgUser = findViewById(R.id.imgUser);

            if(urlImage != null && !urlImage.isEmpty()){
                Picasso.get().load(urlImage).placeholder(R.drawable.user).into(imgUser);
            }else {
                String usernamePreferences = getStringPreferences(preferences,"username");
                String passwordPreferences = getStringPreferences(preferences,"password");
                if(!(usernamePreferences.isEmpty() || passwordPreferences.isEmpty())){
                    try {
                        ContextWrapper cw = new ContextWrapper(getApplicationContext());
                        File directory = cw.getDir(AppConfigString.ImageDIR, Context.MODE_PRIVATE);
                        File UserImage = new File(directory, AppConfigString.UserImageName);
                        Picasso.get().load(UserImage).placeholder(R.drawable.avatar_user_empty).into(imgUser);
                    } catch (Exception ex) {
                        Log.d(TAG,ex.getMessage());
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                String hoten = USRdao.getConfig(AppConfigString.HoTen).getConfigInfo();
                                if(!hoten.isEmpty() && txtUser.getText().toString().equals("*")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            txtUser.setText(hoten);
                                        }
                                    });
                                }
                            }catch (Exception ex){
                                Log.e(TAG,ex.getMessage());
                            }
                        }
                    }).start();
                }

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
        try{
            if(!txtUser.getText().toString().isEmpty() && !txtUser.getText().toString().equals("*") ){
                imgUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(), UpdateUserActivity.class);
                        i.putExtra("FullName",FullName);
                        i.putExtra("urlImage",urlImage);
                        i.putExtra("connected",isConnected);
                        i.putExtra("token",token);
                        startActivity(i);
                    }
                });
            }
        }catch (Exception ex){

        }

        try {
            txtOfflineMode = findViewById(R.id.txtOfflineMode);
        }catch (Exception ex){

        }

        if(!TAG.equals("MainActivity")){
            try {
                btnHome = findViewById(R.id.btnHome);
                btnLichBS = findViewById(R.id.btnLichBS);
                btnDichVu = findViewById(R.id.btnDichVu);
                btnTinTuc = findViewById(R.id.btnTinTuc);
                imgHome = findViewById(R.id.imgHome);
                imgHome.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.homepage_gray));
            }catch (Exception ex){
                Log.d(TAG, ex.getMessage());
            }
            try{
                btnHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.viewNum = 1;
                        mContext.exitToMain = true;
                        finish();
                    }
                });
                btnLichBS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            mContext.viewNum = 2;
                            mContext.exitToMain = true;
                        } catch (Exception e) {
                            Log.d(TAG, e.getMessage());
                        }
                        finish();
                    }
                });
                btnDichVu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            mContext.viewNum = 3;
                            mContext.exitToMain = true;
                        } catch (Exception e) {
                            Log.d(TAG, e.getMessage());
                        }
                        finish();
                    }
                });
                btnTinTuc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            mContext.viewNum = 4;
                            mContext.exitToMain = true;
                        } catch (Exception e) {
                            Log.d(TAG, e.getMessage());
                        }
                        finish();
                    }
                });
            }catch (Exception e){
                Log.d(TAG, e.getMessage());
            }
        }

    }



    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityStatusReceiver, intentFilter);
        try{
            if(mContext.exitToMain){
                finish();
            }
        }catch (Exception ignored){

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connectivityStatusReceiver != null) {
            // unregister receiver
            try{
                unregisterReceiver(connectivityStatusReceiver);
            }catch (Exception ex){

            }
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
