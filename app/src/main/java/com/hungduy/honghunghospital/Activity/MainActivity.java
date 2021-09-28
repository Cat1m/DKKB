package com.hungduy.honghunghospital.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hungduy.honghunghospital.Fragment.LoginFragment;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.FragmentUtils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class MainActivity extends BaseActivity {
    private LinearLayout btnHome,btnLichBS,btnDichVu,btnTinTuc;
    private ImageView imgHome,imgLichBS,imgDichVu,imgTinTuc;
    private ScrollView svTrangChu;

    Bitmap homepage_gray;
    Bitmap doctor_gray;
    Bitmap services_gray;
    Bitmap information_gray;
    Bitmap homepage_color;
    Bitmap doctor_color;
    Bitmap services_color;
    Bitmap information_color;

    private int selected = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView();
        btnMainClick();

        LoginFragment loginFM = new LoginFragment();
        FragmentUtils.addFragmentToLayout(R.id.svTrangChu,getSupportFragmentManager(),loginFM,"");






        initButtonImage();// load Image to memory for quick response



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
                clearBtnColor();
                imgHome.setImageBitmap(homepage_color);
                imgHome.setTag("1");
            }
        });
        btnLichBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearBtnColor();
                imgLichBS.setImageBitmap(doctor_color);
                imgLichBS.setTag("1");
            }
        });
        btnDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
