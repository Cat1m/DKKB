package com.hungduy.honghunghospitalapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hungduy.honghunghospitalapp.Adapter.LichSuKhamAdapter;
import com.hungduy.honghunghospitalapp.Model.ResponseModel;
import com.hungduy.honghunghospitalapp.Model.getModel.getLichSuKham;
import com.hungduy.honghunghospitalapp.Model.getModel.getMaTen;
import com.hungduy.honghunghospitalapp.Model.setModel.setLichSuKham;
import com.hungduy.honghunghospitalapp.R;
import com.hungduy.honghunghospitalapp.Utility.CallbackResponse;
import com.hungduy.honghunghospitalapp.Utility.UtilityHHH;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import jrizani.jrspinner.JRSpinner;
import retrofit2.Response;

public class TraCuuActivity extends BaseActivity {
    private Button btnLienKet,btnTimKiem;
    private EditText edtTuNgay,edtDenNgay;
    private JRSpinner spMaBenhNhan;
    private ArrayList<getMaTen> hosokham;
    private String mahoSo="";
    private ArrayList<getLichSuKham> lichSuKhams;
    private RecyclerView view;
    private LichSuKhamAdapter lichSuKhamAdapter;
    private Calendar calendar = Calendar.getInstance();
    private Date today = calendar.getTime();
    private DateFormat dateFormatForUser = new SimpleDateFormat("dd/MM/yyyy");
    private Date ThreeMonth;
    private int vitri = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_cuu);
        mapView();
        hosokham = new ArrayList<>();
        lichSuKhams = new ArrayList<>();
        Calendar x = Calendar.getInstance();
        x.add(Calendar.MONTH,-1);
        ThreeMonth = x.getTime();
        btnLienKet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TraCuuActivity.this,LinkHISActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        mapFunction();
        lichSuKhamAdapter = new LichSuKhamAdapter(lichSuKhams,this,bundle);
        view.setAdapter(lichSuKhamAdapter);


    }

    @SuppressLint("NotifyDataSetChanged")
    private void mapFunction() {
        spMaBenhNhan.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                vitri = position;
                try{
                    mahoSo = hosokham.get(position).getMa();
                }catch (Exception ignored){  }
            }
        });

        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mahoSo.isEmpty()){
                    Toast.makeText(TraCuuActivity.this, "Vui lòng chọn hồ sơ", Toast.LENGTH_SHORT).show();
                    return;
                }
                showDialogLoading(15000);
                setLichSuKham x = new setLichSuKham(edtTuNgay.getText().toString(),edtDenNgay.getText().toString(),mahoSo);
                mAPIService.getLichSuKhamBenh(token,x)
                        .enqueue(new CallbackResponse(TraCuuActivity.this) {
                    @Override
                    public void success(Response<ResponseModel> response) {
                        lichSuKhams.clear();
                        try{
                            getLichSuKham[] lsk = new Gson().fromJson(response.body().getData(),getLichSuKham[].class);
                            if(lsk.length > 0){
                                lichSuKhams.addAll(Arrays.asList(lsk));
                            }
                        }catch (Exception ignored){

                        }
                        lichSuKhamAdapter.notifyDataSetChanged();
                        HideDialogLoading();
                    }
                });
            }
        });
        edtTuNgay.setText(dateFormatForUser.format(today));
        UtilityHHH.edtDate(getSupportFragmentManager(),edtTuNgay,false);
        edtDenNgay.setText(dateFormatForUser.format(ThreeMonth));
        UtilityHHH.edtDate(getSupportFragmentManager(),edtDenNgay,false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAPIService.getListLink(token).enqueue(new CallbackResponse(this) {
            @Override
            public void success(Response<ResponseModel> response) {
                try{
                    getMaTen[] list = new Gson().fromJson(response.body().getData(),getMaTen[].class);
                    hosokham.clear();
                    spMaBenhNhan.clear();
                    if(list.length > 0){
                        String[] hs = new String[list.length];
                        for(int i=0;i<list.length;i++){
                            hosokham.add(list[i]);
                            hs[i] = list[i].getMa() + " - "+ list[i].getTen();
                        }
                        spMaBenhNhan.setItems(hs);
                        if(vitri > list.length -1){
                            spMaBenhNhan.select(vitri);
                        }else{
                            vitri = 0;
                            spMaBenhNhan.select(0);
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(TraCuuActivity.this, "Đã có lỗi xảy ra "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void mapView() {
        btnLienKet = findViewById(R.id.btnLienKet);
        btnTimKiem = findViewById(R.id.btnTimKiem);
        spMaBenhNhan = findViewById(R.id.spMaBenhNhan);
        edtTuNgay = findViewById(R.id.edtTuNgay);
        edtDenNgay = findViewById(R.id.edtDenNgay);
        view = findViewById(R.id.viewLink);
    }
}