package com.hungduy.honghunghospitalapp.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hungduy.honghunghospitalapp.Model.ResponseModel;
import com.hungduy.honghunghospitalapp.Model.extModel.getBSCoHinh;
import com.hungduy.honghunghospitalapp.Model.extModel.getListLichLamViecBS;
import com.hungduy.honghunghospitalapp.Model.getModel.baseGetClass;
import com.hungduy.honghunghospitalapp.Model.getModel.getLichLamViecBS;
import com.hungduy.honghunghospitalapp.Model.getModel.getThongTinBS;
import com.hungduy.honghunghospitalapp.R;
import com.hungduy.honghunghospitalapp.Utility.CSJRSpinner.BSSpinner;
import com.hungduy.honghunghospitalapp.Utility.CallbackResponse;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Response;

public class BacSiFragment extends BaseFragment {
    private BSSpinner txtHoTen;
    private ArrayList<getBSCoHinh> listBS;
    private TextView txtInfoBS,txtDoctorName,txtXemThem,textDangNhapXemLich;
    private ConstraintLayout LayoutDoctor;
    private ImageView imgDoctor;
    private RadioButton btnTuanNay,btnTuanSau;
    private getThongTinBS bs;
    private getListLichLamViecBS llvBS;
    private LinearLayout viewLLV;
    private TextView txtT2S,txtT3S,txtT4S,txtT5S,txtT6S,txtT7S,txtT8S,txtT2C,txtT3C,txtT4C,txtT5C,txtT6C,txtT7C,txtT8C;

    public BacSiFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView(view);
        listBS = new ArrayList<>();
        llvBS = new getListLichLamViecBS();

        if(token != null && !token.isEmpty()){
            viewLLV.setVisibility(View.VISIBLE);
            textDangNhapXemLich.setVisibility(View.GONE);
        }

        LayoutDoctor.setVisibility(View.GONE);
        txtXemThem.setVisibility(View.GONE);
        txtHoTen.setOnItemClickListener(new BSSpinner.OnItemClickListener() {
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
                    txtXemThem.setVisibility(View.GONE);
                }else{
                    txtInfoBS.setMaxLines(4);
                    txtXemThem.setVisibility(View.VISIBLE);
                }
            }
        });
        btnTuanSau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnTuanSau.isChecked() && llvBS.getTuanSau() != null){
                    for (getLichLamViecBS llv : llvBS.getTuanSau()) {
                        setLichLamViec(llv);
                    }
                }
            }
        });
        btnTuanNay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnTuanNay.isChecked() && llvBS.getTuannay() != null){
                    for (getLichLamViecBS llv : llvBS.getTuannay()) {
                        setLichLamViec(llv);
                    }
                }
            }
        });

        mAPIService.getAllActiveDoctor(APIKey).enqueue(new CallbackResponse(getActivity()){
            @Override
            public void success(Response<ResponseModel> response) {
                getBSCoHinh[] dsBS = new Gson().fromJson(response.body().getData(),getBSCoHinh[].class);
                if(dsBS.length > 0){
                    for (getBSCoHinh bs: dsBS ) {
                        if(txtHoTen.getText().toString().equals(bs.getTen())){
                            getDetai(bs.getMa());
                        }
                        listBS.add(bs);
                    }
                    txtHoTen.setItems(listBS);
                   // Log.d(TAG,"Nhận DS "+ dsBS.length +" bs");
                }
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if(token != null && !token.isEmpty()){
            viewLLV.setVisibility(View.VISIBLE);
            textDangNhapXemLich.setVisibility(View.GONE);
        }
    }

    private void getDetai(String ma){
        btnTuanNay.setChecked(true);

        mAPIService.getDetailActiveDoctor(APIKey,new baseGetClass(ma)).enqueue(new CallbackResponse(getActivity()){
            @Override
            public void success(Response<ResponseModel> response) {
                if(response.body().getStatus().equals("OK")){
                    LayoutDoctor.setVisibility(View.VISIBLE);
                    bs = new Gson().fromJson(response.body().getData(),getThongTinBS.class);
                    if(bs != null){
                        Picasso.get().load(bs.getHinhAnh()).placeholder(R.drawable.avatar_user_empty).into(imgDoctor);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            txtInfoBS.setText(Html.fromHtml(bs.getThongTin(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            txtInfoBS.setText(Html.fromHtml(bs.getThongTin()));
                        }
                        txtInfoBS.setMaxLines(4);
                        txtXemThem.setVisibility(View.VISIBLE);
                        txtDoctorName.setText(bs.getTenChucDanh() + " " +bs.getTenNhanVien());
                    }
                }else{
                    LayoutDoctor.setVisibility(View.GONE);
                }
            }
        });

        if(token != null && !token.isEmpty()){
            mAPIService.getLichLamViecBS(APIKey,new baseGetClass(ma)).enqueue(new CallbackResponse(getActivity()){

                @Override
                public void success(Response<ResponseModel> response) {
                    getListLichLamViecBS llvs = new Gson().fromJson(response.body().getData(),getListLichLamViecBS.class);
                    if(llvs.getTuannay().size()>0){
                        ArrayList<getLichLamViecBS> a = new ArrayList<>();
                        ArrayList<getLichLamViecBS> b = new ArrayList<>();
                        for (getLichLamViecBS llv : llvs.getTuannay()) {
                            a.add(llv);
                            setLichLamViec(llv);
                        }
                        for (getLichLamViecBS llv : llvs.getTuanSau()) {
                            b.add(llv);
                        }
                        llvBS.setTuannay(a);
                        llvBS.setTuanSau(b);
                    }
                }

            });
        }


    }

    private void setLichLamViec(getLichLamViecBS llv){
        switch (llv.getThu()){
            case 1:
                txtT2S.setText(llv.isSang() ? "Có":"-");
                txtT2C.setText(llv.isChieu() ? "Có":"-");
                break;
            case 2:
                txtT3S.setText(llv.isSang() ? "Có":"-");
                txtT3C.setText(llv.isChieu() ? "Có":"-");
                break;
            case 3:
                txtT4S.setText(llv.isSang() ? "Có":"-");
                txtT4C.setText(llv.isChieu() ? "Có":"-");
                break;
            case 4:
                txtT5S.setText(llv.isSang() ? "Có":"-");
                txtT5C.setText(llv.isChieu() ? "Có":"-");
                break;
            case 5:
                txtT6S.setText(llv.isSang() ? "Có":"-");
                txtT6C.setText(llv.isChieu() ? "Có":"-");
                break;
            case 6:
                txtT7S.setText(llv.isSang() ? "Có":"-");
                txtT7C.setText(llv.isChieu() ? "Có":"-");
                break;
            case 0:
                txtT8S.setText(llv.isSang() ? "Có":"-");
                txtT8C.setText(llv.isChieu() ? "Có":"-");
                break;
        }
    }

    private void mapView(View view) {
        txtHoTen = view.findViewById(R.id.txtNhomDV);
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
        btnTuanNay = view.findViewById(R.id.btnTuanNay);
        btnTuanSau = view.findViewById(R.id.btnTuanSau);
        txtXemThem = view.findViewById(R.id.txtXemThem);
        textDangNhapXemLich = view.findViewById(R.id.textDangNhapXemLich);
        viewLLV = view.findViewById(R.id.viewLLV);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_bac_si, container, false);
    }
}