package com.hungduy.honghunghospital.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.hungduy.honghunghospital.Adapter.KhaiBaoYTeAdapter;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.extModel.CauHoiKhaiBaoYTeEXT;
import com.hungduy.honghunghospital.Model.getModel.getCauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.Model.getModel.getMaTen;
import com.hungduy.honghunghospital.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KhaiBaoYTeActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private KhaiBaoYTeAdapter KhaiBaoYTeADT;
    private ArrayList<getCauHoiKhaiBaoYTe> cauHoiKhaiBaoYTes;
    private ArrayList<CauHoiKhaiBaoYTeEXT> CauTL;

    public void updateCauTraLoi(CauHoiKhaiBaoYTeEXT tl){
        for (CauHoiKhaiBaoYTeEXT a : CauTL ) {
            if(a.getMa().equals(tl.getMa()) && !a.getCautraloi().equals(tl.getCautraloi())){
                a.setCautraloi(tl.getCautraloi());
                //Log.d(TAG,tl.getCauhoi() + " - "+tl.getCautraloi());
                break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khai_bao_yte);

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
                            KhaiBaoYTeADT = new KhaiBaoYTeAdapter(cauHoiKhaiBaoYTes,getApplicationContext(),KhaiBaoYTeActivity.this);
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
    }

    private void mapView() {
        recyclerView = findViewById(R.id.recyclerView);
    }


}