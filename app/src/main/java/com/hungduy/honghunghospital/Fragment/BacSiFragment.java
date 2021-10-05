package com.hungduy.honghunghospital.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hungduy.honghunghospital.Activity.LogoActivity;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.getModel.baseGetClass;
import com.hungduy.honghunghospital.Model.getModel.getLichLamViecBS;
import com.hungduy.honghunghospital.Model.getModel.getMaTen;
import com.hungduy.honghunghospital.Model.getModel.getThongTinBS;
import com.hungduy.honghunghospital.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import jrizani.jrspinner.JRSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BacSiFragment extends BaseFragment {
    private JRSpinner txtHoTen;
    private ArrayList<getMaTen> listBS;
    private TextView txtInfoBS,txtDoctorName;
    private ConstraintLayout LayoutDoctor;
    private ImageView imgDoctor;

    private getThongTinBS bs;
    private ArrayList<getLichLamViecBS> llvBS;

    private TextView txtT2S,txtT3S,txtT4S,txtT5S,txtT6S,txtT7S,txtT8S,txtT2C,txtT3C,txtT4C,txtT5C,txtT6C,txtT7C,txtT8C;

    public BacSiFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    public String getSafeSubstring(String s, int maxLength){
        if(!TextUtils.isEmpty(s)){
            if(s.length() >= maxLength){
                return s.substring(0, maxLength);
            }
        }
        return s;
    }
    @Override
    public void onResume() {
        super.onResume();
        mAPIService.getAllActiveDoctor(APIKey).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("OK")){
                        getMaTen[] dsBS = new Gson().fromJson(response.body().getData(),getMaTen[].class);
                        if(dsBS.length > 0){
                            String[] tenBS = new String[dsBS.length];
                            int i=0;
                            for (getMaTen bs: dsBS ) {
                                if(txtHoTen.getText().toString().equals(bs.getTen())){
                                    getDetai(bs.getMa());
                                }
                                listBS.add(bs);
                                tenBS[i] = bs.getTen();
                                i++;
                            }
                            txtHoTen.setItems(tenBS);
                            Log.d(TAG,"Nhận DS "+ dsBS.length +" bs");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView(view);
        listBS = new ArrayList<>();
        llvBS = new ArrayList<>();

        LayoutDoctor.setVisibility(View.GONE);
        txtHoTen.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getDetai(listBS.get(position).getMa());
            }
        });
        txtInfoBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtInfoBS.getMaxLines() == 4){
                    txtInfoBS.setMaxLines(50);
                }else{
                    txtInfoBS.setMaxLines(4);
                }
            }
        });
    }

    private void getDetai(String ma){
        mAPIService.getDetailActiveDoctor(APIKey,new baseGetClass(ma)).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("OK")){
                        LayoutDoctor.setVisibility(View.VISIBLE);
                        bs = new Gson().fromJson(response.body().getData(),getThongTinBS.class);
                        if(bs != null){
                            Picasso.get().load(bs.getHinhAnh()).placeholder(R.drawable.avatar_user_empty).into(imgDoctor);
                            String minStringHtml;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                txtInfoBS.setText(Html.fromHtml(bs.getThongTin(), Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                txtInfoBS.setText(Html.fromHtml(bs.getThongTin()));
                            }
                            txtInfoBS.setMaxLines(4);
                            txtDoctorName.setText(bs.getTenChucDanh() + " " +bs.getTenNhanVien());
                        }
                    }else{
                        LayoutDoctor.setVisibility(View.GONE);
                        ThongBao(getActivity(),"Đã có lỗi xảy ra",response.body().getMessenge(),R.drawable.connection_error);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });

        mAPIService.getLichLamViecBS(APIKey,new baseGetClass(ma)).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("OK")){
                        getLichLamViecBS[] llvs = new Gson().fromJson(response.body().getData(),getLichLamViecBS[].class);
                        if(llvs.length>0){
                            for (getLichLamViecBS llv : llvs) {
                                llvBS.add(llv);
                                if(llv.getThu() == 1){
                                    txtT2S.setText(llv.isSang() ? "Có":"");
                                    txtT2C.setText(llv.isChieu() ? "Có":"");
                                }
                                if(llv.getThu() == 2){
                                    txtT3S.setText(llv.isSang() ? "Có":"");
                                    txtT3C.setText(llv.isChieu() ? "Có":"");
                                }
                                if(llv.getThu() == 3){
                                    txtT4S.setText(llv.isSang() ? "Có":"");
                                    txtT4C.setText(llv.isChieu() ? "Có":"");
                                }
                                if(llv.getThu() == 4){
                                    txtT5S.setText(llv.isSang() ? "Có":"");
                                    txtT5C.setText(llv.isChieu() ? "Có":"");
                                }
                                if(llv.getThu() == 5){
                                    txtT6S.setText(llv.isSang() ? "Có":"");
                                    txtT6C.setText(llv.isChieu() ? "Có":"");
                                }
                                if(llv.getThu() == 6){
                                    txtT7S.setText(llv.isSang() ? "Có":"");
                                    txtT7C.setText(llv.isChieu() ? "Có":"");
                                }
                                if(llv.getThu() == 0){
                                    txtT8S.setText(llv.isSang() ? "Có":"");
                                    txtT8C.setText(llv.isChieu() ? "Có":"");
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }



    private void mapView(View view) {
        txtHoTen = view.findViewById(R.id.txtHoTen);
        LayoutDoctor = view.findViewById(R.id.LayoutDoctor);
        imgDoctor = view.findViewById(R.id.imgDoctor);
        txtInfoBS = view.findViewById(R.id.txtInfoBS);
        txtDoctorName = view.findViewById(R.id.txtDoctorName);
        txtT2S = view.findViewById(R.id.txtT2S);
        txtT3S = view.findViewById(R.id.txtT3S);
        txtT4S  = view.findViewById(R.id.txtT4S);
        txtT5S  = view.findViewById(R.id.txtT5S);
        txtT6S = view.findViewById(R.id.txtT6S);
        txtT7S = view.findViewById(R.id.txtT7S);
        txtT8S = view.findViewById(R.id.txtT8S);
        txtT2C = view.findViewById(R.id.txtT2C);
        txtT3C = view.findViewById(R.id.txtT3C);
        txtT4C = view.findViewById(R.id.txtT4C);
        txtT5C = view.findViewById(R.id.txtT5C);
        txtT6C = view.findViewById(R.id.txtT6C);
        txtT7C = view.findViewById(R.id.txtT7C);
        txtT8C = view.findViewById(R.id.txtT8C);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_bac_si, container, false);
    }
}