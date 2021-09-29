package com.hungduy.honghunghospital.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
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
    private AutoCompleteTextView txtTinhThanh,txtQuanHuyen;
    private ArrayList<getMaTen> listTinhThanh  = new ArrayList<>();
    private ArrayList<getMaTen> listQuanHuyen  = new ArrayList<>();

    private String matinhthanh = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        mapView();
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

        mAPIService.getTinhThanh(new baseGetClass(APIKey)).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("OK")){
                        getMaTen[] g = new Gson().fromJson(response.body().getData(), getMaTen[].class);
                        if(g.length>0){
                            listTinhThanh = new ArrayList<getMaTen>(Arrays.asList(g));
                            ArrayList<String> s = new ArrayList<>();
                            Log.d(TAG,"Nhận "+ listTinhThanh.size()+" tỉnh thành");
                            for (getMaTen a: listTinhThanh) {
                                s.add(a.getTen());
                            }
                            ArrayAdapter<String> adpt = new ArrayAdapter<String>(RegisterActivity.this,
                                    android.R.layout.select_dialog_item,s);
                            txtTinhThanh.setThreshold(0);
                            txtTinhThanh.setAdapter(adpt);
                            return;
                        }
                    }
                }else{
                    //code != 200
                }
                ThongBao(RegisterActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error,null);
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                ThongBao(RegisterActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error,null);
            }
        });

        txtTinhThanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String ten = (String)parent.getItemAtPosition(position);
                matinhthanh = listTinhThanh.stream().filter(t -> t.getTen().equals(ten)).findFirst().get().getMa();
                Log.d(TAG,  matinhthanh +" - " + ten);
                if(matinhthanh != null && matinhthanh != ""){
                    DoDuLieuQuanHuyen(matinhthanh);
                    txtQuanHuyen.setText("");
                }
            }
        });

        txtQuanHuyen.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
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
                            ArrayList<String> s = new ArrayList<>();
                            Log.d(TAG,"Nhận "+ listQuanHuyen.size()+" quận huyện");
                            for (getMaTen a: listQuanHuyen) {
                                s.add(a.getTen());
                            }
                            ArrayAdapter<String> adpt = new ArrayAdapter<String>(RegisterActivity.this,
                                    android.R.layout.select_dialog_item,s);
                            txtQuanHuyen.setThreshold(0);
                            txtQuanHuyen.setAdapter(adpt);
                            return;
                        }
                    }
                }else{
                    //code != 200
                }
                ThongBao(RegisterActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error,null);
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                ThongBao(RegisterActivity.this,"Có lỗi xảy ra","Đã có lỗi xảy ra vui lòng thử lại",R.drawable.connection_error,null);
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
    }
}