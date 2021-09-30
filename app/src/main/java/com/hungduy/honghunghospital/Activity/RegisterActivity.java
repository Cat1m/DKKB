package com.hungduy.honghunghospital.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.drjacky.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.getModel.baseGetClass;
import com.hungduy.honghunghospital.Model.getModel.getMaTen;
import com.hungduy.honghunghospital.Model.setModel.setUserModel;
import com.hungduy.honghunghospital.R;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.Console;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import jrizani.jrspinner.JRSpinner;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {
    private ImageView imgLogoBVHH,imgUser,imgBHYT;
    private TextView txtNgaySinh,txtThangSinh,txtNamSinh;
    private EditText txtPassword,txtReEnterPassword,txtHoTen,txtDiaChi,txtSDT,txtCMND,txtMaBHYT;
    private Button btnThoat,btnLuu;
    private JRSpinner txtTinhThanh,txtQuanHuyen,txtXaPhuong,txtApKhuPho,txtQuocTich;
    private RadioButton chkNam,chkNu,chkKhac;

    private ArrayList<getMaTen> listTinhThanh  = new ArrayList<>();
    private ArrayList<getMaTen> listQuanHuyen  = new ArrayList<>();
    private ArrayList<getMaTen> listPhuongXa  = new ArrayList<>();
    private ArrayList<getMaTen> listApKhuPho  = new ArrayList<>();
    private ArrayList<getMaTen> listQuocGia  = new ArrayList<>();

    private Uri URIimgUser,URIimgBHYT;

    private Drawable shape_edittext_error ;
    private Drawable shape_edittext_have_focus ;

    private String matinhthanh = "";
    private String maquanhuyen = "";
    private String maphuongxa = "";
    private String maapkhupho = "";
    private String maquoctich = "";

    private String gioitinh = "0";

    private String imgUserURL = "";
    private String imgBHYTURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        mapView();
        shape_edittext_error = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.shape_edittext_error);
        shape_edittext_have_focus = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.shape_edittext_have_focus);
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
                ThongBao(RegisterActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error);
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                ThongBao(RegisterActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error);
            }
        });

        mAPIService.getQuocGia(APIKey).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("OK")){
                        getMaTen[] g = new Gson().fromJson(response.body().getData(), getMaTen[].class);
                        if(g.length>0){
                            listQuocGia = new ArrayList<getMaTen>(Arrays.asList(g));
                            Log.d(TAG,"Nhận "+ listQuocGia.size()+" tỉnh thành");
                            String[] s = new String[listQuocGia.size()];
                            int i=0;
                            int vitriVN = 0;
                            for (getMaTen a: listQuocGia) {
                                s[i]=a.getTen();

                                if(a.getTen().equals("Việt Nam")){
                                    vitriVN = i;
                                    maquoctich = a.getMa();
                                }
                                i++;

                            }
                            txtQuocTich.setItems(s);
                            txtQuocTich.select(vitriVN);
                            return;
                        }
                    }
                }else{
                    //code != 200
                }
                ThongBao(RegisterActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error);
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                ThongBao(RegisterActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error);
            }
        });

        txtTinhThanh.setItems(new String[0]);
        txtQuanHuyen.setItems(new String[0]);
        txtXaPhuong.setItems(new String[0]);
        txtApKhuPho.setItems(new String[0]);

       // Picasso.get().load(R.drawable.icon_photo).resize(30,30).centerCrop().into(imgBHYT);
        imgBHYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(RegisterActivity.this)
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

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(RegisterActivity.this)
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

        imgLogoBVHH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

        txtQuocTich.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String code = listQuocGia.get(position).getMa();
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

        txtReEnterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!txtPassword.getText().toString().equals(txtReEnterPassword.getText().toString())){
                    txtReEnterPassword.setBackground(shape_edittext_error);
                }else{
                    txtReEnterPassword.setBackground(shape_edittext_have_focus);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtPassword.getText().toString().isEmpty()){
                    txtPassword.setBackground(shape_edittext_error);
                }else{
                    txtPassword.setBackground(shape_edittext_have_focus);
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
                if(txtHoTen.getText().toString().isEmpty() ||txtPassword.getText().toString().isEmpty()
                        || txtReEnterPassword.getText().toString().isEmpty()
                        || txtSDT.getText().toString().isEmpty() || matinhthanh.isEmpty() || maquanhuyen.isEmpty()
                        || maphuongxa.isEmpty()){
                    if(txtHoTen.getText().toString().isEmpty()){
                        txtHoTen.setBackground(shape_edittext_error);
                    }else{
                        txtHoTen.setBackground(shape_edittext_have_focus);
                    }

                    if(txtPassword.getText().toString().isEmpty()){
                        txtPassword.setBackground(shape_edittext_error);
                    }else{
                        txtPassword.setBackground(shape_edittext_have_focus);
                    }

                    if(txtReEnterPassword.getText().toString().isEmpty()){
                        txtReEnterPassword.setBackground(shape_edittext_error);
                    }else{
                        txtReEnterPassword.setBackground(shape_edittext_have_focus);
                    }

                    if(txtQuocTich.getText().toString().isEmpty()){
                        txtQuocTich.setBackground(shape_edittext_error);
                    }else{
                        txtQuocTich.setBackground(shape_edittext_have_focus);
                    }

                    if(txtSDT.getText().toString().isEmpty()){
                        txtSDT.setBackground(shape_edittext_error);
                    }else{
                        if(txtSDT.getText().toString().length() > 11){
                            txtSDT.setBackground(shape_edittext_error);
                            ThongBao(RegisterActivity.this,"Đã có lỗi xảy ra",
                                    "Số điện thoại bạn nhập không đúng địng dạng !!!",R.drawable.connection_error);
                            return;
                        }
                        if(txtSDT.getText().toString().length() < 10){
                            txtSDT.setBackground(shape_edittext_error);
                            ThongBao(RegisterActivity.this,"Đã có lỗi xảy ra",
                                    "Số điện thoại bạn nhập không đúng địng dạng !!!",R.drawable.connection_error);
                            return;
                        }
                        txtSDT.setBackground(shape_edittext_have_focus);
                    }
                    ThongBao(RegisterActivity.this,"Đã có lỗi xảy ra",
                            "Bạn chưa nhập đủ thông tin. Những mục có dấu * là bắt buộc!",R.drawable.connection_error);
                }else{
                    btnLuu.setEnabled(false);

                    if(URIimgUser != null)
                    {
                        File file = new File(URIimgUser.getPath());
                        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("files[0]", file.getName(), requestFile);
                        mAPIService.ImageUploadFile(APIKey, body).enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                String[] url = new Gson().fromJson(response.body().getData(), String[].class);
                                imgUserURL = url[0];
                                Log.d(TAG, response.body().getStatus() + " " + response.body().getMessenge() + " " + url[0]);
                            }

                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t) {

                            }
                        });
                    }

                    if(URIimgBHYT != null)
                    {
                        File file = new File(URIimgBHYT.getPath());
                        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("files[0]", file.getName(), requestFile);
                        mAPIService.ImageUploadFile(APIKey, body).enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                String[] url = new Gson().fromJson(response.body().getData(), String[].class);
                                imgBHYTURL = url[0];
                                Log.d(TAG, response.body().getStatus() + " " + response.body().getMessenge() + " " + url[0]);
                            }

                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t) {

                            }
                        });
                    }

                    Thread setUser = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            setUserModel u = new setUserModel(txtHoTen.getText().toString(),txtNgaySinh.getText().toString()+
                                    "/"+txtThangSinh.getText().toString()+"/"+txtNamSinh.getText().toString(),gioitinh,matinhthanh
                                    ,maquanhuyen,maphuongxa,maapkhupho,maquoctich,txtSDT.getText().toString(),txtPassword.getText().toString(),
                                    txtDiaChi.getText().toString(),txtCMND.getText().toString(),txtMaBHYT.getText().toString(),imgUserURL,imgBHYTURL);
                            mAPIService.setUser(APIKey,u).enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    if(response.isSuccessful()){
                                        if(response.body().getStatus().equals("OK")){
                                            //to do Update Image
                                            ThongBao(RegisterActivity.this, "Thành công", "Đăng kí thành công !!",
                                                    R.drawable.connection_error, new FancyGifDialogListener() {
                                                        @Override
                                                        public void OnClick() {
                                                            finish();
                                                        }
                                                    });
                                        }else{
                                            ThongBao(RegisterActivity.this, "Đã có lỗi xảy ra", response.body().getMessenge()
                                                    ,R.drawable.connection_error);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseModel> call, Throwable t) {

                                }
                            });
                        }
                    });

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (URIimgUser != null && imgUserURL =="") {
                            }
                            while (URIimgBHYT != null && imgBHYTURL =="") {
                            }
                            setUser.start();
                        }
                    }).start();

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
        DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new DatePickerDialog.OnDateSetListener() {
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
                ThongBao(RegisterActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error);
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                ThongBao(RegisterActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error);
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
                ThongBao(RegisterActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error);
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                ThongBao(RegisterActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error);
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
                ThongBao(RegisterActivity.this, "Có lỗi xảy ra", "Đã có lỗi xảy ra vui lòng thử lại", R.drawable.connection_error);
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                ThongBao(RegisterActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error);
            }
        });
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
                    Picasso.get().load(URIimgUser).into(imgUser);
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                }
            });

    private void mapView() {
        imgLogoBVHH = findViewById(R.id.imgLogoBVHH);
        imgUser = findViewById(R.id.imgUser);
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
        chkNam = findViewById(R.id.chkNam);
        chkNu = findViewById(R.id.chkNu);
        chkKhac = findViewById(R.id.chkKhac);
        txtPassword = findViewById(R.id.txtPassword);
        txtReEnterPassword = findViewById(R.id.txtReEnterPassword);
        btnLuu = findViewById(R.id.btnLuu);
        txtHoTen = findViewById(R.id.txtHoTen);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtSDT = findViewById(R.id.txtSDT);
        txtCMND = findViewById(R.id.txtCMND);
        txtMaBHYT= findViewById(R.id.txtMaBHYT);
    }
}