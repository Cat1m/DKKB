package com.hungduy.honghunghospital.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hungduy.honghunghospital.Activity.MainActivity;
import com.hungduy.honghunghospital.Activity.RegisterActivity;
import com.hungduy.honghunghospital.Model.LoginModel;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.getModel.UserModel;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.FragmentUtils;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends BaseFragment {

    private TextView txtRegister,txtForgetPassword;
    private EditText txtUsername,txtPassword;
    private Button btnLogin;

    private String usernamePreferences,passwordPreferences;
    public LoginFragment() {
    }


    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgetPasswordFragment logined = new ForgetPasswordFragment();
                FragmentUtils.replaceFragment(R.id.svTrangChu,getActivity().getSupportFragmentManager(),logined,"");
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtUsername.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty()){
                    ThongBao(getActivity(),"Đã có lỗi xảy ra","Vui lòng nhập số điện thoại và mật khẩu !!!",
                            R.drawable.connection_error);
                    return;
                }else{
                    Login(txtUsername.getText().toString(),txtPassword.getText().toString());
                }
            }
        });

        usernamePreferences = getStringPreferences(preferences,"username");
        passwordPreferences = getStringPreferences(preferences,"password");

        if(usernamePreferences != ""&& passwordPreferences != ""){
            Login(usernamePreferences,passwordPreferences);
        }

    }

    private void Login(String username,String password){
        mAPIService.login(APIKey,new LoginModel(username,password)).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()){
                    if (response.body().getStatus().equals("OK")){
                        UserModel u = new Gson().fromJson(response.body().getData(), UserModel.class);
                        if(u != null){
                            setStringPreferences(preferences,"username",username);
                            setStringPreferences(preferences,"password",password);

                            HomeLoginedFragment logined = new HomeLoginedFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("FullName",u.getFullName());
                            bundle.putString("urlImage",u.getUrlImage());
                            bundle.putString("token",u.getToken());

                            MainActivity activity = (MainActivity) getActivity();
                            activity.LoginSuccess(u.getToken(),u.getFullName(),u.getUrlImage());

                            logined.setArguments(bundle);
                            FragmentUtils.replaceFragment(R.id.svTrangChu,getActivity().getSupportFragmentManager(),logined,"");

                        }
                    }else{
                        ThongBao(getActivity(),"Đã có lỗi xảy ra",response.body().getMessenge(),
                                R.drawable.connection_error);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }

    private void mapView(View v) {
        txtRegister = v.findViewById(R.id.txtRegister);
        txtForgetPassword = v.findViewById(R.id.txtForgetPassword);
        btnLogin = v.findViewById(R.id.btnLogin);
        txtUsername = v.findViewById(R.id.txtUsername);
        txtPassword = v.findViewById(R.id.txtPassword);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
}