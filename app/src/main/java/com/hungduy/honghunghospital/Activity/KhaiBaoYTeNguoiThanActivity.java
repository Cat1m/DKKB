package com.hungduy.honghunghospital.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hungduy.honghunghospital.Adapter.KhaiBaoYTeAdapter;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.extModel.CauHoiKhaiBaoYTeEXT;
import com.hungduy.honghunghospital.Model.getModel.getCauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KhaiBaoYTeNguoiThanActivity extends BaseKhaiBaoYTeActivity {
    private RecyclerView recyclerView;
    private KhaiBaoYTeAdapter KhaiBaoYTeADT;
    private Button btnDangKy,btnThoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khai_bao_yte_nguoi_than);

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
                            KhaiBaoYTeADT = new KhaiBaoYTeAdapter(cauHoiKhaiBaoYTes,getApplicationContext(), KhaiBaoYTeNguoiThanActivity.this);
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