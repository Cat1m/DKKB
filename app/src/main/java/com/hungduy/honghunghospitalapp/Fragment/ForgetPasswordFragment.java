package com.hungduy.honghunghospitalapp.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hungduy.honghunghospitalapp.Model.ResponseModel;
import com.hungduy.honghunghospitalapp.Model.getModel.getOTPModel;
import com.hungduy.honghunghospitalapp.Model.setModel.setNewPassword;
import com.hungduy.honghunghospitalapp.R;
import com.hungduy.honghunghospitalapp.Utility.CallbackResponse;
import com.hungduy.honghunghospitalapp.Utility.FragmentUtils;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Response;

public class ForgetPasswordFragment extends BaseFragment {

    private EditText txtSDT,txtNewPassword,txtReEnterPassword,txtOTPCode;
    private Button btnLuu,btnResetPassword;
    private ConstraintLayout viewGetOTP,viewReset;

    private String phoneNumber = "";

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
                dialog_loading.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if(dialog_loading.isShowing()){
                                    dialog_loading.dismiss();
                                }
                            }
                        });
                    }
                },2000);

                mAPIService.getOTP(APIKey,new getOTPModel(txtSDT.getText().toString())).enqueue(new CallbackResponse(getActivity()){
                    @Override
                    public void success(Response<ResponseModel> response) {
                        phoneNumber = txtSDT.getText().toString();
                        viewGetOTP.setVisibility(View.INVISIBLE);
                        viewReset.setVisibility(View.VISIBLE);
                        dialog_loading.dismiss();
                    }

                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        super.onResponse(call, response);
                        if(response.isSuccessful() && response.body().getStatus().equals("Exist")){
                            phoneNumber = txtSDT.getText().toString();
                            viewGetOTP.setVisibility(View.INVISIBLE);
                            viewReset.setVisibility(View.VISIBLE);
                            dialog_loading.dismiss();
                        }
                    }
                });


            }
        });
        txtOTPCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!txtOTPCode.getText().toString().isEmpty()){
                    txtOTPCode.setBackground(shape_edittext_have_focus);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtNewPassword.getText().toString().isEmpty()){
                    txtNewPassword.setBackground(shape_edittext_error);
                    txtReEnterPassword.setBackground(shape_edittext_error);
                }else{
                    txtNewPassword.setBackground(shape_edittext_have_focus);
                    txtReEnterPassword.setBackground(shape_edittext_have_focus);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtReEnterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!txtReEnterPassword.getText().toString().equals(txtNewPassword.getText().toString())){
                    txtReEnterPassword.setBackground(shape_edittext_error);
                }else{
                    txtReEnterPassword.setBackground(shape_edittext_have_focus);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtReEnterPassword.getText().toString().equals(txtNewPassword.getText().toString()) && !txtOTPCode.getText().toString().isEmpty())
                {
                    mAPIService.ResetPassword(APIKey,new setNewPassword(phoneNumber,txtOTPCode.getText().toString(),
                            txtReEnterPassword.getText().toString())).enqueue(new CallbackResponse(getActivity()){
                        @Override
                        public void success(Response<ResponseModel> response) {
                            ThongBao(getActivity(), "Thành công", "Mật khẩu đã được khôi phục thành công!",
                                    R.drawable.connection_error, new FancyGifDialogListener() {
                                        @Override
                                        public void OnClick() {
                                            LoginFragment login = new LoginFragment();
                                            FragmentUtils.replaceFragment(R.id.svTrangChu,getActivity().getSupportFragmentManager(),login,"");
                                        }
                                    });
                        }
                    });
                }else {
                    txtOTPCode.setBackground(shape_edittext_error);
                }
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
        btnResetPassword = v.findViewById(R.id.btnResetPassword);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forget_password, container, false);
    }
}