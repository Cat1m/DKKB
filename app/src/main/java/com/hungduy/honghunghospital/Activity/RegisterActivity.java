package com.hungduy.honghunghospital.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.drjacky.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.getModel.baseGetClass;
import com.hungduy.honghunghospital.Model.getModel.getMaTen;
import com.hungduy.honghunghospital.Model.getModel.get_Token_Ma;
import com.hungduy.honghunghospital.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import jrizani.jrspinner.JRSpinner;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {
    private ImageView imgLogoBVHH,imgUser,imgBHYT;
    private TextView txtHoTen,txtNgaySinh,txtThangSinh,txtNamSinh;
    private Button btnThoat;
    private JRSpinner txtTinhThanh,txtQuanHuyen,txtXaPhuong,txtApKhuPho;
    private RadioButton chkNam,chkNu,chkKhac;

    private ArrayList<getMaTen> listTinhThanh  = new ArrayList<>();
    private ArrayList<getMaTen> listQuanHuyen  = new ArrayList<>();
    private ArrayList<getMaTen> listPhuongXa  = new ArrayList<>();
    private ArrayList<getMaTen> listApKhuPho  = new ArrayList<>();


    private String matinhthanh = "";
    private String maquanhuyen = "";
    private String maphuongxa = "";
    private String maapkhupho = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        mapView();

        mAPIService.getTinhThanh(new baseGetClass(APIKey)).enqueue(new Callback<ResponseModel>() {
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
            }
        });
        chkNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupCheckBox(chkNu);
            }
        });
        chkNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupCheckBox(chkNam);
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
        datePickerDialog.setTitle("Chọn ngày tháng năm sinh");
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                txtNamSinh.setText("");
                txtNgaySinh.setText("");
                txtThangSinh.setText("");
            }
        });
        datePickerDialog.show();
    }

    private void DoDuLieuQuanHuyen(String matinhthanh){
        mAPIService.getQuanHuyen(new get_Token_Ma(APIKey,matinhthanh)).enqueue(new Callback<ResponseModel>() {
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
        mAPIService.getPhuongXa(new get_Token_Ma(APIKey,maquanhuyen)).enqueue(new Callback<ResponseModel>() {
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
        mAPIService.getApKhuPho(new get_Token_Ma(APIKey,maapkhupho)).enqueue(new Callback<ResponseModel>() {
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
                    Uri uri = result.getData().getData();
                    Picasso.get().load(uri).into(imgBHYT);
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                }
            });
    ActivityResultLauncher<Intent> launcherImgUser =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();
                    Picasso.get().load(uri).into(imgUser);
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                }
            });

    private void mapView() {
        imgLogoBVHH = findViewById(R.id.imgLogoBVHH);
        imgUser = findViewById(R.id.imgUser);
        txtHoTen = findViewById(R.id.txtHoTen);;
        txtNgaySinh = findViewById(R.id.txtNgaySinh);
        txtThangSinh = findViewById(R.id.txtThangSinh);
        txtNamSinh = findViewById(R.id.txtNamSinh);
        imgBHYT = findViewById(R.id.imgBHYT);
        btnThoat = findViewById(R.id.btnThoat);
        txtTinhThanh = findViewById(R.id.txtTinhThanh);
        txtQuanHuyen = findViewById(R.id.txtQuanHuyen);
        txtXaPhuong = findViewById(R.id.txtXaPhuong);
        txtApKhuPho = findViewById(R.id.txtApKhuPho);
        chkNam = findViewById(R.id.chkNam);
        chkNu = findViewById(R.id.chkNu);
        chkKhac = findViewById(R.id.chkKhac);
    }
}