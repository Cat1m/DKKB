package com.hungduy.honghunghospital.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hungduy.honghunghospital.Activity.KhaiBaoYTeActivity;
import com.hungduy.honghunghospital.Adapter.KhaiBaoYTeAdapter;
import com.hungduy.honghunghospital.Adapter.TinTucAdapter;
import com.hungduy.honghunghospital.Database.Model.DichVu;
import com.hungduy.honghunghospital.Database.Model.LoaiDichVu;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.extModel.CauHoiKhaiBaoYTeEXT;
import com.hungduy.honghunghospital.Model.getModel.baseGetClass;
import com.hungduy.honghunghospital.Model.getModel.getCauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.Model.getModel.getDichVu;
import com.hungduy.honghunghospital.Model.getModel.getTinTuc;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.CallbackResponse;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import jrizani.jrspinner.JRSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DichVuFragment extends BaseFragment {
    private JRSpinner txtNhomDV,txtDichVu;
    private EditText txtGiaDichVu;
    private ArrayList<LoaiDichVu> loaiDichVus;
    private ArrayList<DichVu> dichVus;
    private boolean connected = true;
    private String donvi = "";


    public DichVuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView(view);

        txtNhomDV.setItems(new String[0]);
        txtDichVu.setItems(new String[0]);
        loaiDichVus = new ArrayList<>();
        dichVus = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if(loaiDichVuDAO.getAll().size() > 0){
                    String[] loaiDV = new String[loaiDichVuDAO.getAll().size()];
                    int i=0;
                    loaiDichVus.clear();
                    for(LoaiDichVu ldv : loaiDichVuDAO.getAll()){
                        loaiDV[i] = ldv.getTen();
                        loaiDichVus.add(ldv);
                        i++;
                    }
                    txtNhomDV.setItems(loaiDV);
                }
            }
        }).start();

        txtNhomDV.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int manhomdv = loaiDichVus.get(position).getMa();
                        donvi = loaiDichVus.get(position).getDonVi();
                        if(dichvuDAO.getDichVuByNhom(manhomdv).size() > 0){
                            dichVus.clear();
                            String[] dv = new String[dichvuDAO.getDichVuByNhom(manhomdv).size()];
                            int i=0;
                            for(DichVu dz : dichvuDAO.getDichVuByNhom(manhomdv)){
                                dv[i] = dz.getTen();
                                dichVus.add(dz);
                                i++;
                            }
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    txtDichVu.setText("Chọn dịch vụ");
                                    txtGiaDichVu.setText("Giá dịch vụ");
                                    txtDichVu.setItems(dv);
                                }
                            });
                        }
                    }
                }).start();
            }
        });
      //  txtDichVu.setText("");
        txtDichVu.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mAPIService.getDichVuByMa(APIKey, new baseGetClass(dichVus.get(position).getMa()+"")).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        try{
                            getDichVu dv = new Gson().fromJson(response.body().getData(),getDichVu.class);
                            if(dv != null){
                                Log.d(TAG, "onResponse: Data Internet");
                                String money = "";
                                NumberFormat formatter = new DecimalFormat("#,###");
                                try {
                                    money =  formatter.format(Double.parseDouble(dv.getGia())).toString();
                                }catch (Exception e){
                                    money = dv.getGia();
                                }
                                txtGiaDichVu.setText(money+ "VNĐ/"+ donvi);
                            }
                        }catch (Exception ex){
                            Log.d(TAG, "onResponse: Internet Error Datalocal");
                            Toast.makeText(getContext(),"Kết nối máy chủ lỗi. Bạn đang xem dữ liệu trên máy!!!",Toast.LENGTH_SHORT).show();
                            String money = "";
                            NumberFormat formatter = new DecimalFormat("#,###");
                            try {
                                money =  formatter.format(Double.parseDouble(dichVus.get(position).getGia())).toString();
                            }catch (Exception e){
                                money = dichVus.get(position).getGia();
                            }
                            txtGiaDichVu.setText(money+ "VNĐ/" +donvi);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.d(TAG, "onResponse: Internet Error Datalocal");
                        Toast.makeText(getContext(),"Kết nối máy chủ lỗi. Bạn đang xem dữ liệu trên máy!!!",Toast.LENGTH_SHORT).show();
                        String money = "";
                        NumberFormat formatter = new DecimalFormat("#,###");
                        try {
                            money =  formatter.format(Double.parseDouble(dichVus.get(position).getGia())).toString();
                        }catch (Exception e){
                            money = dichVus.get(position).getGia();
                        }
                        txtGiaDichVu.setText(money+ "VNĐ/" +donvi);
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        txtNhomDV.setText("Chọn nhóm dịch vụ");
        txtDichVu.setText("Chọn dịch vụ");
        txtGiaDichVu.setText("Giá dịch vụ");
    }

    private void mapView(View v) {
        txtNhomDV = v.findViewById(R.id.txtNhomDV);
        txtDichVu = v.findViewById(R.id.txtDichVu);
        txtGiaDichVu = v.findViewById(R.id.txtGiaDichVu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dich_vu, container, false);
    }
}