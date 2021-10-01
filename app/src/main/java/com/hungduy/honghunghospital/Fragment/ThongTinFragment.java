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

import com.google.gson.Gson;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.getModel.baseGetClass;
import com.hungduy.honghunghospital.Model.getModel.getLichLamViecBS;
import com.hungduy.honghunghospital.Model.getModel.getMaTen;
import com.hungduy.honghunghospital.Model.getModel.getThongTinBS;
import com.hungduy.honghunghospital.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import jrizani.jrspinner.JRSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongTinFragment extends BaseFragment {

    public ThongTinFragment() {
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


    }

    private void mapView(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_thong_tin, container, false);
    }
}