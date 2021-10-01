package com.hungduy.honghunghospital.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hungduy.honghunghospital.Fragment.BacSiFragment;
import com.hungduy.honghunghospital.Fragment.BaseFragment;
import com.hungduy.honghunghospital.Fragment.DichVuFragment;
import com.hungduy.honghunghospital.Fragment.HomeLoginedFragment;
import com.hungduy.honghunghospital.Fragment.LoginFragment;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.FragmentUtils;
import com.hungduy.honghunghospital.Utility.QLCVScrollView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView();
        btnMainClick();

        loginFM = new LoginFragment();
        BacSiFM = new BacSiFragment();
        DichVuFM = new DichVuFragment();
        FragmentUtils.addFragmentToLayout(R.id.svTrangChu,getSupportFragmentManager(),loginFM,"");

        initButtonImage();// load Image to memory for quick response



    }

    public void LoginSuccess(String token,String fullname,String urlImage){
        this.token = token;
        this.FullName = fullname;
        this.urlImage = urlImage;
        Bundle bundle = new Bundle();
        bundle.putString("FullName", fullname);
        bundle.putString("urlImage", urlImage);
        bundle.putString("token", token);
        BacSiFM.setArguments(bundle);
        DichVuFM.setArguments(bundle);
        Log.d(TAG,"Login successs");
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


    private void btnMainClick() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(token.isEmpty()){
                    svTrangChu.setBackgroundColor(getResources().getColor(R.color.white));
                    FragmentUtils.replaceFragment(R.id.svTrangChu,getSupportFragmentManager(),loginFM,"");
                }else{
                    HomeLoginedFragment logined = new HomeLoginedFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("FullName", FullName);
                    bundle.putString("urlImage", urlImage);
                    bundle.putString("token", token);
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
                FragmentUtils.replaceFragment(R.id.svTrangChu,getSupportFragmentManager(),BacSiFM,"");
                svTrangChu.setBackgroundColor(getResources().getColor(R.color.ColorGreenLight));
                clearBtnColor();
                imgLichBS.setImageBitmap(doctor_color);
                imgLichBS.setTag("1");
            }
        });
        btnDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentUtils.replaceFragment(R.id.svTrangChu,getSupportFragmentManager(),DichVuFM,"");
                svTrangChu.setBackgroundColor(getResources().getColor(R.color.ColorGreenLight));
                clearBtnColor();
                imgDichVu.setImageBitmap(services_color);
                imgDichVu.setTag("1");
            }
        });
        btnTinTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearBtnColor();
                imgTinTuc.setImageBitmap(information_color);
                imgTinTuc.setTag("1");
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


}
