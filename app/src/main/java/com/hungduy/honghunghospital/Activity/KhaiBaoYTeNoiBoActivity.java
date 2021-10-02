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
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.extModel.CauHoiKhaiBaoYTeEXT;
import com.hungduy.honghunghospital.Model.getModel.getCauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.Model.getModel.getMaTen;
import com.hungduy.honghunghospital.R;

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
        mAPIService.getCauHoiKBYT(APIKey).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("OK")){
                        getCauHoiKhaiBaoYTe[] cauhois = new Gson().fromJson(response.body().getData(),getCauHoiKhaiBaoYTe[].class);
                        if(cauhois.length > 0){
                            int i=0;
                            for (getCauHoiKhaiBaoYTe a: cauhois) {
                                cauHoiKhaiBaoYTes.add(a);
                                CauTL.add(new CauHoiKhaiBaoYTeEXT(a,"Không"));
                            }
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            KhaiBaoYTeADT = new KhaiBaoYTeAdapter(cauHoiKhaiBaoYTes,getApplicationContext(), KhaiBaoYTeNoiBoActivity.this);
                            recyclerView.setAdapter(KhaiBaoYTeADT);
                            recyclerView.setLayoutManager(linearLayoutManager);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });




        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean kq = false;
                String cautraloi = "";
                for (CauHoiKhaiBaoYTeEXT s : CauTL){
                    cautraloi += ",{ m: '"+s.getMa()+"' ,c : '"+ s.getCautraloi() +"'}";
                    if(s.getCautraloi().equals("Có")){
                        kq = true;
                    }
                }
                Intent i = new Intent(getApplicationContext(), KetQuaActivity.class);
                i.putExtra("isTestCovid",kq);
                i.putExtra("noidungkham"," Anh/Chị đã khai báo y tế thành công");
                i.putExtra("FullName",FullName);
                i.putExtra("urlImage",urlImage);
                i.putExtra("QR","{ obj:'NB' ,kbyt:["+cautraloi.substring(1)+"]}");
                startActivity(i);
                finish();
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