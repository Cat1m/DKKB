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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.getModel.getOTPModel;
import com.hungduy.honghunghospital.R;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordFragment extends BaseFragment {

    private EditText txtSDT,txtNewPassword,txtReEnterPassword,txtOTPCode;
    private Button btnLuu;
    private ConstraintLayout viewGetOTP,viewReset;

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
        viewReset.setVisibility(View.INVISIBLE);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAPIService.getOTP(APIKey,new getOTPModel(txtSDT.getText().toString())).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if(response.isSuccessful()){
                            if(response.body().getStatus().equals("OK")){
                                viewGetOTP.setVisibility(View.INVISIBLE);
                                viewReset.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {

                    }
                });


            }
        });




    }

    private void mapView(View v) {
        viewGetOTP = v.findViewById(R.id.viewGetOTP);
        viewReset = v.findViewById(R.id.viewReset);
        btnLuu = v.findViewById(R.id.btnLuu);
        txtSDT = v.findViewById(R.id.txtSDT);
        txtNewPassword = v.findViewById(R.id.txtNewPassword);
        txtReEnterPassword = v.findViewById(R.id.txtReEnterPassword);
        txtOTPCode= v.findViewById(R.id.txtOTPCode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forget_password, container, false);
    }
}