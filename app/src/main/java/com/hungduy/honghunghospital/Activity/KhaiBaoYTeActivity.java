package com.hungduy.honghunghospital.Activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hungduy.honghunghospital.Adapter.KhaiBaoYTeAdapter;
import com.hungduy.honghunghospital.Database.Model.CauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.extModel.CauHoiKhaiBaoYTeEXT;
import com.hungduy.honghunghospital.Model.extModel.getBSCoHinh;
import com.hungduy.honghunghospital.Model.getModel.baseGetClass;
import com.hungduy.honghunghospital.Model.getModel.getCauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.Model.getModel.getMaTen;
import com.hungduy.honghunghospital.Model.getModel.getNgayLamViecDKK;
import com.hungduy.honghunghospital.Model.setModel.setDangKyKham;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.CSJRSpinner.BSSpinner;
import com.hungduy.honghunghospital.Utility.CallbackResponse;
import com.hungduy.honghunghospital.Utility.UtilityHHH;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jrizani.jrspinner.JRSpinner;
import retrofit2.Response;

public class KhaiBaoYTeActivity extends BaseKhaiBaoYTeActivity {
    private RecyclerView recyclerView;
    private KhaiBaoYTeAdapter KhaiBaoYTeADT;
    private Button btnDangKy,btnThoat,btnChonBS,btnChonChuyenKhoa,btnDichVuKhac,btnDuaNguoiThan;
    private ArrayList<getBSCoHinh> listBS;
    private ArrayList<getMaTen> listDMCK;
    private ArrayList<getMaTen> listDV;
    private getMaTen bacSi;
    private getMaTen chuyenKhoa;
    private getMaTen dichVu;
    private String date;
    private String buoi="";

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
                  //  Log.d(TAG,"Data local !!!");
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
                handleSelectDoctor();
            }
        });

        btnChonChuyenKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleChonKhoa();
            }
        });

        btnDichVuKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDVK();
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
                }
                if(chuyenKhoa != null){
                    found =true;
                    maloaidangky = 2;
                    buoi = "";
                    madangky = chuyenKhoa;
                }
                if(dichVu != null){
                    found =true;
                    maloaidangky = 3;
                    buoi = "";
                    madangky = dichVu;
                }
                if(found){
                    showDialogLoading(1000);
                    String khaibaoyte = "";
                    for(CauHoiKhaiBaoYTeEXT c : CauTL){
                        khaibaoyte += c.getCauhoi() +" : " + c.getCautraloi() + "|";
                    }
                    setDangKyKham dkkb = new setDangKyKham("1", khaibaoyte, maloaidangky + "",
                            madangky.getMa(),"", "", date,buoi);
                    final getMaTen dv = madangky;
                    final int maloai = maloaidangky;
                    Log.d(TAG, "onClick: "+ new Gson().toJson(dkkb));
                    mAPIService.setDangKyKham(token,dkkb).enqueue(new CallbackResponse(KhaiBaoYTeActivity.this) {
                        @Override
                        public void success(Response<ResponseModel> response) {
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
                            i.putExtra("noidungkham"," Anh/Chị đã đăng ký khám " + dv.getTen() + " thành công <br/>Dự kiến ngày khám: "+date);
                            i.putExtra("dv",dv.getTen());
                            i.putExtra("loai",1);
                            i.putExtra("FullName",FullName);
                            i.putExtra("urlImage",urlImage);
                            i.putExtra("QR",response.body().getData());
                            HideDialogLoading();
                            startActivity(i);
                            finish();
                        }
                    });
                }else{
                    ThongBao(KhaiBaoYTeActivity.this,"Thông báo","Vui lòng chọn dịch vụ bạn cần !",R.drawable.searching);
                    HideDialogLoading();
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

    private void handleDVK() {
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
        txtBS.setTitle("Chọn dịch vụ");
        EditText edtNgayKham = dialog.findViewById(R.id.edtNgayKham);
        edtNgayKham.setEnabled(true);
        UtilityHHH.edtDate(getSupportFragmentManager(),edtNgayKham,true);
        mAPIService.getDichVu(APIKey).enqueue(new CallbackResponse(KhaiBaoYTeActivity.this) {
            @Override
            public void success(Response<ResponseModel> response) {
                try{
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
                      //  Log.d(TAG,"Nhận DS "+ dsBS.length +" danh mục chuyên khoa");
                    }
                }catch (Exception e){
                    Toast.makeText(KhaiBaoYTeActivity.this, "Đã có lỗi xảy ra "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
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
        title.setText("Chọn dịch vụ");
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
                date = "";
                dialog.dismiss();
            }
        });
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNgayKham.getText().toString().equals("Chọn ngày") || dichVu == null){
                    Toast.makeText(KhaiBaoYTeActivity.this, "Vui lòng chọn đầy đủ", Toast.LENGTH_SHORT).show();
                    return;
                }
                date = edtNgayKham.getText().toString();
                btnChonBS.setEnabled(false);
                btnChonChuyenKhoa.setEnabled(false);
                btnDuaNguoiThan.setEnabled(false);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void handleChonKhoa() {
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
        txtBS.setTitle("Chọn chuyên khoa");
        EditText edtNgayKham = dialog.findViewById(R.id.edtNgayKham);
        edtNgayKham.setEnabled(true);
        UtilityHHH.edtDate(getSupportFragmentManager(),edtNgayKham,true);
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
                  //  Log.d(TAG,"Nhận DS "+ dsBS.length +" danh mục chuyên khoa");
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
        title.setText("Chọn chuyên khoa");
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
                date = "";
                dialog.dismiss();
            }
        });
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNgayKham.getText().toString().equals("Chọn ngày") || chuyenKhoa == null){
                    Toast.makeText(KhaiBaoYTeActivity.this, "Vui lòng chọn đầy đủ", Toast.LENGTH_SHORT).show();
                    return;
                }
                date = edtNgayKham.getText().toString();
                btnChonBS.setEnabled(false);
                btnDichVuKhac.setEnabled(false);
                btnDuaNguoiThan.setEnabled(false);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void handleSelectDoctor() {
        Dialog dialog;
        dialog = new Dialog(KhaiBaoYTeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dang_ky_kham_bs);
        TextView title = dialog.findViewById(R.id.title);
        TextView txt1 = dialog.findViewById(R.id.txt1);
        EditText edtNgayKham = dialog.findViewById(R.id.edtNgayKham);
        Button negativeBtn = dialog.findViewById(R.id.negativeBtn);
        Button positiveBtn = dialog.findViewById(R.id.positiveBtn);
        BSSpinner txtBS = dialog.findViewById(R.id.txtBS);
        CheckBox cbxSang = dialog.findViewById(R.id.cbxSang);
        CheckBox cbxChieu = dialog.findViewById(R.id.cbxChieu);
        cbxSang.setEnabled(false);
        cbxChieu.setEnabled(false);
        cbxChieu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cbxSang.isEnabled()){
                    cbxSang.setChecked(!b);
                    buoi = "c";
                }
            }
        });
        cbxSang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cbxSang.isEnabled()) {
                    cbxChieu.setChecked(!b);
                    buoi = "s";
                }
            }
        });
        mAPIService.getAllActiveDoctor(APIKey).enqueue(new CallbackResponse(KhaiBaoYTeActivity.this){
            @Override
            public void success(Response<ResponseModel> response) {
                getBSCoHinh[] dsBS = new Gson().fromJson(response.body().getData(), getBSCoHinh[].class);
                if(dsBS.length > 0){
                    listBS.clear();
                    for (getBSCoHinh bs: dsBS ) {
                        listBS.add(bs);
                    }
                    txtBS.setItems(listBS);
                  //  Log.d(TAG,"Nhận DS "+ dsBS.length +" bs");
                }
            }
        });
        ArrayList<getNgayLamViecDKK> arngayLamViecDKKS = new ArrayList<>();
        txtBS.setOnItemClickListener(new BSSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                bacSi = listBS.get(position);
                mAPIService.getLichLamViecDKK(APIKey, new baseGetClass(bacSi.getMa())).enqueue(new CallbackResponse(KhaiBaoYTeActivity.this) {
                    @Override
                    public void success(Response<ResponseModel> response) {
                        arngayLamViecDKKS.clear();
                        try{
                            getNgayLamViecDKK[] ngayLamViecDKKS = new Gson().fromJson(response.body().getData(),getNgayLamViecDKK[].class);
                            if(ngayLamViecDKKS.length > 0){
                                Calendar[] date = new Calendar[ngayLamViecDKKS.length];
                                for(int i=0;i<ngayLamViecDKKS.length;i++){
                                    Calendar x = Calendar.getInstance();
                                    x.set(Calendar.DAY_OF_MONTH, UtilityHHH.toInt(ngayLamViecDKKS[i].getNgay()));
                                    date[i] = x;
                                    arngayLamViecDKKS.add(ngayLamViecDKKS[i]);
                                }
                                UtilityHHH.edtDateWithEnableDate(getSupportFragmentManager(), edtNgayKham, date, new UtilityHHH.DateSetCallback() {
                                    @Override
                                    public void CalllBack(Calendar c) {
                                        cbxChieu.setChecked(false);
                                        cbxChieu.setEnabled(false);
                                        cbxSang.setChecked(false);
                                        cbxSang.setEnabled(false);
                                        buoi = "";
                                        String ngay = c.get(Calendar.DAY_OF_MONTH) +"";
                                        for(getNgayLamViecDKK u : arngayLamViecDKKS){
                                            if(u.getNgay().equals(ngay)){
                                                if(u.getC().equals("c")){
                                                    cbxChieu.setEnabled(true);
                                                    cbxChieu.setChecked(true);
                                                }
                                                if(u.getS().equals("s")){
                                                    cbxSang.setEnabled(true);
                                                    cbxSang.setChecked(true);
                                                }
                                                break;
                                            }
                                        }
                                    }
                                });
                                edtNgayKham.setEnabled(true);
                            }else{
                                cbxSang.setEnabled(false);
                                cbxChieu.setEnabled(false);
                                edtNgayKham.setText("Chọn ngày");
                                edtNgayKham.setEnabled(false);
                                buoi = "";
                                date = "";
                                Toast.makeText(KhaiBaoYTeActivity.this, "Bác sĩ bạn chọn không có lịch làm việc. Vui lòng chọn bác sĩ khác !!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){

                        }
                    }
                });
                btnChonBS.setText("Chọn bác sĩ "+listBS.get(position).getTen());
                btnChonBS.setBackground(getResources().getDrawable(R.drawable.btn_shape_green));
                btnChonBS.setTextColor(getResources().getColor(R.color.white));
            }
        });

        title.setText("Chọn bác sĩ");
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
                date = "";
                buoi = "";
                dialog.dismiss();
            }
        });
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNgayKham.getText().toString().equals("Chọn ngày") || bacSi == null || buoi.isEmpty()){
                    Toast.makeText(KhaiBaoYTeActivity.this, "Vui lòng chọn đầy đủ", Toast.LENGTH_SHORT).show();
                    return;
                }
                date = edtNgayKham.getText().toString();
                btnChonChuyenKhoa.setEnabled(false);
                btnDichVuKhac.setEnabled(false);
                btnDuaNguoiThan.setEnabled(false);
                dialog.dismiss();
            }
        });
        dialog.show();
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