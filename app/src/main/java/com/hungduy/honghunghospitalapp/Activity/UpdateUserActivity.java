package com.hungduy.honghunghospitalapp.Activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.github.drjacky.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.hungduy.honghunghospitalapp.Database.Model.DanToc;
import com.hungduy.honghunghospitalapp.Database.Model.KhuPho;
import com.hungduy.honghunghospitalapp.Database.Model.PhuongXa;
import com.hungduy.honghunghospitalapp.Database.Model.QuanHuyen;
import com.hungduy.honghunghospitalapp.Database.Model.QuocGia;
import com.hungduy.honghunghospitalapp.Database.Model.TinhThanh;
import com.hungduy.honghunghospitalapp.Database.Model.UserData;
import com.hungduy.honghunghospitalapp.Model.ResponseModel;
import com.hungduy.honghunghospitalapp.Model.getModel.getUser;
import com.hungduy.honghunghospitalapp.Model.setModel.updateUser;
import com.hungduy.honghunghospitalapp.R;
import com.hungduy.honghunghospitalapp.Utility.AppConfigString;
import com.hungduy.honghunghospitalapp.Utility.CallbackResponse;
import com.hungduy.honghunghospitalapp.Utility.UtilityHHH;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import jrizani.jrspinner.JRSpinner;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserActivity extends BaseActivity {
    private ImageView imgLogoBVHH,imgUserAVT,imgBHYT,imgUser;
    private TextView txtNgaySinh,txtThangSinh,txtNamSinh;
    private EditText txtNewPassword,txtReEnterNewPassword,txtHoTen,txtDiaChi,txtSDT,txtCMND,txtMaBHYT,txtOldPassword;
    private Button btnThoat,btnLuu;
    private JRSpinner txtTinhThanh,txtQuanHuyen,txtXaPhuong,txtApKhuPho,txtQuocTich,txtDanToc;
    private RadioButton chkNam,chkNu,chkKhac;
    private LinearLayout ViewUpdate,viewNewPassword,ViewRegister;
    private Switch swDoiMatKhau;

    private ArrayList<TinhThanh> listTinhThanh  = new ArrayList<>();
    private ArrayList<QuanHuyen> listQuanHuyen  = new ArrayList<>();
    private ArrayList<PhuongXa> listPhuongXa  = new ArrayList<>();
    private ArrayList<KhuPho> listApKhuPho  = new ArrayList<>();
    private ArrayList<QuocGia> listQuocGia  = new ArrayList<>();
    private ArrayList<DanToc> listDanToc = new ArrayList<>();

    private Uri URIimgUser,URIimgBHYT;


    private String matinhthanh = "";
    private String maquanhuyen = "";
    private String maphuongxa = "";
    private String maapkhupho = "";
    private String maquoctich = "";

    private String gioitinh = "0";

    private String imgUserURL = "";
    private String imgBHYTURL = "";

    private String newimgUserURL = "";
    private String newimgBHYTURL = "";


    @Override
    public void Disconnect() {
        super.Disconnect();
        btnLuu.setEnabled(false);
        btnLuu.setAlpha(.5f);
    }

    @Override
    public void Connected() {
        super.Connected();
        if(token == null || token.isEmpty()){
            btnLuu.setEnabled(false);
            btnLuu.setAlpha(.5f);
        }else{
            mAPIService.ping().enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if(response.isSuccessful() && response.body().getStatus().equals("OK")){
                        btnLuu.setEnabled(true);
                        btnLuu.setAlpha(1f);
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {

                }
            });
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        showDialogLoading(2000);

        mapView();
        initView();

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] s = new String[QGdao.getAll().size()];
                int i=0;
                int vitriVN = 0;
                for (QuocGia a: QGdao.getAll()) {
                    s[i]=a.getTen();
                    if(a.getTen().equals("Việt Nam")){
                        vitriVN = i;
                        maquoctich = a.getMa()+"";
                    }
                    i++;
                }
                listQuocGia = (ArrayList<QuocGia>) QGdao.getAll();
                int finalVitriVN = vitriVN;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        txtQuocTich.setItems(s);
                        txtQuocTich.select(finalVitriVN);
                    }
                });
                Log.d(TAG,"Nhận "+ listQuocGia.size()+" quốc gia");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] s = new String[dantocDAO.getAll().size()];
                int i=0;
                for (DanToc a: dantocDAO.getAll()) {
                    s[i]=a.getTen();
                    i++;
                }
                listDanToc = (ArrayList<DanToc>) dantocDAO.getAll();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        txtDanToc.setItems(s);
                    }
                });
                Log.d(TAG,"Nhận "+ listDanToc.size()+" dan toc");
            }
        }).start();

        txtTinhThanh.setItems(new String[0]);
        txtQuanHuyen.setItems(new String[0]);
        txtXaPhuong.setItems(new String[0]);
        txtApKhuPho.setItems(new String[0]);

        swDoiMatKhau.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                txtNewPassword.setText("");
                txtReEnterNewPassword.setText("");
                if(b){
                    viewNewPassword.setVisibility(View.VISIBLE);
                }else {
                    viewNewPassword.setVisibility(View.GONE);
                }
            }
        });

        imgBHYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(UpdateUserActivity.this)
                        .crop()
                        .cropFreeStyle()
                        .maxResultSize(512, 512, true)
                        .createIntentFromDialog((Function1) (new Function1() {
                            public Object invoke(Object var1) {
                                this.invoke((Intent) var1);
                                return Unit.INSTANCE;
                            }

                            public final void invoke(@NotNull Intent it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                launcherImgBHYT.launch(it);
                            }
                        }));
            }
        });

        imgUserAVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(UpdateUserActivity.this)
                        .crop()
                        .cropOval()
                        .maxResultSize(512, 512, true)
                        .createIntentFromDialog((Function1) (new Function1() {
                            public Object invoke(Object var1) {
                                this.invoke((Intent) var1);
                                return Unit.INSTANCE;
                            }

                            public final void invoke(@NotNull Intent it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                launcherImgUser.launch(it);
                            }
                        }));
            }
        });

        imgLogoBVHH.setEnabled(false);
        imgUser.setEnabled(false);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtTinhThanh.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String code = listTinhThanh.get(position).getMa() + "";
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
            }
        });

        txtQuanHuyen.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String code = listQuanHuyen.get(position).getMa()+"";
                maquanhuyen = code;
                txtXaPhuong.setItems(new String[0]);
                txtApKhuPho.setItems(new String[0]);
                txtXaPhuong.setText("----");
                txtApKhuPho.setText("----");
                maapkhupho = "";
                maphuongxa = "";
                DoDuLieuXaPhuong(listQuanHuyen.get(position).getMa());
            }
        });

        txtXaPhuong.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String code = listPhuongXa.get(position).getMa()+"";
                maphuongxa = code;
                maapkhupho = "";
                txtApKhuPho.setItems(new String[0]);
                txtApKhuPho.setText("----");
                DoDuLieuApKhuPho(listPhuongXa.get(position).getMa());
            }
        });

        txtApKhuPho.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String code = listApKhuPho.get(position).getMa()+"";
                maapkhupho = code;
            }
        });

        txtQuocTich.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String code = listQuocGia.get(position).getMa()+"";
                if(!maquoctich.equals(code)){
                    maquoctich = code;
                    Log.d(TAG,  maquoctich +" - " + listQuocGia.get(position).getTen());
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
        
        txtReEnterNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!txtNewPassword.getText().toString().equals(txtReEnterNewPassword.getText().toString())){
                    txtReEnterNewPassword.setBackground(shape_edittext_error);
                }else{
                    txtReEnterNewPassword.setBackground(shape_edittext_have_focus);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtOldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtOldPassword.getText().toString().isEmpty()){
                    txtOldPassword.setBackground(shape_edittext_error);
                }else{
                    txtOldPassword.setBackground(shape_edittext_have_focus);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtSDT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtSDT.getText().toString().isEmpty()){
                    txtSDT.setBackground(shape_edittext_error);
                }else{
                    txtSDT.setBackground(shape_edittext_have_focus);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(swDoiMatKhau.isChecked() ){
                    if(txtNewPassword.getText().toString().isEmpty()){
                        txtNewPassword.setBackground(shape_edittext_error);
                    }else{
                        txtNewPassword.setBackground(shape_edittext_have_focus);
                    }
                    if(txtReEnterNewPassword.getText().toString().isEmpty()){
                        txtReEnterNewPassword.setBackground(shape_edittext_error);
                    }else{
                        txtReEnterNewPassword.setBackground(shape_edittext_have_focus);
                    }

                    if(txtReEnterNewPassword.getText().toString().length() < 8){
                        ThongBao(UpdateUserActivity.this,"Đã có lỗi xảy ra",
                                "Mật khẩu phải từ 8 ký tự trở lên",R.drawable.password_drbl);
                        return;
                    }
                }
                if(txtHoTen.getText().toString().isEmpty() ||txtOldPassword.getText().toString().isEmpty()
                        || txtSDT.getText().toString().isEmpty() || matinhthanh.isEmpty() || maquanhuyen.isEmpty()
                        || maphuongxa.isEmpty()){
                    if(txtHoTen.getText().toString().isEmpty()){
                        txtHoTen.setBackground(shape_edittext_error);
                    }else{
                        txtHoTen.setBackground(shape_edittext_have_focus);
                    }

                    if(txtOldPassword.getText().toString().isEmpty()){
                        txtOldPassword.setBackground(shape_edittext_error);
                    }else{
                        txtOldPassword.setBackground(shape_edittext_have_focus);
                    }

                    if(txtQuocTich.getText().toString().isEmpty()){
                        txtQuocTich.setBackground(shape_edittext_error);
                    }else{
                        txtQuocTich.setBackground(shape_edittext_have_focus);
                    }
                    ThongBao(UpdateUserActivity.this,"Đã có lỗi xảy ra",
                            "Bạn chưa nhập đủ thông tin. Những mục có dấu * là bắt buộc!",R.drawable.connection_error);
                } else {
                    if(swDoiMatKhau.isChecked() && !txtNewPassword.getText().toString().equals(txtReEnterNewPassword.getText().toString())){
                        ThongBao(UpdateUserActivity.this,"Đã có lỗi xảy ra",
                                "Mật khẩu không trùng khớp",R.drawable.password_drbl);
                        return;
                    }
                    showDialogLoading(2000);

                    btnLuu.setEnabled(false);
                    btnLuu.setAlpha(.3f);
                    if (URIimgUser != null) {
                        File file = new File(URIimgUser.getPath());
                        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("files[0]", file.getName(), requestFile);
                        mAPIService.ImageUploadFile(APIKey, body).enqueue(new CallbackResponse(UpdateUserActivity.this) {
                            @Override
                            public void success(Response<ResponseModel> response) {
                                try {
                                    String[] url = new Gson().fromJson(response.body().getData(), String[].class);
                                    newimgUserURL = url[0];
                                    Log.d(TAG, response.body().getStatus() + " " + response.body().getMessenge() + " " + url[0]);
                                }catch (Exception ex){

                                }
                            }
                        });
                    }

                    if (URIimgBHYT != null) {
                        File file = new File(URIimgBHYT.getPath());
                        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("files[0]", file.getName(), requestFile);
                        mAPIService.ImageUploadFile(APIKey, body).enqueue(new CallbackResponse(UpdateUserActivity.this) {
                            @Override
                            public void success(Response<ResponseModel> response) {
                                try{
                                    String[] url = new Gson().fromJson(response.body().getData(), String[].class);
                                    newimgBHYTURL = url[0];
                                    Log.d(TAG, response.body().getStatus() + " " + response.body().getMessenge() + " " + url[0]);
                                }catch (Exception ex){

                                }
                            }
                        });
                    }

                    Thread setUser = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            updateUser usrUpdate = new updateUser();
                            usrUpdate.setTinh(matinhthanh);
                            usrUpdate.setQuanhuyen(maquanhuyen);
                            usrUpdate.setXaphuong(maphuongxa);
                            usrUpdate.setApkhupho(maapkhupho);
                            usrUpdate.setQuoctich(maquoctich);
                            usrUpdate.setOldPassword(txtOldPassword.getText().toString());
                            usrUpdate.setImgUser(newimgUserURL.isEmpty() ? imgUserURL : newimgUserURL);
                            usrUpdate.setImgBHYT(newimgBHYTURL.isEmpty() ? imgBHYTURL : newimgBHYTURL);
                            usrUpdate.setPassword(swDoiMatKhau.isChecked() ? txtReEnterNewPassword.getText().toString() : "");
                            usrUpdate.setSonha(txtDiaChi.getText().toString());
                            usrUpdate.setHochieu(txtCMND.getText().toString());
                            usrUpdate.setBhyt(txtMaBHYT.getText().toString());
                            usrUpdate.setHoten(txtHoTen.getText().toString());
                            usrUpdate.setGioitinh(gioitinh);
                            usrUpdate.setNgaysinh(txtNgaySinh.getText().toString() +"/"+txtThangSinh.getText().toString()+"/"+txtNamSinh.getText().toString());
                            mAPIService.updateUser(token, usrUpdate).enqueue(new CallbackResponse(UpdateUserActivity.this) {
                                @Override
                                public void success(Response<ResponseModel> response) {
                                    if (response.body().getStatus().equals("OK")) {
                                        ThongBao(UpdateUserActivity.this, "Thành công", response.body().getMessenge(), R.drawable.connection_error, new FancyGifDialogListener() {
                                            @Override
                                            public void OnClick() {
                                                finish();
                                            }
                                        });
                                    }else{
                                        btnLuu.setEnabled(true);
                                        btnLuu.setAlpha(1f);
                                        HideDialogLoading();
                                    }
                                }
                            });

                        }
                    });

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (URIimgUser != null && newimgUserURL == "") {
                            }
                            while (URIimgBHYT != null && newimgBHYTURL == "") {
                            }
                            setUser.start();
                        }
                    }).start();

                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isConnected){
                    mAPIService.getUserbyToken(token).enqueue(new CallbackResponse(UpdateUserActivity.this){
                        @Override
                        public void success(Response<ResponseModel> response) {
                            getUser usr;
                            try{
                                usr = new Gson().fromJson(response.body().getData(),getUser.class);
                            }catch (Exception e){
                                usr = null;
                            }

                            if(usr != null){
                                getUser finalUsr = usr;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        USRdao.insert(new UserData(AppConfigString.Username, finalUsr.getUsername()));
                                        USRdao.insert(new UserData(AppConfigString.HoTen, finalUsr.getHoTen()));
                                        USRdao.insert(new UserData(AppConfigString.NgaySinh, finalUsr.getNgaySinh()));
                                        USRdao.insert(new UserData(AppConfigString.GioiTinh, finalUsr.getGioiTinh()+""));
                                        USRdao.insert(new UserData(AppConfigString.MaTinh, finalUsr.getMaTinh()+""));
                                        USRdao.insert(new UserData(AppConfigString.MaHuyen, finalUsr.getMaHuyen()+""));
                                        USRdao.insert(new UserData(AppConfigString.MaPhuongXa, finalUsr.getMaPhuongXa()+""));
                                        USRdao.insert(new UserData(AppConfigString.MaApKhuPho, finalUsr.getMaApKhuPho()+""));
                                        USRdao.insert(new UserData(AppConfigString.SoNha, finalUsr.getSoNha()));
                                        USRdao.insert(new UserData(AppConfigString.QuocTich, finalUsr.getQuocTich()+""));
                                        USRdao.insert(new UserData(AppConfigString.HoChieu, finalUsr.getHoChieu()));
                                        USRdao.insert(new UserData(AppConfigString.MaTheBHYT, finalUsr.getMaTheBHYT()));
                                        USRdao.insert(new UserData(AppConfigString.HinhBHYT, finalUsr.getHinhBHYT()));
                                        USRdao.insert(new UserData(AppConfigString.HinhAnh, finalUsr.getHinhAnh()));
                                        USRdao.insert(new UserData(AppConfigString.DanToc, finalUsr.getDanToc()));
                                    }
                                }).start();
                                txtHoTen.setText(usr.getHoTen());
                                try{
                                    txtNgaySinh.setText(usr.getNgaySinh().split("/")[0]);
                                    txtThangSinh.setText(usr.getNgaySinh().split("/")[1]);
                                    txtNamSinh.setText(usr.getNgaySinh().split("/")[2]);

                                }catch (Exception ex){
                                    txtNgaySinh.setText("");
                                    txtThangSinh.setText("");
                                    txtNamSinh.setText("");
                                }
                                imgUserURL = usr.getHinhAnh();
                                imgBHYTURL = usr.getHinhBHYT();
                                switch (usr.getGioiTinh()){
                                    case 0 :
                                        chkNam.setChecked(true);
                                        chkNu.setChecked(false);
                                        chkKhac.setChecked(false);
                                        break;
                                    case 1:
                                        chkNam.setChecked(false);
                                        chkNu.setChecked(true);
                                        chkKhac.setChecked(false);
                                        break;
                                    case 2:
                                        chkNam.setChecked(false);
                                        chkNu.setChecked(false);
                                        chkKhac.setChecked(true);
                                        break;
                                }
                                for(TinhThanh a : listTinhThanh){
                                    if(a.getMa() == usr.getMaTinh()){
                                        txtTinhThanh.setText(a.getTen());
                                        break;
                                    }
                                }
                                gioitinh = usr.getGioiTinh()+"";
                                matinhthanh = usr.getMaTinh()+"";
                                maquanhuyen = usr.getMaHuyen()+"";
                                maphuongxa = usr.getMaPhuongXa()+"";
                                maapkhupho = usr.getMaApKhuPho()+"";
                                DoDuLieuQuanHuyen(usr.getMaTinh());
                                DoDuLieuXaPhuong(usr.getMaHuyen());
                                DoDuLieuApKhuPho(usr.getMaPhuongXa());
                                if(maapkhupho.equals("")){
                                    txtApKhuPho.setEnabled(false);
                                    txtApKhuPho.setText("---");
                                }
                                txtDiaChi.setText(usr.getSoNha());
                                maquoctich = usr.getQuocTich()+"";
                                for(QuocGia q : listQuocGia){
                                    if(q.getMa() == usr.getQuocTich()){
                                        txtQuocTich.setText(q.getTen());
                                    }
                                }
                                txtCMND.setText(usr.getHoChieu());
                                txtMaBHYT.setText(usr.getMaTheBHYT());
                                txtSDT.setText(usr.getUsername());

                                for(int i=0;i<listDanToc.size();i++){
                                    if(usr.getDanToc().equals(listDanToc.get(i).getMa()+"")){
                                        txtDanToc.select(i);
                                    }
                                }

                                if(!usr.getHinhAnh().isEmpty()){
                                    Picasso.get().load(usr.getHinhAnh()).into(imgUserAVT);
                                    Picasso.get().load(usr.getHinhAnh()).into(
                                            UtilityHHH.picassoImageTarget(getApplicationContext(), AppConfigString.ImageDIR
                                                    , AppConfigString.UserImageName));
                                }else{
                                    Picasso.get().load(R.drawable.avatar_user_empty).into(
                                            UtilityHHH.picassoImageTarget(getApplicationContext(), AppConfigString.ImageDIR
                                                    , AppConfigString.UserImageName));
                                }
                                if(!usr.getHinhBHYT().isEmpty()){
                                    try{
                                        mAPIService.getBHYT(token).enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if(response.isSuccessful()){
                                                    byte[] bytes = new byte[0];
                                                    try {
                                                        bytes = response.body().bytes();
                                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                        imgBHYT.setImageBitmap(bitmap);
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                                            }
                                        });
                                    }catch (Exception e){
                                    }
                                }else{
                                    Picasso.get().load(R.drawable.bhyt).into(
                                            UtilityHHH.picassoImageTarget(getApplicationContext(), AppConfigString.ImageDIR
                                                    , AppConfigString.BHYTImageName));
                                }
                            }
                        }
                    });
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                String sdt = USRdao.getConfig(AppConfigString.Username).getConfigInfo();
                                String hoten = USRdao.getConfig(AppConfigString.HoTen).getConfigInfo();
                                String ngaysinh = USRdao.getConfig(AppConfigString.NgaySinh).getConfigInfo();
                                gioitinh = USRdao.getConfig(AppConfigString.GioiTinh).getConfigInfo();
                                String matinh = USRdao.getConfig(AppConfigString.MaTinh).getConfigInfo();
                                String mahuyen = USRdao.getConfig(AppConfigString.MaHuyen).getConfigInfo();
                                String maphuong = USRdao.getConfig(AppConfigString.MaPhuongXa).getConfigInfo();
                                String makp = USRdao.getConfig(AppConfigString.MaApKhuPho).getConfigInfo();
                                String sonha = USRdao.getConfig(AppConfigString.SoNha).getConfigInfo();
                                String maquoctichL = USRdao.getConfig(AppConfigString.QuocTich).getConfigInfo();
                                String hochieu = USRdao.getConfig(AppConfigString.HoChieu).getConfigInfo();
                                String soBHYT = USRdao.getConfig(AppConfigString.MaTheBHYT).getConfigInfo();
                                String HinhBH = USRdao.getConfig(AppConfigString.HinhBHYT).getConfigInfo();
                                String hinhanh = USRdao.getConfig(AppConfigString.HinhAnh).getConfigInfo();
                                String dantoc = USRdao.getConfig(AppConfigString.DanToc).getConfigInfo();
                                imgUserURL = USRdao.getConfig(AppConfigString.HinhAnh).getConfigInfo();
                                imgBHYTURL = USRdao.getConfig(AppConfigString.HinhBHYT).getConfigInfo();
                                token = USRdao.getConfig(AppConfigString.Token).getConfigInfo();
                                if(!sdt.isEmpty()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            txtHoTen.setText(hoten);
                                            try{
                                                txtNgaySinh.setText(ngaysinh.split("/")[0]);
                                                txtThangSinh.setText(ngaysinh.split("/")[1]);
                                                txtNamSinh.setText(ngaysinh.split("/")[2]);
                                            }catch (Exception ex){
                                                txtNgaySinh.setText("");
                                                txtThangSinh.setText("");
                                                txtNamSinh.setText("");
                                            }
                                            switch (gioitinh){
                                                case "0" :
                                                    chkNam.setChecked(true);
                                                    chkNu.setChecked(false);
                                                    chkKhac.setChecked(false);
                                                    break;
                                                case "1":
                                                    chkNam.setChecked(false);
                                                    chkNu.setChecked(true);
                                                    chkKhac.setChecked(false);
                                                    break;
                                                case "2":
                                                    chkNam.setChecked(false);
                                                    chkNu.setChecked(false);
                                                    chkKhac.setChecked(true);
                                                    break;
                                            }
                                            for(TinhThanh a : listTinhThanh){
                                                if(a.getMa() == Integer.parseInt(matinh)){
                                                    txtTinhThanh.setText(a.getTen());
                                                    break;
                                                }
                                            }
                                            matinhthanh = matinh;
                                            maquanhuyen = mahuyen;
                                            maphuongxa = maphuong;
                                            maapkhupho = makp;
                                            maquoctich = maquoctichL;
                                            DoDuLieuQuanHuyen(Integer.parseInt(matinh));
                                            DoDuLieuXaPhuong(Integer.parseInt(mahuyen));
                                            DoDuLieuApKhuPho(Integer.parseInt(maphuong));
                                            if(maapkhupho.equals("")){
                                                txtApKhuPho.setEnabled(false);
                                                txtApKhuPho.setText("---");
                                            }
                                            txtDiaChi.setText(sonha);
                                            for(QuocGia q : listQuocGia){
                                                if(maquoctichL.equals(q.getMa()+"")){
                                                    txtQuocTich.setText(q.getTen());
                                                }
                                            }
                                            txtCMND.setText(hochieu);
                                            txtMaBHYT.setText(soBHYT);
                                            txtSDT.setText(sdt);

                                            for(int i=0;i<listDanToc.size();i++){
                                                if(dantoc.equals(listDanToc.get(i).getMa()+"")){
                                                    txtDanToc.select(i);
                                                }
                                            }

                                            ContextWrapper cw = new ContextWrapper(getApplicationContext());
                                            File directory = cw.getDir(AppConfigString.ImageDIR, Context.MODE_PRIVATE);
                                            File UserImage,BHYTImage;
                                            try{
                                                UserImage = new File(directory, AppConfigString.UserImageName);
                                                BHYTImage = new File(directory, AppConfigString.BHYTImageName);

                                            }catch (Exception ex){
                                                UserImage = null;
                                                BHYTImage = null;
                                            }
                                            Picasso.get().load(UserImage).placeholder(R.drawable.avatar_user_empty).into(imgUserAVT);
                                            Picasso.get().load(BHYTImage).placeholder(R.drawable.bhyt).into(imgBHYT);
                                        }
                                    });
                                }
                            }catch (Exception e){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(UpdateUserActivity.this, "Lỗi " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                        }
                    }).start();
                    dialog_loading.dismiss();
                }
            }
        }, 1000);
    }

    private void initView() {
      /*  txtHoTen.setEnabled(false);
        txtNgaySinh.setEnabled(false);
        txtThangSinh.setEnabled(false);
        txtNamSinh.setEnabled(false);
        chkKhac.setEnabled(false);
        chkNam.setEnabled(false);
        chkNu.setEnabled(false);*/
        txtSDT.setEnabled(false);
        txtDanToc.setEnabled(false);
        ViewUpdate.setVisibility(View.VISIBLE);
        ViewRegister.setVisibility(View.GONE);
        viewNewPassword.setVisibility(View.GONE);
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

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                txtNamSinh.setText(year+"");
                txtNgaySinh.setText(dayOfMonth < 10  ?"0"+ dayOfMonth :dayOfMonth+"");
                txtThangSinh.setText(monthOfYear+1 < 10 ? "0"+(monthOfYear+1) : monthOfYear+1+"");
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.setOkText("Chọn");
        datePickerDialog.setCancelText("Hủy");
        datePickerDialog.setLocale(new Locale("vi"));
        datePickerDialog.show(getSupportFragmentManager(), "Chọn ngày sinh");
       /* Calendar c = Calendar.getInstance();
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
      android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(UpdateUserActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new android.app.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                txtNamSinh.setText(year+"");
                txtNgaySinh.setText(dayOfMonth < 10  ?"0"+ dayOfMonth :dayOfMonth+"");
                txtThangSinh.setText(monthOfYear+1 < 10 ? "0"+(monthOfYear+1) : monthOfYear+1+"");
            }
        }, mYear, mMonth, mDay);
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
                int select = -1;
                for (QuanHuyen a: qh) {
                    if(maquanhuyen.equals(a.getMa()+"")){
                        select = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtQuanHuyen.setText(a.getTen());
                            }
                        });
                    }
                    s[i]=a.getTen();
                    i++;
                }
                listQuanHuyen = qh;
                int finalSelect = select;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtQuanHuyen.setItems(s);
                        if(finalSelect > 0){
                            txtQuanHuyen.select(finalSelect);
                        }
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
                int select = -1;
                for (PhuongXa a: px) {
                    if(maphuongxa.equals(a.getMa()+"")){
                        select =i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtXaPhuong.setText(a.getTen());
                            }
                        });
                    }
                    s[i]=a.getTen();
                    i++;
                }
                listPhuongXa = px;
                int finalSelect = select;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        txtXaPhuong.setItems(s);
                        if(finalSelect > 0){
                            txtXaPhuong.select(finalSelect);
                        }
                    }
                });
                Log.d(TAG,"Nhận "+ px.size()+" phường xã");
            }
        }).start();
    }

    private void DoDuLieuApKhuPho(int px){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<KhuPho> kp = (ArrayList<KhuPho>) KPdao.getKhuPhoByPhuongXa(px);
                String[] s = new String[kp.size()];
                int i=0;
                int select = -1;
                for (KhuPho a: kp) {
                    if(maapkhupho.equals(a.getMa()+"")){
                        select = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtApKhuPho.setText(a.getTen());
                            }
                        });
                    }
                    s[i]=a.getTen();
                    i++;
                }
                listApKhuPho = kp;
                int finalSelect = select;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if(kp.size() > 0){
                            txtApKhuPho.setItems(s);
                            if(finalSelect > 0){
                                txtApKhuPho.select(finalSelect);
                            }
                        }else {
                            txtApKhuPho.setEnabled(false);
                        }
                    }
                });
                Log.d(TAG,"Nhận "+ kp.size()+" phường xã");
            }
        }).start();
    }

    ActivityResultLauncher<Intent> launcherImgBHYT =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                if (result.getResultCode() == RESULT_OK) {
                    URIimgBHYT = result.getData().getData();
                    Picasso.get().load(URIimgBHYT).into(imgBHYT);
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                }
            });
    ActivityResultLauncher<Intent> launcherImgUser =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                if (result.getResultCode() == RESULT_OK) {
                    URIimgUser = result.getData().getData();
                    Picasso.get().load(URIimgUser).into(imgUserAVT);
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                }
            });

    private void mapView() {
        imgUser = findViewById(R.id.imgUser);
        imgLogoBVHH = findViewById(R.id.imgLogoBVHH);
        imgUserAVT = findViewById(R.id.imgUserAVT);
        txtNgaySinh = findViewById(R.id.txtNgaySinh);
        txtThangSinh = findViewById(R.id.txtThangSinh);
        txtNamSinh = findViewById(R.id.txtNamSinh);
        imgBHYT = findViewById(R.id.imgBHYT);
        btnThoat = findViewById(R.id.btnThoat);
        txtTinhThanh = findViewById(R.id.txtTinhThanh);
        txtQuanHuyen = findViewById(R.id.txtQuanHuyen);
        txtXaPhuong = findViewById(R.id.txtXaPhuong);
        txtApKhuPho = findViewById(R.id.txtApKhuPho);
        txtQuocTich = findViewById(R.id.txtQuocTich);
        txtDanToc = findViewById(R.id.txtDanToc);
        chkNam = findViewById(R.id.chkNam);
        chkNu = findViewById(R.id.chkNu);
        chkKhac = findViewById(R.id.chkKhac);
        txtNewPassword = findViewById(R.id.txtNewPassword);
        txtReEnterNewPassword = findViewById(R.id.txtReEnterNewPassword);
        btnLuu = findViewById(R.id.btnLuu);
        txtHoTen = findViewById(R.id.txtNhomDV);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtSDT = findViewById(R.id.txtSDT);
        txtCMND = findViewById(R.id.txtCMND);
        txtMaBHYT = findViewById(R.id.txtMaBHYT);
        ViewUpdate = findViewById(R.id.ViewUpdate);
        swDoiMatKhau = findViewById(R.id.swDoiMatKhau);
        ViewRegister = findViewById(R.id.ViewRegister);
        viewNewPassword = findViewById(R.id.viewNewPassword);
        txtOldPassword = findViewById(R.id.txtOldPassword);
    }
}