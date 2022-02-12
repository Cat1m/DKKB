package com.hungduy.honghunghospital.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.hungduy.honghunghospital.Database.Model.UserData;
import com.hungduy.honghunghospital.Fragment.BacSiFragment;
import com.hungduy.honghunghospital.Fragment.BaseFragment;
import com.hungduy.honghunghospital.Fragment.DichVuFragment;
import com.hungduy.honghunghospital.Fragment.HomeLoginedFragment;
import com.hungduy.honghunghospital.Fragment.LoginFragment;
import com.hungduy.honghunghospital.Fragment.ThongTinFragment;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.getModel.getUser;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.ServiceSyncDATA;
import com.hungduy.honghunghospital.Utility.AppConfigString;
import com.hungduy.honghunghospital.Utility.CallbackResponse;
import com.hungduy.honghunghospital.Utility.FragmentUtils;
import com.hungduy.honghunghospital.Utility.UtilityHHH;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    private LinearLayout btnHome,btnLichBS,btnDichVu,btnTinTuc;
    private ImageView imgHome,imgLichBS,imgDichVu,imgTinTuc;
    private LinearLayout svTrangChu;

    Bitmap homepage_gray;
    Bitmap doctor_gray;
    Bitmap services_gray;
    Bitmap information_gray;
    Bitmap homepage_color;
    Bitmap doctor_color;
    Bitmap services_color;
    Bitmap information_color;

    private int selected = 1;
    private LoginFragment loginFM;
    private BacSiFragment BacSiFM;
    private DichVuFragment DichVuFM;
    private ThongTinFragment ThongTinFM;

    private boolean Connected = true;
    public int viewNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mapView();
        btnMainClick();
        super.setMainActivity(this);


        loginFM = new LoginFragment();
        BacSiFM = new BacSiFragment();
        DichVuFM = new DichVuFragment();
        ThongTinFM = new ThongTinFragment();

        String usernamePreferences = getStringPreferences(preferences,"username");
        String passwordPreferences = getStringPreferences(preferences,"password");
        if(!(usernamePreferences.isEmpty() || passwordPreferences.isEmpty())){
            HomeLoginedFragment logined = new HomeLoginedFragment();
            bundle.putString("FullName", FullName);
            bundle.putString("urlImage", urlImage);
            bundle.putString("token", token);
            logined.setArguments(bundle);
            FragmentUtils.addFragmentToLayout(R.id.svTrangChu,getSupportFragmentManager(),logined,"");
            svTrangChu.setBackgroundColor(getResources().getColor(R.color.ColorGreenLight));
            logined.Logined(false);
        }else{
            FragmentUtils.addFragmentToLayout(R.id.svTrangChu,getSupportFragmentManager(),loginFM,"");
        }

        try{
            String url = bundle.getString("url");
            if(url.contains("https")){
                Intent i = new Intent(this,WebviewActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }else{

            }
        }catch (Exception e){

        }

        initButtonImage();// load Image to memory for quick response
    }

    public void LoginSuccess(String token, String fullname, String urlImage){
        this.token = token;
        this.FullName = fullname;
        this.urlImage = urlImage;
        svTrangChu.setBackgroundColor(getResources().getColor(R.color.ColorGreenLight));
        Bundle bundle = new Bundle();
        bundle.putString("FullName", fullname);
        bundle.putString("urlImage", urlImage);
        bundle.putString("token", token);
        BacSiFM.setArguments(bundle);
        DichVuFM.setArguments(bundle);
        ThongTinFM.setArguments(bundle);
        Log.d(TAG,"Login successs");

        new Thread(new Runnable() {
            @Override
            public void run() {
                mAPIService.getUserbyToken(token).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful() && response.body().getStatus().equals("OK")) {
                            getUser usr = new Gson().fromJson(response.body().getData(), getUser.class);
                            if (usr != null) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        USRdao.insert(new UserData(AppConfigString.Username, usr.getUsername()));
                                        USRdao.insert(new UserData(AppConfigString.HinhAnh, usr.getHinhAnh()));
                                        USRdao.insert(new UserData(AppConfigString.HoTen, usr.getHoTen()));
                                        USRdao.insert(new UserData(AppConfigString.NgaySinh, usr.getNgaySinh()));
                                        USRdao.insert(new UserData(AppConfigString.GioiTinh, usr.getGioiTinh() + ""));
                                        USRdao.insert(new UserData(AppConfigString.MaTinh, usr.getMaTinh() + ""));
                                        USRdao.insert(new UserData(AppConfigString.MaHuyen, usr.getMaHuyen() + ""));
                                        USRdao.insert(new UserData(AppConfigString.MaPhuongXa, usr.getMaPhuongXa() + ""));
                                        USRdao.insert(new UserData(AppConfigString.MaApKhuPho, usr.getMaApKhuPho() + ""));
                                        USRdao.insert(new UserData(AppConfigString.SoNha, usr.getSoNha()));
                                        USRdao.insert(new UserData(AppConfigString.QuocTich, usr.getQuocTich() + ""));
                                        USRdao.insert(new UserData(AppConfigString.HoChieu, usr.getHoChieu()));
                                        USRdao.insert(new UserData(AppConfigString.MaTheBHYT, usr.getMaTheBHYT()));
                                        USRdao.insert(new UserData(AppConfigString.HinhBHYT, usr.getHinhBHYT()));
                                        USRdao.insert(new UserData(AppConfigString.Token, token));
                                        USRdao.insert(new UserData(AppConfigString.DanToc,usr.getDanToc()));
                                    }
                                }).start();
                                if (!usr.getHinhAnh().isEmpty()) {
                                    Picasso.get().load(usr.getHinhAnh()).into(
                                            UtilityHHH.picassoImageTarget(getApplicationContext(), AppConfigString.ImageDIR
                                                    , AppConfigString.UserImageName));
                                }
                                if (!usr.getHinhBHYT().isEmpty()) {
                                    Picasso.get().load(usr.getHinhBHYT()).into(
                                            UtilityHHH.picassoImageTarget(getApplicationContext(), AppConfigString.ImageDIR
                                                    , AppConfigString.BHYTImageName));
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {

                    }
                });


            }
        }).start();

    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Nhấn nút quay lại một lần nữa đển thoát", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public void Connected() {
        super.Connected();
        if(getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)){
            if(token == null || token.isEmpty()){
                String usernamePreferences = getStringPreferences(preferences,"username");
                String passwordPreferences = getStringPreferences(preferences,"password");
                if(!(usernamePreferences.isEmpty() && passwordPreferences.isEmpty())){
                    FragmentUtils.addFragmentToLayout(R.id.svTrangChu,getSupportFragmentManager(),loginFM,"");
                    mAPIService.ping().enqueue(new CallbackResponse(MainActivity.this){
                        @Override
                        public void success(Response<ResponseModel> response) {
                            loginFM.RetryLogin();
                            clearBtnColor();
                            imgHome.setImageBitmap(homepage_color);
                            imgHome.setTag("1");
                            Connected = true;
                        }
                    });
                }
            }else {
                try {
                    Connected = true;
                    BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag("");
                    if (baseFragment != null) {
                        baseFragment.Connected();
                    }
                } catch (Exception ex) {
                    Log.e(TAG, ex.getMessage());
                }
            }
        }
    }

    @Override
    public void Disconnect() {
        super.Disconnect();
        Connected = false;
        try{
            BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag("");
            if(baseFragment != null){
                baseFragment.Disconect();
            }
        }catch (Exception ex){
            Log.e(TAG,ex.getMessage());
        }

    }

    private void initButtonImage() {
        homepage_gray = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.homepage_gray);
        doctor_gray = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.doctor_gray);
        services_gray = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.services_gray);
        information_gray = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.information_gray);
        homepage_color = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.homepage_color);
        doctor_color = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.doctor_color);
        services_color = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.services_color);
        information_color = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.information_color);
        imgHome.setTag("1");
        imgLichBS.setTag("0");
        imgDichVu.setTag("0");
        imgTinTuc.setTag("0");
    }

    private void setSvTrangChu(int a){
        switch (a){
            case 1 :
                btnHome.callOnClick();
                viewNum = 0;
                break;
            case 2 :
                btnLichBS.callOnClick();
                viewNum = 0;
                break;
            case 3 :
                btnDichVu.callOnClick();
                viewNum = 0;
                break;
            case 4 :
                btnTinTuc.callOnClick();
                viewNum = 0;
                break;
            default:
                viewNum = 0;
                break;
        }
    }


    private void clearBtnColor(){
        if(imgHome.getTag().toString().equals("1")){
            imgHome.setImageBitmap(homepage_gray);
            imgHome.setTag("0");
        }
        if(imgLichBS.getTag().toString().equals("1")){
            imgLichBS.setImageBitmap(doctor_gray);
            imgLichBS.setTag("0");
        }
        if(imgDichVu.getTag().toString().equals("1")){
            imgDichVu.setImageBitmap(services_gray);
            imgDichVu.setTag("0");
        }
        if(imgTinTuc.getTag().toString().equals("1")){
            imgTinTuc.setImageBitmap(information_gray);
            imgTinTuc.setTag("0");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        String usernamePreferences = getStringPreferences(preferences,"username");
        String passwordPreferences = getStringPreferences(preferences,"password");
        if(usernamePreferences.isEmpty() || passwordPreferences.isEmpty()) {
            if(imgHome.getTag().equals("1")) {
                svTrangChu.setBackgroundColor(getResources().getColor(R.color.white));
            }
        }else{
            svTrangChu.setBackgroundColor(getResources().getColor(R.color.ColorGreenLight));
        }
        setSvTrangChu(viewNum);
    }

    private void btnMainClick() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernamePreferences = getStringPreferences(preferences,"username");
                String passwordPreferences = getStringPreferences(preferences,"password");
                if(usernamePreferences.isEmpty() || passwordPreferences.isEmpty()){
                    svTrangChu.setBackgroundColor(getResources().getColor(R.color.white));
                    FragmentUtils.replaceFragment(R.id.svTrangChu,getSupportFragmentManager(),loginFM,"");
                }else{
                    svTrangChu.setBackgroundColor(getResources().getColor(R.color.ColorGreenLight));
                    HomeLoginedFragment logined = new HomeLoginedFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("FullName", FullName);
                    bundle.putString("urlImage", urlImage);
                    bundle.putString("token", token);
                    bundle.putBoolean("OfflineMode",!Connected);
                    Log.d(TAG, "onClick: connection " + Connected);
                    logined.setArguments(bundle);
                    FragmentUtils.replaceFragment(R.id.svTrangChu,getSupportFragmentManager(),logined,"");
                }
                clearBtnColor();
                imgHome.setImageBitmap(homepage_color);
                imgHome.setTag("1");
            }
        });
        btnLichBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FragmentUtils.replaceFragment(R.id.svTrangChu, getSupportFragmentManager(), BacSiFM, "");
                    svTrangChu.setBackgroundColor(getResources().getColor(R.color.ColorGreenLight));
                    clearBtnColor();
                    imgLichBS.setImageBitmap(doctor_color);
                    imgLichBS.setTag("1");
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Có lỗi xảy ra. Vui lòng thử lại!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FragmentUtils.replaceFragment(R.id.svTrangChu, getSupportFragmentManager(), DichVuFM, "");
                    svTrangChu.setBackgroundColor(getResources().getColor(R.color.ColorGreenLight));
                    clearBtnColor();
                    imgDichVu.setImageBitmap(services_color);
                    imgDichVu.setTag("1");
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Có lỗi xảy ra. Vui lòng thử lại!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnTinTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    FragmentUtils.replaceFragment(R.id.svTrangChu,getSupportFragmentManager(),ThongTinFM,"");
                    svTrangChu.setBackgroundColor(getResources().getColor(R.color.ColorGreenLight));
                    clearBtnColor();
                    imgTinTuc.setImageBitmap(information_color);
                    imgTinTuc.setTag("1");
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Có lỗi xảy ra. Vui lòng thử lại!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void mapView() {
        btnHome = findViewById(R.id.btnHome);
        btnLichBS = findViewById(R.id.btnLichBS);
        btnDichVu = findViewById(R.id.btnDichVu);
        btnTinTuc = findViewById(R.id.btnTinTuc);
        imgHome = findViewById(R.id.imgHome);
        imgLichBS = findViewById(R.id.imgLichBS);
        imgDichVu = findViewById(R.id.imgDichVu);
        imgTinTuc = findViewById(R.id.imgTinTuc);
        svTrangChu = findViewById(R.id.svTrangChu);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Intent serviceIntent = new Intent(this, ServiceSyncDATA.class);
        startService(serviceIntent);

    }


}
