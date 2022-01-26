package com.hungduy.honghunghospital.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hungduy.honghunghospital.Adapter.KhaiBaoYTeAdapter;
import com.hungduy.honghunghospital.Database.Model.CauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.extModel.CauHoiKhaiBaoYTeEXT;
import com.hungduy.honghunghospital.Model.getModel.getCauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.Model.getModel.getMaTen;
import com.hungduy.honghunghospital.Model.setModel.setKhaiBao;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.CallbackResponse;

import java.util.ArrayList;

import jrizani.jrspinner.JRSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KhaiBaoYTeNoiBoActivity extends BaseKhaiBaoYTeActivity {
    private RecyclerView recyclerView;
    private KhaiBaoYTeAdapter KhaiBaoYTeADT;
    private Button btnDangKy,btnThoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khai_bao_yte_noi_bo);

        mapView();
        cauHoiKhaiBaoYTes = new ArrayList<>();
        CauTL = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        KhaiBaoYTeADT = new KhaiBaoYTeAdapter(cauHoiKhaiBaoYTes, getApplicationContext(), KhaiBaoYTeNoiBoActivity.this);
        recyclerView.setAdapter(KhaiBaoYTeADT);
        recyclerView.setLayoutManager(linearLayoutManager);

        new Thread(new Runnable() {
            @Override
            public void run() {
                cauHoiKhaiBaoYTes.clear();
                CauTL.clear();
                if (KBYTdao.getAll().size() > 0) {
                    for(CauHoiKhaiBaoYTe ch : KBYTdao.getAll()){
                        cauHoiKhaiBaoYTes.add(new getCauHoiKhaiBaoYTe(ch.getID()+"",ch.getCauHoi(),""));
                        CauTL.add(new CauHoiKhaiBaoYTeEXT(new getCauHoiKhaiBaoYTe(ch.getID()+"",ch.getCauHoi(),""), "Không"));
                    }
                    KhaiBaoYTeADT.notifyDataSetChanged();
                    Log.d(TAG,"Data local !!!");
                } else {
                    mAPIService.getCauHoiKBYT(APIKey).enqueue(new CallbackResponse(KhaiBaoYTeNoiBoActivity.this){
                        @Override
                        public void success(Response<ResponseModel> response) {
                            getCauHoiKhaiBaoYTe[] cauhois = new Gson().fromJson(response.body().getData(), getCauHoiKhaiBaoYTe[].class);
                            if (cauhois.length > 0) {
                                int i = 0;
                                for (getCauHoiKhaiBaoYTe a : cauhois) {
                                    cauHoiKhaiBaoYTes.add(a);
                                    CauTL.add(new CauHoiKhaiBaoYTeEXT(a, "Không"));
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                KBYTdao.insert(new CauHoiKhaiBaoYTe(Integer.parseInt(a.getMa()), a.getCauhoi()));
                                            }catch (Exception ex){
                                            }
                                        }
                                    }).start();
                                }
                            }
                            KhaiBaoYTeADT.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();




        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean kq = false;
                String khaibaoyte = "";
                for(CauHoiKhaiBaoYTeEXT c : CauTL){
                    khaibaoyte += c.getCauhoi() +" : " + c.getCautraloi() + "|";
                    if(c.getCautraloi().equals("Có")){
                        kq = true;
                    }
                }
                boolean finalKq = kq;
                mAPIService.setKhaiBao(token,new setKhaiBao("3",khaibaoyte,""))
                        .enqueue(new CallbackResponse(KhaiBaoYTeNoiBoActivity.this){
                            @Override
                            public void success(Response<ResponseModel> response) {
                                Intent i = new Intent(getApplicationContext(), KetQuaActivity.class);
                                i.putExtra("isTestCovid", finalKq);
                                i.putExtra("noidungkham"," Anh/Chị đã khai báo y tế thành công");
                                i.putExtra("FullName",FullName);
                                i.putExtra("urlImage",urlImage);
                                i.putExtra("QR",response.body().getData());
                                i.putExtra("loai",4);
                                startActivity(i);
                                finish();
                            }
                        });
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void mapView() {
        recyclerView = findViewById(R.id.recyclerView);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnThoat = findViewById(R.id.btnThoat);
    }


}