package com.hungduy.honghunghospital.Activity;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.hungduy.honghunghospital.Database.DAO.KhuPhoDAO;
import com.hungduy.honghunghospital.Database.DAO.PhuongXaDAO;
import com.hungduy.honghunghospital.Database.DAO.QuanHuyenDAO;
import com.hungduy.honghunghospital.Database.DAO.QuocGiaDAO;
import com.hungduy.honghunghospital.Database.DAO.TinhThanhDAO;
import com.hungduy.honghunghospital.Database.LocalDB;
import com.hungduy.honghunghospital.Database.Model.KhuPho;
import com.hungduy.honghunghospital.Database.Model.PhuongXa;
import com.hungduy.honghunghospital.Database.Model.QuanHuyen;
import com.hungduy.honghunghospital.Database.Model.QuocGia;
import com.hungduy.honghunghospital.Database.Model.TinhThanh;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.getModel.baseGetClass;
import com.hungduy.honghunghospital.Model.getModel.getApKhuPho;
import com.hungduy.honghunghospital.Model.getModel.getMaTen;
import com.hungduy.honghunghospital.Model.getModel.getPhuongXa;
import com.hungduy.honghunghospital.Model.getModel.getQuanHuyen;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.UtilityHHH;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.squareup.picasso.Picasso;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    boolean DataTinhThanh,DataQuocGia,DataQuanHuyen,DataPhuongXa,DataApKhuPho;
    int SizeDataTinhThanh,SizeDataQuocGia,SizeDataQuanHuyen,SizeDataPhuongXa,SizeDataApKhuPho;
    private ImageView imgLogo;
    boolean BreakPoint = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        imgLogo = findViewById(R.id.imgLogo);
        imgLogo.setVisibility(View.VISIBLE);
        Glide.with(this).load(getDrawable(R.drawable.loading_color)).placeholder(R.drawable.logo_hungduymedical).into(imgLogo);

        TinhThanhDAO TTdao = database.tinhThanhDAO();
        QuocGiaDAO QGdao = database.quocGiaDAO();
        QuanHuyenDAO QHdao = database.quanHuyenDAO();
        PhuongXaDAO PXdao = database.phuongXaDAO();
        KhuPhoDAO KPdao = database.khuPhoDAO();

        DataTinhThanh = getBooleanPreferences(preferences,"DataTinhThanh");
        DataQuocGia = getBooleanPreferences(preferences,"DataQuocGia");
        DataQuanHuyen = getBooleanPreferences(preferences,"DataQuanHuyen");
        DataPhuongXa = getBooleanPreferences(preferences,"DataQuanHuyen");
        DataApKhuPho = getBooleanPreferences(preferences,"DataApKhuPho");


        if(!DataTinhThanh){
            mAPIService.getTinhThanh(APIKey).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if(response.isSuccessful() && response.body().getStatus().equals("OK")){
                        getMaTen[] tinhthanh = new Gson().fromJson(response.body().getData(),getMaTen[].class);
                        if(tinhthanh.length > 0){
                            Thread a = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    TTdao.deleteAll();
                                    for(int i=0;i< tinhthanh.length;i++){
                                        try{
                                            TTdao.insert(new TinhThanh(Integer.parseInt(tinhthanh[i].getMa()),tinhthanh[i].getTen()));
                                        }catch (Exception ex){

                                        }
                                    }
                                    SizeDataTinhThanh = tinhthanh.length;
                                    if(TTdao.getAll().size() == tinhthanh.length){
                                        setBooleanPreferences(preferences,"DataTinhThanh",true);
                                    }
                                }
                            });
                            a.start();
                        }

                    }else{

                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {

                }
            });
        }

        if(!DataQuocGia){
            mAPIService.getQuocGia(APIKey).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if(response.isSuccessful() && response.body().getStatus().equals("OK")){
                        getMaTen[] tinhthanh = new Gson().fromJson(response.body().getData(),getMaTen[].class);
                        if(tinhthanh.length > 0){
                            Thread a = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    QGdao.deleteAll();
                                    for(int i=0;i< tinhthanh.length;i++){
                                        try{
                                            QGdao.insert(new QuocGia(Integer.parseInt(tinhthanh[i].getMa()),tinhthanh[i].getTen()));
                                        }catch (Exception ex){

                                        }
                                    }
                                    SizeDataQuocGia = tinhthanh.length;
                                    if(QGdao.getAll().size() == tinhthanh.length){
                                        setBooleanPreferences(preferences,"DataQuocGia",true);
                                    }
                                }
                            });
                            a.start();
                        }
                    }else{

                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {

                }
            });
        }

        if(!DataQuanHuyen){
            mAPIService.getQuanHuyen(APIKey,new baseGetClass("")).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if(response.isSuccessful() && response.body().getStatus().equals("OK")){
                        getQuanHuyen[] quanHuyen = new Gson().fromJson(response.body().getData(),getQuanHuyen[].class);
                        if(quanHuyen.length > 0){
                            Thread a = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    QHdao.deleteAll();
                                    for(int i=0;i< quanHuyen.length;i++){
                                        try{
                                            QHdao.insert(new QuanHuyen(Integer.parseInt(quanHuyen[i].getMa()),
                                                    Integer.parseInt(quanHuyen[i].getMatinhthanh()),quanHuyen[i].getTen()));
                                        }catch (Exception ex){

                                        }
                                    }
                                    SizeDataQuanHuyen = quanHuyen.length;
                                    if(QHdao.getAll().size() == quanHuyen.length){
                                        setBooleanPreferences(preferences,"DataQuanHuyen",true);
                                    }
                                }
                            });
                            a.start();
                        }
                    }else{

                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {

                }
            });
        }

        if(!DataPhuongXa){
            mAPIService.getPhuongXa(APIKey,new baseGetClass("")).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if(response.isSuccessful() && response.body().getStatus().equals("OK")){
                        getPhuongXa[] phuongxa = new Gson().fromJson(response.body().getData(),getPhuongXa[].class);
                        if(phuongxa.length > 0){
                            Thread a = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    PXdao.deleteAll();
                                    for(int i=0;i< phuongxa.length;i++){
                                        try{
                                            PXdao.insert(new PhuongXa(Integer.parseInt(phuongxa[i].getMa()),
                                                    Integer.parseInt(phuongxa[i].getMatinhthanh()),
                                                    Integer.parseInt(phuongxa[i].getMaquanhuyen()),
                                                    phuongxa[i].getTen()));
                                        }catch (Exception ex){

                                        }
                                    }
                                    SizeDataPhuongXa = phuongxa.length;
                                    if(PXdao.getAll().size() == phuongxa.length){
                                        setBooleanPreferences(preferences,"DataPhuongXa",true);
                                    }
                                }
                            });
                            a.start();
                        }
                    }else{

                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {

                }
            });
        }

        if(!DataApKhuPho){
            mAPIService.getApKhuPho(APIKey,new baseGetClass("")).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if(response.isSuccessful() && response.body().getStatus().equals("OK")){
                        getApKhuPho[] ApKhuPho = new Gson().fromJson(response.body().getData(),getApKhuPho[].class);
                        if(ApKhuPho.length > 0){
                            Thread a = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    KPdao.deleteAll();
                                    for(int i=0;i< ApKhuPho.length;i++){
                                        try{
                                            KPdao.insert(new KhuPho(Integer.parseInt(ApKhuPho[i].getMa()),
                                                    Integer.parseInt(ApKhuPho[i].getMatinhthanh()),
                                                    ApKhuPho[i].getTen()));
                                        }catch (Exception ex){

                                        }
                                    }
                                    SizeDataApKhuPho = ApKhuPho.length;
                                    if(KPdao.getAll().size() == ApKhuPho.length){
                                        setBooleanPreferences(preferences,"DataApKhuPho",true);
                                    }
                                }
                            });
                            a.start();
                        }
                    }else{

                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {

                }
            });
        }

             /*   dao.insert(new TinhThanh(UUID.randomUUID().toString(), UUID.randomUUID().toString()));*/
        Thread Break = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    if(!BreakPoint){
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    new FancyGifDialog.Builder(SplashActivity.this)
                                            .setTitle("Đã có lỗi xảy ra")
                                            .setMessage("Không kết nối được với máy chủ. " +
                                                    "Bạn có muốn tiếp tục với chế độ offline")
                                            .setPositiveBtnBackground("#FF4081")
                                            .setPositiveBtnText("Đồng ý")
                                            .setNegativeBtnBackground("#FF4081")
                                            .setNegativeBtnText("Hủy")
                                            .setGifResource( R.drawable.connection_error)
                                            .OnPositiveClicked(new FancyGifDialogListener() {
                                                @Override
                                                public void OnClick() {
                                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    BreakPoint = true;
                                                    finish();
                                                }
                                            })
                                            .OnNegativeClicked(new FancyGifDialogListener() {
                                                @Override
                                                public void OnClick() {
                                                    BreakPoint = true;
                                                    finish();
                                                }
                                            })
                                            .build();
                                }catch (Exception ex){
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread getData = new Thread(new Runnable() {
            @Override
            public void run() {
                if(!UtilityHHH.isInternetAvailable()){
                    BreakPoint = true;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            new FancyGifDialog.Builder(SplashActivity.this)
                                    .setTitle("Đã có lỗi xảy ra")
                                    .setMessage("Không kết nối được với máy chủ. " +
                                            "Bạn có muốn tiếp tục với chế độ offline")
                                    .setPositiveBtnBackground("#FF4081")
                                    .setPositiveBtnText("Đồng ý")
                                    .setNegativeBtnBackground("#FF4081")
                                    .setNegativeBtnText("Hủy")
                                    .setGifResource( R.drawable.connection_error)
                                    .OnPositiveClicked(new FancyGifDialogListener() {
                                        @Override
                                        public void OnClick() {
                                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .OnNegativeClicked(new FancyGifDialogListener() {
                                        @Override
                                        public void OnClick() {
                                            finish();
                                        }
                                    })
                                    .build();

                        }
                    });
                }
                Break.start();
                if(!DataTinhThanh)
                {
                    Log.d(TAG,"1");
                    while(SizeDataTinhThanh == 0){
                        if(BreakPoint) { break;}
                    }
                    while (TTdao.getAll().size() != SizeDataTinhThanh) {
                        if(BreakPoint) { break;}
                    }
                }
                if(!DataQuocGia)
                {
                    Log.d(TAG,"2");
                    while(SizeDataQuocGia == 0){
                        if(BreakPoint) { break;}
                    }
                    while (QGdao.getAll().size() != SizeDataQuocGia ) {
                        if(BreakPoint) { break;}
                    }
                }
                if(!DataQuanHuyen)
                {
                    Log.d(TAG,"3");
                    while(SizeDataQuanHuyen == 0){
                        if(BreakPoint) { break;}
                    }
                    while (QHdao.getAll().size() != SizeDataQuanHuyen) {
                        if(BreakPoint) { break;}
                    }
                }
                if(!DataPhuongXa)
                {
                    Log.d(TAG,"4");
                    while(SizeDataPhuongXa == 0){
                        if(BreakPoint) { break;}
                    }
                    while (PXdao.getAll().size() != SizeDataPhuongXa ) {
                        if(BreakPoint) { break;}
                    }
                }
                if(!DataApKhuPho)
                {
                    Log.d(TAG,"5");
                    while(SizeDataApKhuPho == 0){
                        if(BreakPoint) { break;}
                    }
                    while (KPdao.getAll().size() != SizeDataApKhuPho) {
                        if(BreakPoint) { break;}
                    }
                }
                Log.d(TAG,"Tỉnh thành "+TTdao.getAll().size()+" - Quốc gia "+QGdao.getAll().size() + " - Quận huyện " +
                        QHdao.getAll().size() + " - Phường Xã "+PXdao.getAll().size() +" - Ấp Khu Phố "+KPdao.getAll().size());
                if(!BreakPoint){
                    BreakPoint = true;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });
        getData.start();



        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        String token = task.getResult();
                        Log.d(TAG, token);
                    }
                });

    }

}
