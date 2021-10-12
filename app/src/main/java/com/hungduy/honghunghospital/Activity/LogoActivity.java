package com.hungduy.honghunghospital.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hungduy.honghunghospital.R;
import com.squareup.picasso.Picasso;

public class LogoActivity extends BaseActivity {
    private ImageView imageView3;
    private ImageView imgLogoBVHH;
    private ImageView imgUser;
    private TextView txtDiaChi,txtSDT,txtEmail,txtFacebook,txtWebsite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        mapView();
        Picasso.get().load(R.drawable.anh_nhan_vien_bv).into(imageView3);

        imgLogoBVHH = findViewById(R.id.imgLogoBVHH);
        imgLogoBVHH.setEnabled(false);

        imgUser = findViewById(R.id.imgUser);
        imgUser.setEnabled(false);

        txtDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String strUri = "https://goo.gl/maps/Gm4Ce4jmUVtnMtgi7";
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                }catch (Exception exx){

                }
            }
        });

        txtSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "0941696939"));
                startActivity(intent);
            }
        });

        txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+txtEmail.getText().toString()));
                startActivity(intent);
            }
        });

        txtFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                try {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/161426131359201"));
                    startActivity(intent);
                } catch (Exception e) {
                    intent = new Intent(LogoActivity.this,WebviewActivity.class);
                    intent.putExtra("url","https://www.facebook.com/161426131359201");
                    startActivity(intent);
                }
            }
        });

        txtWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogoActivity.this,WebviewActivity.class);
                intent.putExtra("url","https://honghunghospital.com.vn");
                startActivity(intent);
            }
        });
    }

    private void mapView() {
        imageView3 = findViewById(R.id.imageView3);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtSDT = findViewById(R.id.txtSDT);
        txtEmail = findViewById(R.id.txtEmail);
        txtFacebook = findViewById(R.id.txtFacebook);
        txtWebsite = findViewById(R.id.txtWebsite);
    }
}