package com.hungduy.honghunghospital.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hungduy.honghunghospital.Adapter.ChiTietToaAdapter;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.getModel.baseGetClass;
import com.hungduy.honghunghospital.Model.getModel.getChiTietToa;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.CallbackResponse;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Response;

public class ChiTietToaThuocActivity extends BaseActivity {
    private RecyclerView view;
    private ChiTietToaAdapter chiTietToaAdapter;
    private ArrayList<getChiTietToa> chiTietToas;
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
        chiTietToas = new ArrayList<>();
        chiTietToaAdapter = new ChiTietToaAdapter(chiTietToas,this,bundle);
        view.setAdapter(chiTietToaAdapter);
        mAPIService.getChiTietToaThuoc(token,new baseGetClass(makham)).enqueue(new CallbackResponse(this) {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void success(Response<ResponseModel> response) {
                try{
                    getChiTietToa[] ctt = new Gson().fromJson(response.body().getData(),getChiTietToa[].class);
                    if(ctt != null){
                        chiTietToas.addAll(Arrays.asList(ctt));
                    }
                    chiTietToaAdapter.notifyDataSetChanged();
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