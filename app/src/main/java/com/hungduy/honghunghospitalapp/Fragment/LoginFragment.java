package com.hungduy.honghunghospitalapp.Fragment;

import static com.hungduy.honghunghospitalapp.Utility.UtilityHHH.md5;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.hungduy.honghunghospitalapp.Activity.MainActivity;
import com.hungduy.honghunghospitalapp.Activity.RegisterActivity;
import com.hungduy.honghunghospitalapp.Model.LoginModel;
import com.hungduy.honghunghospitalapp.Model.ResponseModel;
import com.hungduy.honghunghospitalapp.Model.getModel.UserModel;
import com.hungduy.honghunghospitalapp.Model.getModel.dataBaseToken;
import com.hungduy.honghunghospitalapp.R;
import com.hungduy.honghunghospitalapp.Utility.CallbackResponse;
import com.hungduy.honghunghospitalapp.Utility.FragmentUtils;

import org.jetbrains.annotations.NotNull;

import retrofit2.Response;

public class LoginFragment extends BaseFragment {

    private TextView txtRegister,txtForgetPassword;
    private EditText txtUsername,txtPassword;
    private Button btnLogin;
    private String tokenFCM,tokenFiAM;
    private String usernamePreferences,passwordPreferences;

    public LoginFragment() {
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
        tokenFCM = getStringPreferences(preferences,"fcm");
        tokenFiAM = getStringPreferences(preferences,"fiam");
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
            dialog_loading.show();
        }

    }

    public void RetryLogin(){
        if(usernamePreferences != ""&& passwordPreferences != ""){
            Login(usernamePreferences,passwordPreferences);
            dialog_loading.show();
        }
    }

    private void Login(String username,String password){
        mAPIService.login(APIKey,new LoginModel(username,password)).enqueue(new CallbackResponse(getActivity()){
            @Override
            public void success(Response<ResponseModel> response) {
                if (response.body().getStatus().equals("OK")){
                    handleWriteDB(username);
                    dialog_loading.show();
                    MainActivity activity = (MainActivity) getActivity();
                    UserModel u = new Gson().fromJson(response.body().getData(), UserModel.class);
                    if(u != null){
                        try {
                            setStringPreferences(preferences, "username", username);
                            setStringPreferences(preferences, "password", password);

                            HomeLoginedFragment logined = new HomeLoginedFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("FullName",u.getFullName());
                            bundle.putString("urlImage",u.getUrlImage());
                            bundle.putString("token",u.getToken());
                            setBooleanPreferences(preferences, "noibo", u.getNoibo().equals("1"));

                            logined.setArguments(bundle);
                            activity.LoginSuccess(u.getToken(),u.getFullName(),u.getUrlImage());



                            FragmentUtils.replaceFragment(R.id.svTrangChu,getActivity().getSupportFragmentManager(),logined,"");
                        }catch (Exception ex){

                        }

                    }
                }else{
                    try{
                        setStringPreferences(preferences, "username", "");
                        setStringPreferences(preferences, "password", "");
                    }catch (Exception ex){

                    }
                }
                dialog_loading.dismiss();
            }
        });

    }

    private void handleWriteDB(String id) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        dataBaseToken data = new dataBaseToken(id,tokenFCM,tokenFiAM);
        mDatabase.child("DKKB_User").child(md5(id)).setValue(data);
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