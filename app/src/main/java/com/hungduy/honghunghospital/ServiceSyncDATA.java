package com.hungduy.honghunghospital;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.hungduy.honghunghospital.Activity.KhaiBaoYTeActivity;
import com.hungduy.honghunghospital.Activity.SplashActivity;
import com.hungduy.honghunghospital.Adapter.KhaiBaoYTeAdapter;
import com.hungduy.honghunghospital.Database.DAO.BacSiDAO;
import com.hungduy.honghunghospital.Database.DAO.CauHoiKhaiBaoYTeDAO;
import com.hungduy.honghunghospital.Database.DAO.KhuPhoDAO;
import com.hungduy.honghunghospital.Database.DAO.PhuongXaDAO;
import com.hungduy.honghunghospital.Database.DAO.QuanHuyenDAO;
import com.hungduy.honghunghospital.Database.DAO.QuocGiaDAO;
import com.hungduy.honghunghospital.Database.DAO.TinTucDAO;
import com.hungduy.honghunghospital.Database.DAO.TinhThanhDAO;
import com.hungduy.honghunghospital.Database.DAO.UserDataDAO;
import com.hungduy.honghunghospital.Database.LocalDB;
import com.hungduy.honghunghospital.Database.Model.BacSi;
import com.hungduy.honghunghospital.Database.Model.CauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.Database.Model.KhuPho;
import com.hungduy.honghunghospital.Database.Model.TinTuc;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.extModel.CauHoiKhaiBaoYTeEXT;
import com.hungduy.honghunghospital.Model.getModel.baseGetClass;
import com.hungduy.honghunghospital.Model.getModel.getApKhuPho;
import com.hungduy.honghunghospital.Model.getModel.getCauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.Model.getModel.getMaTen;
import com.hungduy.honghunghospital.Model.getModel.getTinTuc;
import com.hungduy.honghunghospital.Utility.APIService;
import com.hungduy.honghunghospital.Utility.ApiUtils;
import com.hungduy.honghunghospital.Utility.CallbackResponse;
import com.hungduy.honghunghospital.Utility.UtilityHHH;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceSyncDATA extends Service {
    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;
    private APIService mAPIService;
    private String APIKey;
    private LocalDB database;
    private TinhThanhDAO TTdao;
    private QuocGiaDAO QGdao;
    private QuanHuyenDAO QHdao;
    private PhuongXaDAO PXdao;
    private KhuPhoDAO KPdao;
    private UserDataDAO USRdao;
    private CauHoiKhaiBaoYTeDAO KBYTdao;
    private TinTucDAO tinTucDAO;
    private BacSiDAO bacSiDAO;
    private static String TAG = "ServiceSyncDATA";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
       // Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();

      /*  handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                Toast.makeText(context, "Service is still running", Toast.LENGTH_LONG).show();
                handler.postDelayed(runnable, 10000);
            }
        };

        handler.postDelayed(runnable, 15000);*/


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


        mAPIService = ApiUtils.getAPIService();// register API services
        //service Update Location run one
        String currentYearMonth = new SimpleDateFormat("yyyyMM", Locale.getDefault()).format(new Date());
        try {
            APIKey = UtilityHHH.getSha512fromString("HHHApp4P1"+currentYearMonth);
        }catch (Exception ex){
        }
        mAPIService.getCauHoiKBYT(APIKey).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful() && response.body().getStatus().equals("OK")) {
                    getCauHoiKhaiBaoYTe[] cauhois = new Gson().fromJson(response.body().getData(), getCauHoiKhaiBaoYTe[].class);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (cauhois.length > KBYTdao.getAll().size() && cauhois.length > 0) {
                                KBYTdao.deleteAll();
                                for (getCauHoiKhaiBaoYTe a : cauhois) {
                                    KBYTdao.insert(new CauHoiKhaiBaoYTe(Integer.parseInt(a.getMa()), a.getCauhoi()));
                                }
                                Log.d(TAG,"Get kbyt");
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });

        mAPIService.getTinTuc(APIKey).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful() && response.body().getStatus().equals("OK")) {
                    getTinTuc[] tinTucs = new Gson().fromJson(response.body().getData(), getTinTuc[].class);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (tinTucs.length > tinTucDAO.getAll().size() && tinTucs.length > 0) {
                                tinTucDAO.deleteAll();
                                for (getTinTuc a : tinTucs) {
                                    tinTucDAO.insert(new TinTuc(a.getMa(), a.getTen(), a.getUrl(), a.getMota()));
                                }
                                Log.d(TAG,"Get data Tin Tuc");
                            }
                        }
                    }).start();

                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });

        mAPIService.getAllActiveDoctor(APIKey).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful() && response.body().getStatus().equals("OK")){
                    getMaTen[] bacSi = new Gson().fromJson(response.body().getData(), getMaTen[].class);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (bacSi.length > bacSiDAO.getAll().size() && bacSi.length > 0) {
                                bacSiDAO.deleteAll();
                                for (getMaTen a : bacSi) {
                                    bacSiDAO.insert(new BacSi(Integer.parseInt(a.getMa()),a.getTen()));
                                }
                                Log.d(TAG,"Get data bac si");
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);
        //Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

}
