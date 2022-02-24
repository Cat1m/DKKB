package com.hungduy.honghunghospital.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hungduy.honghunghospital.Adapter.ChiTietDichVuSDAdapter;
import com.hungduy.honghunghospital.Adapter.ChiTietToaAdapter;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.getModel.baseGetClass;
import com.hungduy.honghunghospital.Model.getModel.getChiTietToa;
import com.hungduy.honghunghospital.Model.getModel.getDichVuSD;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.CallbackResponse;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Response;

public class ChiTietDichVuSDActivity extends BaseActivity {
    private RecyclerView view;
    private ChiTietDichVuSDAdapter chiTietDichVuSDAdapter;
    private ArrayList<getDichVuSD> getDichVuSDS;
    private Button btnThoat;
    private String makham = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_toa_thuoc);
        mapView();
        try{
            makham = bundle.getString("makham");
        }catch (Exception e){
            Toast.makeText(this, "Đã xảy ra lỗi: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
        getDichVuSDS = new ArrayList<>();
        chiTietDichVuSDAdapter = new ChiTietDichVuSDAdapter(getDichVuSDS,this,bundle);
        view.setAdapter(chiTietDichVuSDAdapter);
        mAPIService.getDichVuSuDung(token,new baseGetClass(makham)).enqueue(new CallbackResponse(this) {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void success(Response<ResponseModel> response) {
                try{
                    getDichVuSD[] ctt = new Gson().fromJson(response.body().getData(),getDichVuSD[].class);
                    if(ctt != null){
                        getDichVuSDS.addAll(Arrays.asList(ctt));
                    }
                    chiTietDichVuSDAdapter.notifyDataSetChanged();
                }catch (Exception ignored){
                }
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
        view = findViewById(R.id.view);
        btnThoat = findViewById(R.id.btnThoat);
    }
}