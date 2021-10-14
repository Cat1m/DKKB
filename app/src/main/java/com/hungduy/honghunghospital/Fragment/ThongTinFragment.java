package com.hungduy.honghunghospital.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hungduy.honghunghospital.Adapter.TinTucAdapter;
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
    private ArrayList<getTinTuc> getTinTucs= new ArrayList<>();
    private RecyclerView rcViewTinTuc;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        TinTucAdapter tinTucADT= new TinTucAdapter(getTinTucs,getContext(), getActivity());
        rcViewTinTuc.setAdapter(tinTucADT);
        rcViewTinTuc.setLayoutManager(linearLayoutManager);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getTinTucs.clear();
                if(tinTucDAO.getAll().size() > 0){
                    for(TinTuc a : tinTucDAO.getAll()){
                        getTinTucs.add(new getTinTuc(a.getID(),a.getTitle(),a.getMoTa(),a.getURL()));
                        tinTucADT.notifyDataSetChanged();
                    }
                    Log.d(TAG,"localDATA");
                }else{
                    mAPIService.getTinTuc(APIKey).enqueue(new CallbackResponse(getActivity()){

                        @Override
                        public void success(Response<ResponseModel> response) {
                            super.success(response);
                            getTinTuc[] tinTucs = new Gson().fromJson(response.body().getData(),getTinTuc[].class);
                            if(tinTucs.length > 0){
                                int i=0;
                                for (getTinTuc a: tinTucs) {
                                    getTinTucs.add(a);
                                    tinTucADT.notifyDataSetChanged();
                                }
                            }
                        }
                    });
                }
            }
        }).start();



    }

    private void mapView(View view) {
        rcViewTinTuc = view.findViewById(R.id.rcViewTinTuc);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_thong_tin, container, false);
    }
}