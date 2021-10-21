package com.hungduy.honghunghospital.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hungduy.honghunghospital.R;
import com.squareup.picasso.Picasso;

public class LogoActivity extends BaseActivity {
    private ImageView imageView3;
    private ImageView imgWeb,imgFacebook,imgYoutube,imgZalo;
    private ImageView imgLogoBVHH,imgUser;
    private TextView txtDiaChi,txtSDT,txtEmail;
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
                    Intent intent = new Intent(LogoActivity.this,WebviewActivity.class);
                    intent.putExtra("url","https://goo.gl/maps/Gm4Ce4jmUVtnMtgi7");
                    startActivity(intent);
                    finish();
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

        imgFacebook.setOnClickListener(new View.OnClickListener() {
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
                    finish();
                }
            }
        });

        imgWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogoActivity.this,WebviewActivity.class);
                intent.putExtra("url","https://honghunghospital.com.vn");
                startActivity(intent);
                finish();
            }
        });

        imgYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String youtubeURL = "https://www.youtube.com/channel/UClJptrMqBjA_q3iRNuvPdNg";
                Intent youtubeIntent=null;
                try {
                    youtubeIntent=new Intent(Intent.ACTION_VIEW);
                    youtubeIntent.setPackage("com.google.android.youtube");
                    youtubeIntent.setData(Uri.parse(youtubeURL ));
                    startActivity(youtubeIntent);
                } catch (Exception e) {
                    youtubeIntent = new Intent(LogoActivity.this,WebviewActivity.class);
                    youtubeIntent.putExtra("url",youtubeURL);
                    startActivity(youtubeIntent);
                    finish();
                }
            }
        });

        imgZalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                String url = "https://zalo.me/1959767744609917092";
                try {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setPackage("com.zing.zalo");
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }catch (Exception ex){
                    Toast.makeText(LogoActivity.this,"Bạn chưa cài zalo!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void mapView() {
        imageView3 = findViewById(R.id.imageView3);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtSDT = findViewById(R.id.txtSDT);
        txtEmail = findViewById(R.id.txtEmail);
        imgWeb = findViewById(R.id.imgWeb);
        imgFacebook = findViewById(R.id.imgFacebook);
        imgYoutube = findViewById(R.id.imgYoutube);
        imgZalo = findViewById(R.id.imgZalo);
    }
}