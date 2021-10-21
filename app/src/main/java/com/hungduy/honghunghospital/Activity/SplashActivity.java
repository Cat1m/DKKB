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
import com.hungduy.honghunghospital.ServiceSyncDATA;
import com.hungduy.honghunghospital.Utility.CallbackResponse;
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

        boolean FlagDelALL = getBooleanPreferences(preferences,"FlagDelALL");

        startService(new Intent(this, ServiceSyncDATA.class));

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
                                    if(FlagDelALL){
                                        TTdao.deleteAll();
                                    }
                                    for(int i=0;i< tinhthanh.length;i++){
                                        int ma =Integer.parseInt(tinhthanh[i].getMa());
                                        if(TTdao.getTinhThanh(ma) == null){
                                            try{
                                                TTdao.insert(new TinhThanh(ma,tinhthanh[i].getTen()));
                                            }catch (Exception ex){

                                            }
                                            Log.d(TAG,"Add " + ma);
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
                                    if(FlagDelALL){
                                        QGdao.deleteAll();
                                    }
                                    for(int i=0;i< tinhthanh.length;i++){
                                        int ma =Integer.parseInt(tinhthanh[i].getMa());
                                        if(QGdao.getQuocGia(ma) == null){
                                            try{
                                                QGdao.insert(new QuocGia(Integer.parseInt(tinhthanh[i].getMa()),tinhthanh[i].getTen()));
                                            }catch (Exception ex){

                                            }
                                            Log.d(TAG,"Add " + ma);
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
                                    if(FlagDelALL){
                                        QHdao.deleteAll();
                                    }
                                    for(int i=0;i< quanHuyen.length;i++){
                                        int ma = Integer.parseInt(quanHuyen[i].getMa());
                                        if(QHdao.getQuanHuyen(ma) == null){
                                            try{
                                                QHdao.insert(new QuanHuyen(ma,Integer.parseInt(quanHuyen[i].getMatinhthanh()),quanHuyen[i].getTen()));
                                            }catch (Exception ex){

                                            }
                                            Log.d(TAG,"Add " + ma);
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
                                    if(FlagDelALL){
                                        PXdao.deleteAll();
                                    }
                                    for(int i=0;i< phuongxa.length;i++){
                                        int ma = Integer.parseInt(phuongxa[i].getMa());
                                        if(PXdao.getPhuongXa(ma) == null){
                                            try{
                                                PXdao.insert(new PhuongXa(ma,
                                                        Integer.parseInt(phuongxa[i].getMatinhthanh()),
                                                        Integer.parseInt(phuongxa[i].getMaquanhuyen()),
                                                        phuongxa[i].getTen()));
                                            }catch (Exception ex){
                                            }
                                            Log.d(TAG,"Add " + ma);
                                        }else {

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
            mAPIService.getApKhuPho(APIKey,new baseGetClass("")).enqueue(new CallbackResponse(SplashActivity.this){
                @Override
                public void success(Response<ResponseModel> response) {
                    super.success(response);
                    getApKhuPho[] ApKhuPho = new Gson().fromJson(response.body().getData(),getApKhuPho[].class);
                    if(ApKhuPho.length > 0){
                        Thread a = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if(FlagDelALL){
                                    KPdao.deleteAll();
                                }
                                for(int i=0;i< ApKhuPho.length;i++){
                                    int ma = Integer.parseInt(ApKhuPho[i].getMa());
                                    if(KPdao.getKhuPho(ma) == null){
                                        try{
                                            KPdao.insert(new KhuPho(ma,
                                                    Integer.parseInt(ApKhuPho[i].getMatinhthanh()),
                                                    ApKhuPho[i].getTen()));
                                        }catch (Exception ex){
                                        }
                                        Log.d(TAG,"Add " + ma );
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
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        BreakPoint = true;
                        finish();
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
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.putExtra("OfflineMode",true);
                    startActivity(intent);
                    finish();
                }
                Break.start();
                if(!DataTinhThanh)
                {
                    Log.d(TAG,"1");
                    while(SizeDataTinhThanh == 0){
                        if(BreakPoint) { break;}
                    }
                    while (TTdao.getAll().size() < SizeDataTinhThanh) {
                        if(BreakPoint) { break;}
                    }
                }
                if(!DataQuocGia)
                {
                    Log.d(TAG,"2");
                    while(SizeDataQuocGia == 0){
                        if(BreakPoint) { break;}
                    }
                    while (QGdao.getAll().size() < SizeDataQuocGia ) {
                        if(BreakPoint) { break;}
                    }
                }
                if(!DataQuanHuyen)
                {
                    Log.d(TAG,"3");
                    while(SizeDataQuanHuyen == 0){
                        if(BreakPoint) { break;}
                    }
                    while (QHdao.getAll().size() < SizeDataQuanHuyen) {
                        if(BreakPoint) { break;}
                    }
                }
                if(!DataPhuongXa)
                {
                    Log.d(TAG,"4");
                    while(SizeDataPhuongXa == 0){
                        if(BreakPoint) { break;}
                    }
                    while (PXdao.getAll().size() < SizeDataPhuongXa ) {
                        if(BreakPoint) { break;}
                    }
                }
                if(!DataApKhuPho)
                {
                    Log.d(TAG,"5");
                    while(SizeDataApKhuPho == 0){
                        if(BreakPoint) { break;}
                    }
                    while (KPdao.getAll().size() < SizeDataApKhuPho) {
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
                            intent.putExtra("OfflineMode",false);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });
        getData.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"Tỉnh thành "+TTdao.getAll().size()+" - Quốc gia "+QGdao.getAll().size() + " - Quận huyện " +
                        QHdao.getAll().size() + " - Phường Xã "+PXdao.getAll().size() +" - Ấp Khu Phố "+KPdao.getAll().size());
            }
        }).start();

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
