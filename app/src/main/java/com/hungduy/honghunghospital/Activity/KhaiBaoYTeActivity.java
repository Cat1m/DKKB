package com.hungduy.honghunghospital.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hungduy.honghunghospital.Adapter.KhaiBaoYTeAdapter;
import com.hungduy.honghunghospital.Database.Model.BacSi;
import com.hungduy.honghunghospital.Database.Model.CauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.extModel.CauHoiKhaiBaoYTeEXT;
import com.hungduy.honghunghospital.Model.getModel.getCauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.Model.getModel.getMaTen;
import com.hungduy.honghunghospital.Model.setModel.setDangKyKham;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.CallbackResponse;

import java.util.ArrayList;

import jrizani.jrspinner.JRSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KhaiBaoYTeActivity extends BaseKhaiBaoYTeActivity {
    private RecyclerView recyclerView;
    private KhaiBaoYTeAdapter KhaiBaoYTeADT;
    private Button btnDangKy,btnThoat,btnChonBS,btnChonChuyenKhoa,btnDichVuKhac,btnDuaNguoiThan;
    private ArrayList<getMaTen> listBS;
    private ArrayList<getMaTen> listDMCK;
    private ArrayList<getMaTen> listDV;
    private getMaTen bacSi;
    private getMaTen chuyenKhoa;
    private getMaTen dichVu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khai_bao_yte);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        mapView();
        cauHoiKhaiBaoYTes = new ArrayList<>();
        CauTL = new ArrayList<>();
        listBS = new ArrayList<>();
        listDMCK = new ArrayList<>();
        listDV = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        KhaiBaoYTeADT = new KhaiBaoYTeAdapter(cauHoiKhaiBaoYTes, getApplicationContext(), KhaiBaoYTeActivity.this);
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
                    mAPIService.getCauHoiKBYT(APIKey).enqueue(new CallbackResponse(KhaiBaoYTeActivity.this){
                        @Override
                        public void success(Response<ResponseModel> response) {
                            getCauHoiKhaiBaoYTe[] cauhois = new Gson().fromJson(response.body().getData(), getCauHoiKhaiBaoYTe[].class);
                            if (cauhois.length > 0) {
                                int i = 0;
                                KBYTdao.deleteAll();
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

        btnChonBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog;
                dialog = new Dialog(KhaiBaoYTeActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                if (dialog.getWindow() != null)
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dang_ky_kham);
                TextView title = dialog.findViewById(R.id.title);
                TextView txt1 = dialog.findViewById(R.id.txt1);
                Button negativeBtn = dialog.findViewById(R.id.negativeBtn);
                Button positiveBtn = dialog.findViewById(R.id.positiveBtn);
                JRSpinner txtBS = dialog.findViewById(R.id.txtBS);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(bacSiDAO.getAll().size() > 0){
                            String[] tenBS = new String[bacSiDAO.getAll().size()];
                            int i=0;
                            for(BacSi bs : bacSiDAO.getAll()){
                                listBS.add(new getMaTen(bs.getID()+"", bs.getTen()));
                                tenBS[i] = bs.getTen();
                                i++;
                            }
                            txtBS.setItems(tenBS);
                            Log.d(TAG,"localData");
                        }else{
                            mAPIService.getAllActiveDoctor(APIKey).enqueue(new CallbackResponse(KhaiBaoYTeActivity.this){
                                @Override
                                public void success(Response<ResponseModel> response) {
                                    getMaTen[] dsBS = new Gson().fromJson(response.body().getData(),getMaTen[].class);
                                    if(dsBS.length > 0){
                                        listBS.clear();
                                        String[] tenBS = new String[dsBS.length];
                                        int i=0;
                                        for (getMaTen bs: dsBS ) {
                                            listBS.add(bs);
                                            tenBS[i] = bs.getTen();
                                            i++;
                                        }
                                        txtBS.setItems(tenBS);
                                        Log.d(TAG,"Nhận DS "+ dsBS.length +" bs");
                                    }
                                }
                            });
                        }
                    }
                }).start();
                txtBS.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        bacSi = listBS.get(position);
                        btnChonBS.setText("Chọn bác sĩ "+listBS.get(position).getTen());
                        btnChonBS.setBackground(getResources().getDrawable(R.drawable.btn_shape_green));
                        btnChonBS.setTextColor(getResources().getColor(R.color.white));
                    }
                });
                title.setText("Chọn bác sĩ bạn muốn khám");
                txt1.setText("Bác sĩ");
                negativeBtn.setText("Hủy");
                positiveBtn.setText("Đồng ý");
                negativeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnChonBS.setText(R.string.txt_toi_muon_kham_bac_si);
                        btnChonBS.setBackground(getResources().getDrawable(R.drawable.btn_shape_green_light));
                        btnChonBS.setTextColor(getResources().getColor(R.color.textColorGreen));
                        btnChonChuyenKhoa.setEnabled(true);
                        btnDichVuKhac.setEnabled(true);
                        btnDuaNguoiThan.setEnabled(true);
                        bacSi = null;
                        dialog.dismiss();
                    }
                });
                positiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnChonChuyenKhoa.setEnabled(false);
                        btnDichVuKhac.setEnabled(false);
                        btnDuaNguoiThan.setEnabled(false);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        btnChonChuyenKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog;
                dialog = new Dialog(KhaiBaoYTeActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                if (dialog.getWindow() != null)
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dang_ky_kham);
                TextView title = dialog.findViewById(R.id.title);
                TextView txt1 = dialog.findViewById(R.id.txt1);
                Button negativeBtn = dialog.findViewById(R.id.negativeBtn);
                Button positiveBtn = dialog.findViewById(R.id.positiveBtn);
                JRSpinner txtBS = dialog.findViewById(R.id.txtBS);
                mAPIService.getDmChuyenKhoa(APIKey).enqueue(new CallbackResponse(KhaiBaoYTeActivity.this){
                    @Override
                    public void success(Response<ResponseModel> response) {
                        getMaTen[] dsBS = new Gson().fromJson(response.body().getData(),getMaTen[].class);
                        if(dsBS.length > 0){
                            listDMCK.clear();
                            String[] tenBS = new String[dsBS.length];
                            int i=0;
                            for (getMaTen bs: dsBS ) {
                                listDMCK.add(bs);
                                tenBS[i] = bs.getTen();
                                i++;
                            }
                            txtBS.setItems(tenBS);
                            Log.d(TAG,"Nhận DS "+ dsBS.length +" danh mục chuyên khoa");
                        }
                    }
                });
                txtBS.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        chuyenKhoa = listDMCK.get(position);
                        btnChonChuyenKhoa.setText("Chọn khoa "+listDMCK.get(position).getTen());
                        btnChonChuyenKhoa.setBackground(getResources().getDrawable(R.drawable.btn_shape_green));
                        btnChonChuyenKhoa.setTextColor(getResources().getColor(R.color.white));
                    }
                });
                title.setText("Chọn chuyên khoa bạn muốn khám");
                txt1.setText("Chuyên khoa");
                negativeBtn.setText("Hủy");
                positiveBtn.setText("Đồng ý");
                negativeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnChonChuyenKhoa.setText(R.string.txt_toi_muon_kham_chuyen_khoa);
                        btnChonChuyenKhoa.setBackground(getResources().getDrawable(R.drawable.btn_shape_green_light));
                        btnChonChuyenKhoa.setTextColor(getResources().getColor(R.color.textColorGreen));
                        btnChonBS.setEnabled(true);
                        btnDichVuKhac.setEnabled(true);
                        btnDuaNguoiThan.setEnabled(true);
                        chuyenKhoa = null;
                        dialog.dismiss();
                    }
                });
                positiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnChonBS.setEnabled(false);
                        btnDichVuKhac.setEnabled(false);
                        btnDuaNguoiThan.setEnabled(false);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        btnDichVuKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog;
                dialog = new Dialog(KhaiBaoYTeActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                if (dialog.getWindow() != null)
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dang_ky_kham);
                TextView title = dialog.findViewById(R.id.title);
                TextView txt1 = dialog.findViewById(R.id.txt1);
                Button negativeBtn = dialog.findViewById(R.id.negativeBtn);
                Button positiveBtn = dialog.findViewById(R.id.positiveBtn);
                JRSpinner txtBS = dialog.findViewById(R.id.txtBS);
                mAPIService.getDichVu(APIKey).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if(response.isSuccessful()){
                            if(response.body().getStatus().equals("OK")){
                                getMaTen[] dsBS = new Gson().fromJson(response.body().getData(),getMaTen[].class);
                                if(dsBS.length > 0){
                                    listDV.clear();
                                    String[] tenBS = new String[dsBS.length];
                                    int i=0;
                                    for (getMaTen bs: dsBS ) {
                                        listDV.add(bs);
                                        tenBS[i] = bs.getTen();
                                        i++;
                                    }
                                    txtBS.setItems(tenBS);
                                    Log.d(TAG,"Nhận DS "+ dsBS.length +" danh mục chuyên khoa");
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {

                    }
                });
                txtBS.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        dichVu = listDV.get(position);
                        btnDichVuKhac.setText("Chọn dịch vụ "+listDV.get(position).getTen());
                        btnDichVuKhac.setBackground(getResources().getDrawable(R.drawable.btn_shape_green));
                        btnDichVuKhac.setTextColor(getResources().getColor(R.color.white));
                    }
                });
                title.setText("Chọn dịch vụ bạn muốn");
                txt1.setText("Dịch vụ");
                negativeBtn.setText("Hủy");
                positiveBtn.setText("Đồng ý");
                negativeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnDichVuKhac.setText(R.string.txt_toi_muon_dang_ky_dich_vu_khac);
                        btnDichVuKhac.setBackground(getResources().getDrawable(R.drawable.btn_shape_green_light));
                        btnDichVuKhac.setTextColor(getResources().getColor(R.color.textColorGreen));
                        btnChonBS.setEnabled(true);
                        btnChonChuyenKhoa.setEnabled(true);
                        btnDuaNguoiThan.setEnabled(true);
                        dichVu = null;
                        dialog.dismiss();
                    }
                });
                positiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnChonBS.setEnabled(false);
                        btnChonChuyenKhoa.setEnabled(false);
                        btnDuaNguoiThan.setEnabled(false);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        btnDuaNguoiThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), KhaiBaoYTeNguoiThanActivity.class);
                i.putExtra("FullName",FullName);
                i.putExtra("urlImage",urlImage);
                i.putExtra("token",token);
                startActivity(i);
                finish();
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean found = false;
                int maloaidangky = 0;
                getMaTen madangky = new getMaTen();
                if(bacSi != null){
                    found =true;
                    maloaidangky = 1;
                    madangky = bacSi;
                    madangky.setTen(" bác sĩ: "+bacSi.getTen());
                    Log.d(TAG,bacSi.getTen());
                }
                if(chuyenKhoa != null){
                    found =true;
                    maloaidangky = 2;
                    madangky = chuyenKhoa;
                    Log.d(TAG,chuyenKhoa.getTen());
                }
                if(dichVu != null){
                    found =true;
                    maloaidangky = 3;
                    madangky = dichVu;
                    Log.d(TAG,dichVu.getTen());
                }
                if(found){
                    String khaibaoyte = "";
                    for(CauHoiKhaiBaoYTeEXT c : CauTL){
                        khaibaoyte += c.getCauhoi() +" : " + c.getCautraloi() + "|";
                    }
                    setDangKyKham dkkb = new setDangKyKham("1", khaibaoyte, maloaidangky + "",
                            madangky.getMa(), "", "");
                    final getMaTen dv = madangky;
                    final int maloai = maloaidangky;
                    mAPIService.setDangKyKham(token,dkkb ).enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if(response.isSuccessful()){
                                if(response.body().getStatus().equals("OK")){
                                    boolean kq = false;
                                    for (CauHoiKhaiBaoYTeEXT s : CauTL){
                                        if(s.getCautraloi().equals("Có")){
                                            kq = true;
                                            break;
                                        }
                                    }
                                    if(maloai == 3 && dv.getMa().equals("9") ){
                                        kq=true;
                                    }
                                    Intent i = new Intent(getApplicationContext(), KetQuaActivity.class);
                                    i.putExtra("isTestCovid",kq);
                                    i.putExtra("noidungkham"," Anh/Chị đã đăng ký khám " + dv.getTen() + " <br/> thành công");
                                    i.putExtra("dv",dv.getTen());
                                    i.putExtra("loai",1);
                                    i.putExtra("FullName",FullName);
                                    i.putExtra("urlImage",urlImage);
                                    i.putExtra("QR",response.body().getData());
                                    startActivity(i);
                                    finish();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {

                        }
                    });
                }else{
                    ThongBao(KhaiBaoYTeActivity.this,"Đã có lỗi xảy ra","Vui lòng chọn dịch vụ bạn cần !",R.drawable.searching);
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
        recyclerView = findViewById(R.id.recyclerView);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnChonBS = findViewById(R.id.btnChonBS);
        btnChonChuyenKhoa = findViewById(R.id.btnChonChuyenKhoa);
        btnDichVuKhac = findViewById(R.id.btnDichVuKhac);
        btnDuaNguoiThan = findViewById(R.id.btnDuaNguoiThan);
        btnThoat = findViewById(R.id.btnThoat);
    }


}