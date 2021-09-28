package com.hungduy.honghunghospital.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.hungduy.honghunghospital.Fragment.LoginFragment;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.FragmentUtils;
import com.squareup.picasso.Picasso;

public class MainActivity extends BaseActivity {
    private LinearLayout btnHome,btnLichBS,btnDichVu,btnTinTuc;
    private ImageView imgHome,imgLichBS,imgDichVu,imgTinTuc;
    private ScrollView svTrangChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView();
        btnMainClick();

        LoginFragment loginFM = new LoginFragment();





        FragmentUtils.addFragmentToLayout(R.id.svTrangChu,getSupportFragmentManager(),loginFM,"");
    }

    private void clearBtnColor(){
        Picasso.get().load(R.drawable.homepage_gray).into(imgHome);
        Picasso.get().load(R.drawable.doctor_gray).into(imgLichBS);
        Picasso.get().load(R.drawable.services_gray).into(imgDichVu);
        Picasso.get().load(R.drawable.information_gray).into(imgTinTuc);
    }

    private void btnMainClick() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearBtnColor();
                Picasso.get().load(R.drawable.homepage_color).into(imgHome);
            }
        });
        btnLichBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearBtnColor();
                Picasso.get().load(R.drawable.doctor_color).into(imgLichBS);
            }
        });
        btnDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearBtnColor();
                Picasso.get().load(R.drawable.services_color).into(imgDichVu);
            }
        });
        btnTinTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearBtnColor();
                Picasso.get().load(R.drawable.information_color).into(imgTinTuc);
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
