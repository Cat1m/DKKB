package com.hungduy.honghunghospital.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hungduy.honghunghospital.Adapter.KhaiBaoYTeAdapter;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.extModel.CauHoiKhaiBaoYTeEXT;
import com.hungduy.honghunghospital.Model.getModel.baseGetClass;
import com.hungduy.honghunghospital.Model.getModel.getCauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.Model.getModel.getMaTen;
import com.hungduy.honghunghospital.Model.setModel.setDangKyKham;
import com.hungduy.honghunghospital.Model.setModel.setNguoiThanDangKyKham;
import com.hungduy.honghunghospital.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import jrizani.jrspinner.JRSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KhaiBaoYTeNguoiThanActivity extends BaseKhaiBaoYTeActivity {
    private RecyclerView recyclerView;
    private KhaiBaoYTeAdapter KhaiBaoYTeADT;
    private Button btnDangKy,btnThoat,btnChonBS,btnChonChuyenKhoa,btnDichVuKhac;
    private JRSpinner txtTinhThanh,txtQuanHuyen,txtXaPhuong,txtApKhuPho;
    private TextView txtNgaySinh,txtThangSinh,txtNamSinh;
    private ArrayList<getMaTen> listBS;
    private ArrayList<getMaTen> listDMCK;
    private ArrayList<getMaTen> listDV;
    private getMaTen bacSi;
    private getMaTen chuyenKhoa;
    private getMaTen dichVu;
    private String matinhthanh = "";
    private String maquanhuyen = "";
    private String maphuongxa = "";
    private String maapkhupho = "";
    private String maquoctich = "";

    private EditText txtHoTen,txtDiaChi,txtMaBHYT;

    private String gioitinh = "0";
    private RadioButton chkNam,chkNu,chkKhac;

    private ArrayList<getMaTen> listTinhThanh  = new ArrayList<>();
    private ArrayList<getMaTen> listQuanHuyen  = new ArrayList<>();
    private ArrayList<getMaTen> listPhuongXa  = new ArrayList<>();
    private ArrayList<getMaTen> listApKhuPho  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khai_bao_yte_nguoi_than);

        mapView();
        cauHoiKhaiBaoYTes = new ArrayList<>();
        CauTL = new ArrayList<>();
        listBS = new ArrayList<>();
        listDMCK = new ArrayList<>();
        listDV = new ArrayList<>();

        txtTinhThanh.setItems(new String[0]);
        txtQuanHuyen.setItems(new String[0]);
        txtXaPhuong.setItems(new String[0]);
        txtApKhuPho.setItems(new String[0]);

        btnChonBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog;
                dialog = new Dialog(KhaiBaoYTeNguoiThanActivity.this);
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
                mAPIService.getAllActiveDoctor(APIKey).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if(response.isSuccessful()){
                            if(response.body().getStatus().equals("OK")){
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
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {

                    }
                });
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
                        bacSi = null;
                        dialog.dismiss();
                    }
                });
                positiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnChonChuyenKhoa.setEnabled(false);
                        btnDichVuKhac.setEnabled(false);
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
                dialog = new Dialog(KhaiBaoYTeNguoiThanActivity.this);
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
                mAPIService.getDmChuyenKhoa(APIKey).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if(response.isSuccessful()){
                            if(response.body().getStatus().equals("OK")){
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
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {

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
                        chuyenKhoa = null;
                        dialog.dismiss();
                    }
                });
                positiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnChonBS.setEnabled(false);
                        btnDichVuKhac.setEnabled(false);
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
                dialog = new Dialog(KhaiBaoYTeNguoiThanActivity.this);
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
                        dichVu = null;
                        dialog.dismiss();
                    }
                });
                positiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnChonBS.setEnabled(false);
                        btnChonChuyenKhoa.setEnabled(false);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

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

        mAPIService.getTinhThanh(APIKey).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("OK")){
                        getMaTen[] g = new Gson().fromJson(response.body().getData(), getMaTen[].class);
                        if(g.length>0){
                            listTinhThanh = new ArrayList<getMaTen>(Arrays.asList(g));
                            Log.d(TAG,"Nhận "+ listTinhThanh.size()+" tỉnh thành");
                            String[] s = new String[listTinhThanh.size()];
                            int i=0;
                            for (getMaTen a: listTinhThanh) {
                                s[i]=a.getTen();
                                i++;
                            }
                            txtTinhThanh.setItems(s);
                            return;
                        }
                    }
                }else{
                    //code != 200
                }
                ThongBao(KhaiBaoYTeNguoiThanActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error);
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                ThongBao(KhaiBaoYTeNguoiThanActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error);
            }
        });

        txtTinhThanh.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String code = listTinhThanh.get(position).getMa();
                if(!matinhthanh.equals(code)){
                    matinhthanh = code;
                    txtQuanHuyen.setItems(new String[0]);
                    txtXaPhuong.setItems(new String[0]);
                    txtApKhuPho.setItems(new String[0]);
                    txtQuanHuyen.setText("----");
                    txtXaPhuong.setText("----");
                    txtApKhuPho.setText("----");
                    maquanhuyen = "";
                    maphuongxa = "";
                    maapkhupho = "";
                    DoDuLieuQuanHuyen(code);
                    Log.d(TAG,  matinhthanh +" - " + listTinhThanh.get(position).getTen());
                }
            }
        });

        txtQuanHuyen.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String code = listQuanHuyen.get(position).getMa();
                if(!maquanhuyen.equals(code)){
                    maquanhuyen = code;
                    txtXaPhuong.setItems(new String[0]);
                    txtApKhuPho.setItems(new String[0]);
                    txtXaPhuong.setText("----");
                    txtApKhuPho.setText("----");
                    maapkhupho = "";
                    maphuongxa = "";
                    DoDuLieuXaPhuong(code);
                    Log.d(TAG,  maquanhuyen +" - " + listQuanHuyen.get(position).getTen());
                }
            }
        });

        txtXaPhuong.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String code = listPhuongXa.get(position).getMa();
                if(!maphuongxa.equals(code)){
                    maphuongxa = code;
                    maapkhupho = "";
                    txtApKhuPho.setItems(new String[0]);
                    txtApKhuPho.setText("----");
                    DoDuLieuApKhuPho(code);
                    Log.d(TAG,  maphuongxa +" - " + listPhuongXa.get(position).getTen());
                }
            }
        });

        txtApKhuPho.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String code = listApKhuPho.get(position).getMa();
                if(!maapkhupho.equals(code)){
                    maapkhupho = code;
                    Log.d(TAG,  maapkhupho +" - " + listApKhuPho.get(position).getTen());
                }
            }
        });



        txtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NgaySinhPicker();
            }
        });

        txtThangSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NgaySinhPicker();
            }
        });

        txtNamSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NgaySinhPicker();
            }
        });

        chkKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupCheckBox(chkKhac);
                gioitinh = "2";
            }
        });
        chkNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupCheckBox(chkNu);
                gioitinh = "1";
            }
        });
        chkNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupCheckBox(chkNam);
                gioitinh = "0";
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    setNguoiThanDangKyKham ngthan = new setNguoiThanDangKyKham();
                    ngthan.setHoten(txtHoTen.getText().toString());
                    ngthan.setNgaysinh(txtNgaySinh.getText().toString()+
                            "/"+txtThangSinh.getText().toString()+"/"+txtNamSinh.getText().toString());
                    ngthan.setGioitinh(gioitinh);
                    ngthan.setTinh(matinhthanh);
                    ngthan.setQuanhuyen(maquanhuyen);
                    ngthan.setApkhupho(maapkhupho);
                    ngthan.setXaphuong(maphuongxa);
                    ngthan.setCungDiaChi("0");
                    ngthan.setMaMucDich("1");
                    ngthan.setKhaiBaoYTe(khaibaoyte);
                    ngthan.setMaLoaiDangKy(maloaidangky+"");
                    ngthan.setMaDangKyKham(madangky.getMa());
                    ngthan.setSoNha(txtDiaChi.getText().toString());
                    ngthan.setSoTheBH(txtMaBHYT.getText().toString());
                    mAPIService.setDangKyKhamNguoiThan(token,ngthan).enqueue(new Callback<ResponseModel>() {
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
                                    i.putExtra("noidungkham"," Anh/Chị đã đăng ký " + dv.getTen() + " <br/>khám thành công");
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
                }
            }
        });
    }

    private void groupCheckBox(RadioButton b){
        if(b != chkKhac){
            chkKhac.setChecked(false);
        }
        if(b != chkNam){
            chkNam.setChecked(false);
        }
        if(b != chkNu){
            chkNu.setChecked(false);
        }
    }

    private void NgaySinhPicker(){
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(KhaiBaoYTeNguoiThanActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                txtNamSinh.setText(year+"");
                txtNgaySinh.setText(dayOfMonth+"");
                txtThangSinh.setText(monthOfYear+1 < 10 ? "0"+(monthOfYear+1) : monthOfYear+1+"");
            }
        }, 1997, 0, 26);
        datePickerDialog.setTitle("Chọn ngày");

        datePickerDialog.show();
    }

    private void DoDuLieuQuanHuyen(String matinhthanh){
        mAPIService.getQuanHuyen(APIKey,new baseGetClass(matinhthanh)).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("OK")){
                        getMaTen[] g = new Gson().fromJson(response.body().getData(), getMaTen[].class);
                        if(g.length>0){
                            listQuanHuyen.clear();
                            listQuanHuyen = new ArrayList<getMaTen>(Arrays.asList(g));
                            Log.d(TAG,"Nhận "+ listQuanHuyen.size()+" quận huyện");
                            String[] s = new String[listQuanHuyen.size()];
                            int i=0;
                            for (getMaTen a: listQuanHuyen) {
                                s[i]=a.getTen();
                                i++;
                            }
                            txtQuanHuyen.setItems(s);
                            return;
                        }
                    }
                }else{
                    //code != 200
                }
                ThongBao(KhaiBaoYTeNguoiThanActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error);
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                ThongBao(KhaiBaoYTeNguoiThanActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error);
            }
        });
    }

    private void DoDuLieuXaPhuong(String maquanhuyen){
        mAPIService.getPhuongXa(APIKey,new baseGetClass(maquanhuyen)).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("OK")){
                        getMaTen[] g = new Gson().fromJson(response.body().getData(), getMaTen[].class);
                        if(g.length>0){
                            listPhuongXa.clear();
                            listPhuongXa = new ArrayList<getMaTen>(Arrays.asList(g));
                            Log.d(TAG,"Nhận "+ listPhuongXa.size()+" phường xã");
                            String[] s = new String[listPhuongXa.size()];
                            int i=0;
                            for (getMaTen a: listPhuongXa) {
                                s[i]=a.getTen();
                                i++;
                            }
                            txtXaPhuong.setItems(s);
                            return;
                        }
                    }
                }else{
                    //code != 200
                }
                ThongBao(KhaiBaoYTeNguoiThanActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error);
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                ThongBao(KhaiBaoYTeNguoiThanActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error);
            }
        });
    }

    private void DoDuLieuApKhuPho(String maapkhupho){
        mAPIService.getApKhuPho(APIKey,new baseGetClass(maapkhupho)).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("OK")){
                        getMaTen[] g = new Gson().fromJson(response.body().getData(), getMaTen[].class);
                        if(g.length>0){
                            txtApKhuPho.setEnabled(true);
                            listApKhuPho.clear();
                            listApKhuPho = new ArrayList<getMaTen>(Arrays.asList(g));
                            Log.d(TAG,"Nhận "+ listApKhuPho.size()+" ấp khu phố");
                            String[] s = new String[listApKhuPho.size()];
                            int i=0;
                            for (getMaTen a: listApKhuPho) {
                                s[i]=a.getTen();
                                i++;
                            }
                            txtApKhuPho.setItems(s);
                            return;
                        }else{
                            txtApKhuPho.setEnabled(false);
                            return;
                        }
                    }
                }else{
                    //code != 200
                }
                ThongBao(KhaiBaoYTeNguoiThanActivity.this, "Có lỗi xảy ra", "Đã có lỗi xảy ra vui lòng thử lại", R.drawable.connection_error);
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                ThongBao(KhaiBaoYTeNguoiThanActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error);
            }
        });
    }


    private void mapView() {
        recyclerView = findViewById(R.id.recyclerView);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnThoat = findViewById(R.id.btnThoat);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnChonBS = findViewById(R.id.btnChonBS);
        btnChonChuyenKhoa = findViewById(R.id.btnChonChuyenKhoa);
        btnDichVuKhac = findViewById(R.id.btnDichVuKhac);
        txtTinhThanh = findViewById(R.id.txtTinhThanh);
        txtTinhThanh = findViewById(R.id.txtTinhThanh);
        txtQuanHuyen = findViewById(R.id.txtQuanHuyen);
        txtXaPhuong = findViewById(R.id.txtXaPhuong);
        txtApKhuPho = findViewById(R.id.txtApKhuPho);
        txtNgaySinh = findViewById(R.id.txtNgaySinh);
        txtThangSinh = findViewById(R.id.txtThangSinh);
        txtNamSinh = findViewById(R.id.txtNamSinh);
        chkNam = findViewById(R.id.chkNam);
        chkNu = findViewById(R.id.chkNu);
        chkKhac = findViewById(R.id.chkKhac);
        txtHoTen = findViewById(R.id.txtHoTen);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtMaBHYT = findViewById(R.id.txtMaBHYT);
    }


}