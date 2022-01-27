package com.hungduy.honghunghospital.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hungduy.honghunghospital.Activity.WebviewActivity;
import com.hungduy.honghunghospital.Adapter.TinTucAdapter;
import com.hungduy.honghunghospital.Adapter.TinTucHorizontalAdapter;
import com.hungduy.honghunghospital.Database.Model.TinTuc;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.getModel.baseGetClass;
import com.hungduy.honghunghospital.Model.getModel.getLichLamViecBS;
import com.hungduy.honghunghospital.Model.getModel.getMaTen;
import com.hungduy.honghunghospital.Model.getModel.getThongTinBS;
import com.hungduy.honghunghospital.Model.getModel.getTinTuc;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.CallbackResponse;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import jrizani.jrspinner.JRSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongTinFragment extends BaseFragment {
    private ArrayList<getTinTuc> getTinTucChuongTrinhUuDai= new ArrayList<>();
    private ArrayList<getTinTuc> getTinTucThongBaoQuyDinh= new ArrayList<>();
    private ArrayList<getTinTuc> getTinTucKhac= new ArrayList<>();
    private RecyclerView rcChuongTrinh,rcThongBaoQuyDinh,rcThongBaoKhac;
    private TextView txtThongBao,txtTinKhac;
    public ThongTinFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView(view);

        LinearLayoutManager linearLayoutManagerHo = new LinearLayoutManager(getContext());
        linearLayoutManagerHo.setOrientation(RecyclerView.HORIZONTAL);
        TinTucHorizontalAdapter tinTucHoADT= new TinTucHorizontalAdapter(getTinTucChuongTrinhUuDai,getContext(), getActivity());
        rcChuongTrinh.setAdapter(tinTucHoADT);
        rcChuongTrinh.setLayoutManager(linearLayoutManagerHo);

        LinearLayoutManager layoutQD = new LinearLayoutManager(getContext());
        TinTucAdapter QuyDinhADT= new TinTucAdapter(getTinTucThongBaoQuyDinh,getContext(), getActivity());
        rcThongBaoQuyDinh.setAdapter(QuyDinhADT);
        rcThongBaoQuyDinh.setLayoutManager(layoutQD);


        LinearLayoutManager layoutKhac = new LinearLayoutManager(getContext());
        TinTucAdapter KhacADT= new TinTucAdapter(getTinTucKhac,getContext(), getActivity());
        rcThongBaoKhac.setAdapter(KhacADT);
        rcThongBaoKhac.setLayoutManager(layoutKhac);


      /*  new Thread(new Runnable() {
            @Override
            public void run() {
                getTinTucs.clear();
                getTinTucChuongTrinhUuDai.clear();
                if(tinTucDAO.getAll().size() > 0){
                    for(TinTuc a : tinTucDAO.getAll()){
                        getTinTucs.add(new getTinTuc(a.getID(),a.getLoai(),a.getTitle(),a.getMoTa(),a.getURL()));
                        if(a.getLoai() == 4){
                            getTinTucChuongTrinhUuDai.add(new getTinTuc(a.getID(),a.getLoai(),a.getTitle(),a.getMoTa(),a.getURL()));
                            tinTucHoADT.notifyDataSetChanged();
                        }
                        if(a.getLoai() == 1){
                            getTinTucThongBaoQuyDinh.add(new getTinTuc(a.getID(),a.getLoai(),a.getTitle(),a.getMoTa(),a.getURL()));
                            QuyDinhADT.notifyDataSetChanged();
                        }
                        tinTucADT.notifyDataSetChanged();
                    }
                    Log.d(TAG,"localDATA");
                }else{

                }
            }
        });*/ // code này đang lỗi

        mAPIService.getTinTuc(APIKey).enqueue(new CallbackResponse(getActivity()){
            @Override
            public void success(Response<ResponseModel> response) {
                try{
                    getTinTuc[] tinTucs = new Gson().fromJson(response.body().getData(),getTinTuc[].class);
                    if(tinTucs.length > 0){
                        int i=0;
                        getTinTucChuongTrinhUuDai.clear();
                        getTinTucThongBaoQuyDinh.clear();
                        getTinTucKhac.clear();
                        for (getTinTuc a: tinTucs) {
                            if(a.getLoai() == 4){
                                getTinTucChuongTrinhUuDai.add(a);
                                tinTucHoADT.notifyDataSetChanged();
                            }
                            if(a.getLoai() == 1){
                                getTinTucThongBaoQuyDinh.add(a);
                                QuyDinhADT.notifyDataSetChanged();
                            }
                            if(a.getLoai() == 3){
                                getTinTucKhac.add(a);
                                KhacADT.notifyDataSetChanged();
                            }
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Đã có lỗi khi lấy tin tức !!!", Toast.LENGTH_SHORT).show();
                }
                
            }
        });
        txtThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("url","https://honghunghospital.com.vn/category/tin-tuc/thong-bao/");
                startActivity(intent);
            }
        });
        txtTinKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("url","https://honghunghospital.com.vn/category/tin-tuc/");
                startActivity(intent);
            }
        });

    }

    private void mapView(View view) {
        rcThongBaoKhac = view.findViewById(R.id.rcThongBaoKhac);
        rcChuongTrinh = view.findViewById(R.id.rcChuongTrinh);
        rcThongBaoQuyDinh = view.findViewById(R.id.rcThongBaoQuyDinh);
        txtThongBao = view.findViewById(R.id.txtThongBao);
        txtTinKhac = view.findViewById(R.id.txtTinKhac);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_thong_tin, container, false);
    }
}