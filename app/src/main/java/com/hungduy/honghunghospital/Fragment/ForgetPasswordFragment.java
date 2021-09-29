package com.hungduy.honghunghospital.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hungduy.honghunghospital.Activity.ResetPasswordActivity;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.getModel.getOTPModel;
import com.hungduy.honghunghospital.R;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordFragment extends BaseFragment {

    private EditText txtData;
    private Button btnNhapOTP;


    public ForgetPasswordFragment() {
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
        btnNhapOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAPIService.getOTP(new getOTPModel(APIKey,txtData.getText().toString())).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {

                    }
                });



                Intent i = new Intent(getContext(), ResetPasswordActivity.class);
                startActivity(i);
            }
        });
    }

    private void mapView(View v) {
        txtData = v.findViewById(R.id.txtData);
        btnNhapOTP = v.findViewById(R.id.btnNhapOTP);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forget_password, container, false);
    }
}