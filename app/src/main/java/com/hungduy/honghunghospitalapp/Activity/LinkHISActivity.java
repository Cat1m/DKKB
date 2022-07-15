package com.hungduy.honghunghospitalapp.Activity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hungduy.honghunghospitalapp.Adapter.MaBenhNhanAdapter;
import com.hungduy.honghunghospitalapp.Model.ResponseModel;
import com.hungduy.honghunghospitalapp.Model.extModel.getMaBN;
import com.hungduy.honghunghospitalapp.Model.getModel.baseGetClass;
import com.hungduy.honghunghospitalapp.Model.setModel.linkHis;
import com.hungduy.honghunghospitalapp.Model.setModel.searchMaBN;
import com.hungduy.honghunghospitalapp.R;
import com.hungduy.honghunghospitalapp.Utility.CallbackResponse;
import com.hungduy.honghunghospitalapp.Utility.UtilityHHH;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Response;

public class LinkHISActivity extends BaseActivity {
    private TextView txtMaBenhNhan,txtOTP;
    private Button btnGuiOTP,btnLienKet,btnThoat,btnTimMaBenhNhan,btnHuy,btnTim;
    private LinearLayout viewLink;
    private ConstraintLayout viewSearch;
    private EditText edtHoTen,edtNgaySinh,edtSoChungMinh,edtSDT,edtSoBHYT;
    private ArrayList<getMaBN> getMaBNs;
    private MaBenhNhanAdapter maBenhNhanAdapter;
    private RecyclerView viewKQ;
    private final DateFormat dateFormatForUser = new SimpleDateFormat("dd/MM/yyyy");
    private final DateFormat dateFormatForSearch = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_his);
        mapView();
        getMaBNs = new ArrayList<>();
        maBenhNhanAdapter = new MaBenhNhanAdapter(getMaBNs, this, bundle, new MaBenhNhanAdapter.mabenhnhanAdapterOnClick() {
            @Override
            public void onClick(getMaBN bn) {
                if(bn != null){
                    txtMaBenhNhan.setText(bn.getMa());
                    viewLink.setVisibility(View.VISIBLE);
                    viewSearch.setVisibility(View.GONE);
                }
            }
        });
        viewKQ.setAdapter(maBenhNhanAdapter);

        btnGuiOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtMaBenhNhan.getText().toString().isEmpty()){
                    Toast.makeText(LinkHISActivity.this, "Vui lòng nhập mã bệnh nhân!!!", Toast.LENGTH_LONG).show();
                    return;
                }
                mAPIService.sendOTPMaBN(token,new baseGetClass(txtMaBenhNhan.getText().toString())).enqueue(new CallbackResponse(LinkHISActivity.this) {
                    @Override
                    public void success(Response<ResponseModel> response) {
                        Toast.makeText(LinkHISActivity.this, "Gửi thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnLienKet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtMaBenhNhan.getText().toString().isEmpty()){
                    Toast.makeText(LinkHISActivity.this, "Vui lòng nhập mã bệnh nhân!!!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(txtOTP.getText().toString().isEmpty()){
                    Toast.makeText(LinkHISActivity.this, "Vui lòng nhập mã xác nhận!!!", Toast.LENGTH_LONG).show();
                    return;
                }
                mAPIService.setlinkMaBN(token,new linkHis(txtMaBenhNhan.getText().toString(),txtOTP.getText().toString().toUpperCase())).enqueue(new CallbackResponse(LinkHISActivity.this) {
                    @Override
                    public void success(Response<ResponseModel> response) {
                        Toast.makeText(LinkHISActivity.this, response.body().getMessenge(), Toast.LENGTH_SHORT).show();
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
        btnTimMaBenhNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogLoading(1000);
                viewLink.setVisibility(View.GONE);
                viewSearch.setVisibility(View.VISIBLE);
                edtHoTen.setText("");
                edtNgaySinh.setText("");
                edtSoChungMinh.setText("");
                edtSDT.setText("");
                edtSoBHYT.setText("");
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogLoading(1000);
                viewLink.setVisibility(View.VISIBLE);
                viewSearch.setVisibility(View.GONE);
            }
        });
        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogLoading(2000);
                getMaBNs.clear();
                String ngaysinh = edtNgaySinh.getText().toString();
                try {
                    int mYear = UtilityHHH.toInt(ngaysinh.split("/")[2]);
                    int mMonth = UtilityHHH.toInt(ngaysinh.split("/")[1]);
                    int mDay = UtilityHHH.toInt(ngaysinh.split("/")[0]);
                    ngaysinh = mYear + "-";
                    ngaysinh += mMonth < 10 ? "0"+mMonth : mMonth;
                    ngaysinh += "-";
                    ngaysinh += mDay < 10 ? "0"+mDay : mDay;
                }catch (Exception ignored){
                    ngaysinh = "";
                   // Toast.makeText(LinkHISActivity.this, "Ngày sinh không hợp lệ !!!", Toast.LENGTH_SHORT).show();
                }
                mAPIService.searchMaBN(token, new searchMaBN(edtHoTen.getText().toString().toUpperCase(),ngaysinh,
                        edtSoChungMinh.getText().toString(),edtSDT.getText().toString(),edtSoBHYT.getText().toString()))
                        .enqueue(new CallbackResponse(LinkHISActivity.this) {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void success(Response<ResponseModel> response) {
                        try{
                            getMaBN[] s = new Gson().fromJson(response.body().getData(),getMaBN[].class);
                            getMaBNs.addAll(Arrays.asList(s));
                        }catch (Exception ignored){
                        }
                        maBenhNhanAdapter.notifyDataSetChanged();
                        HideDialogLoading();
                    }
                });
            }
        });
    }

    private void mapView() {
        txtMaBenhNhan = findViewById(R.id.txtMaBenhNhan);
        txtOTP = findViewById(R.id.txtOTP);
        btnGuiOTP = findViewById(R.id.btnGuiOTP);
        btnLienKet = findViewById(R.id.btnLienKet);
        btnThoat = findViewById(R.id.btnThoat);
        btnTimMaBenhNhan = findViewById(R.id.btnTimMaBenhNhan);
        viewSearch = findViewById(R.id.viewSearch);
        viewLink = findViewById(R.id.viewLink);
        btnHuy = findViewById(R.id.btnHuy);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtNgaySinh = findViewById(R.id.edtNgaySinh);
        edtSoChungMinh = findViewById(R.id.edtSoChungMinh);
        edtSDT = findViewById(R.id.edtSDT);
        edtSoBHYT = findViewById(R.id.edtSoBHYT);
        btnTim = findViewById(R.id.btnTim);
        viewKQ = findViewById(R.id.view);
    }
}