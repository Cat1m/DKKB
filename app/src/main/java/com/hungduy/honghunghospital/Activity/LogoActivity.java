package com.hungduy.honghunghospital.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.hungduy.honghunghospital.R;
import com.squareup.picasso.Picasso;

public class LogoActivity extends BaseActivity {
    private ImageView imageView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        mapView();
        Picasso.get().load(R.drawable.anh_nhan_vien_bv).into(imageView3);
    }

    private void mapView() {
        imageView3 = findViewById(R.id.imageView3);
    }

}