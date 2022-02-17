package com.hungduy.honghunghospital.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hungduy.honghunghospital.Adapter.KhaiBaoYTeAdapter;
import com.hungduy.honghunghospital.Database.Model.BacSi;
import com.hungduy.honghunghospital.Database.Model.CauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.Database.Model.DanToc;
import com.hungduy.honghunghospital.Database.Model.KhuPho;
import com.hungduy.honghunghospital.Database.Model.PhuongXa;
import com.hungduy.honghunghospital.Database.Model.QuanHuyen;
import com.hungduy.honghunghospital.Database.Model.TinhThanh;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.extModel.CauHoiKhaiBaoYTeEXT;
import com.hungduy.honghunghospital.Model.extModel.getBSCoHinh;
import com.hungduy.honghunghospital.Model.getModel.baseGetClass;
import com.hungduy.honghunghospital.Model.getModel.getCauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.Model.getModel.getMaTen;
import com.hungduy.honghunghospital.Model.getModel.getNgayLamViecDKK;
import com.hungduy.honghunghospital.Model.setModel.setNguoiThanDangKyKham;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.CSJRSpinner.BSSpinner;
import com.hungduy.honghunghospital.Utility.CallbackResponse;
import com.hungduy.honghunghospital.Utility.UtilityHHH;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import jrizani.jrspinner.JRSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KhaiBaoYTeNguoiThanActivity extends BaseKhaiBaoYTeActivity {
    private RecyclerView recyclerView;
    private KhaiBaoYTeAdapter KhaiBaoYTeADT;
    private Button btnDangKy,btnThoat,btnChonBS,btnChonChuyenKhoa,btnDichVuKhac;
    private JRSpinner txtTinhThanh,txtQuanHuyen,txtXaPhuong,txtApKhuPho,txtDanToc;
    private TextView txtNgaySinh,txtThangSinh,txtNamSinh;
    private LinearLayout viewDiaChi;
    private RadioButton btnCo,btnKhong;
    private ArrayList<getBSCoHinh> listBS;
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
    private String madantoc = "";
    private String date ="";

    private EditText txtHoTen,txtDiaChi,txtMaBHYT;

    private String gioitinh = "0",buoi ="";
    private RadioButton chkNam,chkNu,chkKhac;

    private ArrayList<TinhThanh> listTinhThanh  = new ArrayList<>();
    private ArrayList<QuanHuyen> listQuanHuyen  = new ArrayList<>();
    private ArrayList<PhuongXa> listPhuongXa  = new ArrayList<>();
    private ArrayList<KhuPho> listApKhuPho  = new ArrayList<>();
    private ArrayList<DanToc> listDanToc = new ArrayList<>();

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] s = new String[dantocDAO.getAll().size()];
                int i=0;
                int vitriKinh = 0;
                for (DanToc a: dantocDAO.getAll()) {
                    s[i]=a.getTen();
                    if(a.getTen().equals("Kinh")){
                        vitriKinh = i;
                        madantoc = a.getMa()+"";
                    }
                    i++;
                }
                listDanToc = (ArrayList<DanToc>) dantocDAO.getAll();
                int finalVitriKinh = vitriKinh;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        txtDanToc.setItems(s);
                        txtDanToc.select(finalVitriKinh);
                    }
                });
                Log.d(TAG,"Nhận "+ listDanToc.size()+" dan toc");
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

        viewDiaChi.setVisibility(View.GONE);

        btnCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnCo.isChecked()){
                    viewDiaChi.setVisibility(View.GONE);
                }
            }
        });

        btnKhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnKhong.isChecked()){
                    viewDiaChi.setVisibility(View.VISIBLE);
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        KhaiBaoYTeADT = new KhaiBaoYTeAdapter(cauHoiKhaiBaoYTes, getApplicationContext(), KhaiBaoYTeNguoiThanActivity.this);
        recyclerView.setAdapter(KhaiBaoYTeADT);
        recyclerView.setLayoutManager(linearLayoutManager);

        new Thread(new Runnable() {
            @Override
            public void run() {
                cauHoiKhaiBaoYTes.clear();
                CauTL.clear();
                if (KBYTdao.getAll().size() > 0) {
                    for(CauHoiKhaiBaoYTe ch : KBYTdao.getAll()){
                        ch.setCauHoi(ch.getCauHoi().replaceAll("Anh/Chị","Người thân của anh/chị"));
                        cauHoiKhaiBaoYTes.add(new getCauHoiKhaiBaoYTe(ch.getID()+"",ch.getCauHoi(),""));
                        CauTL.add(new CauHoiKhaiBaoYTeEXT(new getCauHoiKhaiBaoYTe(ch.getID()+"",ch.getCauHoi(),""), "Không"));
                    }
                    KhaiBaoYTeADT.notifyDataSetChanged();
                    Log.d(TAG,"Data local !!!");
                } else {
                    mAPIService.getCauHoiKBYT(APIKey).enqueue(new CallbackResponse(KhaiBaoYTeNguoiThanActivity.this){
                        @Override
                        public void success(Response<ResponseModel> response) {
                            try{
                                getCauHoiKhaiBaoYTe[] cauhois = new Gson().fromJson(response.body().getData(), getCauHoiKhaiBaoYTe[].class);
                                if (cauhois.length > 0) {
                                    int i = 0;
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
                            }catch (Exception e){
                                Toast.makeText(KhaiBaoYTeNguoiThanActivity.this, "Đã có lỗi xảy ra "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] s = new String[TTdao.getAll().size()];
                int i=0;
                for (TinhThanh a: TTdao.getAll()) {
                    s[i]=a.getTen();
                    i++;
                }
                listTinhThanh = (ArrayList<TinhThanh>) TTdao.getAll();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        txtTinhThanh.setItems(s);
                    }
                });
                Log.d(TAG,"Nhận "+ listTinhThanh.size()+" tỉnh thành");
            }
        }).start();

        txtTinhThanh.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String code = listTinhThanh.get(position).getMa()+"";
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
                    DoDuLieuQuanHuyen(listTinhThanh.get(position).getMa());
                    Log.d(TAG,  matinhthanh +" - " + listTinhThanh.get(position).getTen());
                }
            }
        });

        txtQuanHuyen.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String code = listQuanHuyen.get(position).getMa()+"";
                if(!maquanhuyen.equals(code)){
                    maquanhuyen = code;
                    txtXaPhuong.setItems(new String[0]);
                    txtApKhuPho.setItems(new String[0]);
                    txtXaPhuong.setText("----");
                    txtApKhuPho.setText("----");
                    maapkhupho = "";
                    maphuongxa = "";
                    DoDuLieuXaPhuong(listQuanHuyen.get(position).getMa());
                    Log.d(TAG,  maquanhuyen +" - " + listQuanHuyen.get(position).getTen());
                }
            }
        });

        txtXaPhuong.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String code = listPhuongXa.get(position).getMa()+"";
                if(!maphuongxa.equals(code)){
                    maphuongxa = code;
                    maapkhupho = "";
                    txtApKhuPho.setItems(new String[0]);
                    txtApKhuPho.setText("----");
                    DoDuLieuApKhuPho(listPhuongXa.get(position).getMa());
                    Log.d(TAG,  maphuongxa +" - " + listPhuongXa.get(position).getTen());
                }
            }
        });

        txtApKhuPho.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String code = listApKhuPho.get(position).getMa()+"";
                if(!maapkhupho.equals(code)){
                    maapkhupho = code;
                    Log.d(TAG,  maapkhupho +" - " + listApKhuPho.get(position).getTen());
                }
            }
        });

        txtDanToc.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String code = listDanToc.get(position).getMa()+"";
                if(!madantoc.equals(code)){
                    madantoc = code;
                    Log.d(TAG,  madantoc +" - " + listDanToc.get(position).getTen());
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
                showDialogLoading(2000);
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
                    buoi = "";
                    madangky = chuyenKhoa;
                    Log.d(TAG,chuyenKhoa.getTen());
                }
                if(dichVu != null){
                    found =true;
                    maloaidangky = 3;
                    buoi = "";
                    madangky = dichVu;
                    Log.d(TAG,dichVu.getTen());
                }
                if(found){
                    String khaibaoyte = "";
                    for(CauHoiKhaiBaoYTeEXT c : CauTL){
                        khaibaoyte += c.getCauhoi() +" : " + c.getCautraloi() + "|";
                    }
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
                    ngthan.setCungDiaChi(btnCo.isChecked() ? "1":"0");
                    ngthan.setMaMucDich("1");
                    ngthan.setKhaiBaoYTe(khaibaoyte);
                    ngthan.setMaLoaiDangKy(maloaidangky+"");
                    ngthan.setMaDangKyKham(madangky.getMa());
                    ngthan.setSoNha(txtDiaChi.getText().toString());
                    ngthan.setSoTheBH(txtMaBHYT.getText().toString());
                    ngthan.setMaDanToc(madantoc);
                    ngthan.setNgaydukien(date);
                    mAPIService.setDangKyKhamNguoiThan(token,ngthan).enqueue(new CallbackResponse(KhaiBaoYTeNguoiThanActivity.this) {
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
                            i.putExtra("noidungkham","Người thân của anh/chị đã đăng ký " + dv.getTen() + " khám thành công <br/>Dự kiến khám ngày: "+date);
                            i.putExtra("FullName",FullName);
                            i.putExtra("urlImage",urlImage);
                            i.putExtra("loai",3);
                            i.putExtra("QR",response.body().getData());
                            startActivity(i);
                            HideDialogLoading();
                            finish();
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

    private void handleDVK() {
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
        txtBS.setTitle("Chọn dịch vụ");
        EditText edtNgayKham = dialog.findViewById(R.id.edtNgayKham);
        edtNgayKham.setEnabled(true);
        UtilityHHH.edtDate(getSupportFragmentManager(),edtNgayKham,true);
        mAPIService.getDichVu(APIKey).enqueue(new CallbackResponse(KhaiBaoYTeNguoiThanActivity.this) {
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
                        Log.d(TAG,"Nhận DS "+ dsBS.length +" danh mục chuyên khoa");
                    }
                }catch (Exception e){
                    Toast.makeText(KhaiBaoYTeNguoiThanActivity.this, "Đã có lỗi xảy ra "+ e.getMessage(), Toast.LENGTH_SHORT).show();
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
                btnDichVuKhac.setText("Người thân tôi muốn đăng ký dịch vụ khác");
                btnDichVuKhac.setBackground(getResources().getDrawable(R.drawable.btn_shape_green_light));
                btnDichVuKhac.setTextColor(getResources().getColor(R.color.textColorGreen));
                btnChonBS.setEnabled(true);
                btnChonChuyenKhoa.setEnabled(true);
                dichVu = null;
                date = "";
                dialog.dismiss();
            }
        });
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNgayKham.getText().toString().equals("Chọn ngày") || dichVu == null){
                    Toast.makeText(KhaiBaoYTeNguoiThanActivity.this, "Vui lòng chọn đầy đủ", Toast.LENGTH_SHORT).show();
                    return;
                }
                date = edtNgayKham.getText().toString();
                btnChonBS.setEnabled(false);
                btnChonChuyenKhoa.setEnabled(false);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void handleChonKhoa() {
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
        txtBS.setTitle("Chọn chuyên khoa");
        EditText edtNgayKham = dialog.findViewById(R.id.edtNgayKham);
        edtNgayKham.setEnabled(true);
        UtilityHHH.edtDate(getSupportFragmentManager(),edtNgayKham,true);
        mAPIService.getDmChuyenKhoa(APIKey).enqueue(new CallbackResponse(KhaiBaoYTeNguoiThanActivity.this){
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
        title.setText("Chọn chuyên khoa");
        txt1.setText("Chuyên khoa");
        negativeBtn.setText("Hủy");
        positiveBtn.setText("Đồng ý");
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChonChuyenKhoa.setText("Người thân tôi muốn khám/tái khám Chuyên khoa");
                btnChonChuyenKhoa.setBackground(getResources().getDrawable(R.drawable.btn_shape_green_light));
                btnChonChuyenKhoa.setTextColor(getResources().getColor(R.color.textColorGreen));
                btnChonBS.setEnabled(true);
                btnDichVuKhac.setEnabled(true);
                chuyenKhoa = null;
                date = "";
                dialog.dismiss();
            }
        });
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNgayKham.getText().toString().equals("Chọn ngày") || chuyenKhoa == null){
                    Toast.makeText(KhaiBaoYTeNguoiThanActivity.this, "Vui lòng chọn đầy đủ", Toast.LENGTH_SHORT).show();
                    return;
                }
                date = edtNgayKham.getText().toString();
                btnChonBS.setEnabled(false);
                btnDichVuKhac.setEnabled(false);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void handleSelectDoctor() {
        Dialog dialog;
        dialog = new Dialog(KhaiBaoYTeNguoiThanActivity.this);
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
        mAPIService.getAllActiveDoctor(APIKey).enqueue(new CallbackResponse(KhaiBaoYTeNguoiThanActivity.this){
            @Override
            public void success(Response<ResponseModel> response) {
                getBSCoHinh[] dsBS = new Gson().fromJson(response.body().getData(), getBSCoHinh[].class);
                if(dsBS.length > 0){
                    listBS.clear();
                    for (getBSCoHinh bs: dsBS ) {
                        listBS.add(bs);
                    }
                    txtBS.setItems(listBS);
                    Log.d(TAG,"Nhận DS "+ dsBS.length +" bs");
                }
            }
        });
        ArrayList<getNgayLamViecDKK> arngayLamViecDKKS = new ArrayList<>();
        txtBS.setOnItemClickListener(new BSSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                bacSi = listBS.get(position);
                mAPIService.getLichLamViecDKK(APIKey, new baseGetClass(bacSi.getMa())).enqueue(new CallbackResponse(KhaiBaoYTeNguoiThanActivity.this) {
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
                                date = "";
                                buoi = "";
                                Toast.makeText(KhaiBaoYTeNguoiThanActivity.this, "Bác sĩ bạn chọn không có lịch làm việc. Vui lòng chọn bác sĩ khác !!!",
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
                    Toast.makeText(KhaiBaoYTeNguoiThanActivity.this, "Vui lòng chọn đầy đủ", Toast.LENGTH_SHORT).show();
                    return;
                }
                date = edtNgayKham.getText().toString();
                btnChonChuyenKhoa.setEnabled(false);
                btnDichVuKhac.setEnabled(false);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void NgaySinhPicker(){
        Calendar c = Calendar.getInstance();
        int mYear=2022;
        int mMonth=0;
        int mDay=1;
        try{

            mYear = UtilityHHH.toInt(txtNamSinh.getText().toString());
            mMonth = UtilityHHH.toInt(txtThangSinh.getText().toString()) - 1;
            mDay = UtilityHHH.toInt(txtNgaySinh.getText().toString());
        }catch (Exception e){
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }

        com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                txtNamSinh.setText(year+"");
                txtNgaySinh.setText(dayOfMonth+"");
                txtThangSinh.setText(monthOfYear+1 < 10 ? "0"+(monthOfYear+1) : monthOfYear+1+"");
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.setOkText("Chọn");
        datePickerDialog.setCancelText("Hủy");
        datePickerDialog.setLocale(new Locale("vi"));
        datePickerDialog.show(getSupportFragmentManager(), "Chọn ngày sinh");

      /*  DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateUserActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                txtNamSinh.setText(year+"");
                txtNgaySinh.setText(dayOfMonth+"");
                txtThangSinh.setText(monthOfYear+1 < 10 ? "0"+(monthOfYear+1) : monthOfYear+1+"");
            }
        }, 1997, 0, 26);
        datePickerDialog.setTitle("Chọn ngày");
        datePickerDialog.show();*/
    }

    private void DoDuLieuQuanHuyen(int matinhthanh){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<QuanHuyen> qh = (ArrayList<QuanHuyen>) QHdao.getQuanHuyenByTinhThanh(matinhthanh);
                String[] s = new String[qh.size()];
                int i=0;
                for (QuanHuyen a: qh) {
                    s[i]=a.getTen();
                    i++;
                }
                listQuanHuyen = qh;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        txtQuanHuyen.setItems(s);
                    }
                });
                Log.d(TAG,"Nhận "+ qh.size()+" quận huyện");
            }
        }).start();
    }

    private void DoDuLieuXaPhuong(int maquanhuyen){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<PhuongXa> px = (ArrayList<PhuongXa>) PXdao.getPhuongXaByQuanHuyen(maquanhuyen);
                String[] s = new String[px.size()];
                int i=0;
                for (PhuongXa a: px) {
                    s[i]=a.getTen();
                    i++;
                }
                listPhuongXa = px;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        txtXaPhuong.setItems(s);
                    }
                });
                Log.d(TAG,"Nhận "+ px.size()+" phường xã");
            }
        }).start();
    }

    private void DoDuLieuApKhuPho(int maqh){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<KhuPho> kp = (ArrayList<KhuPho>) KPdao.getKhuPhoByPhuongXa(maqh);
                String[] s = new String[kp.size()];
                int i=0;
                for (KhuPho a: kp) {
                    s[i]=a.getTen();
                    i++;
                }
                listApKhuPho = kp;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if(kp.size() > 0){
                            txtApKhuPho.setItems(s);
                        }else {
                            txtApKhuPho.setEnabled(false);
                        }
                    }
                });
                Log.d(TAG,"Nhận "+ kp.size()+" phường xã");
            }
        }).start();
    }

    private void mapView() {
        recyclerView = findViewById(R.id.recyclerView);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnThoat = findViewById(R.id.btnThoat);
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
        txtHoTen = findViewById(R.id.txtNhomDV);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtMaBHYT = findViewById(R.id.txtMaBHYT);
        viewDiaChi = findViewById(R.id.viewDiaChi);
        btnCo = findViewById(R.id.btnCo);
        btnKhong = findViewById(R.id.btnKhong);
        txtDanToc = findViewById(R.id.txtDanToc);
    }


}