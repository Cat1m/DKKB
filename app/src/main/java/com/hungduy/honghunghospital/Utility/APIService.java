package com.hungduy.honghunghospital.Utility;



import com.hungduy.honghunghospital.Model.LoginModel;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.getModel.baseGetClass;
import com.hungduy.honghunghospital.Model.getModel.getOTPModel;
import com.hungduy.honghunghospital.Model.setModel.setUserModel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {
    @Headers("Content-Type: application/json")
    @POST("getOTP")
    Call<ResponseModel> getOTP(@Body getOTPModel x);

    @Headers("Content-Type: application/json")
    @POST("getQuocGia")
    Call<ResponseModel> getQuocGia(@Header("token") String s);

    @Headers("Content-Type: application/json")
    @POST("getTinhThanh")
    Call<ResponseModel> getTinhThanh(@Header("token") String s);

    @Headers("Content-Type: application/json")
    @POST("getQuanHuyen")
    Call<ResponseModel> getQuanHuyen(@Header("token") String s,@Body baseGetClass x);

    @Headers("Content-Type: application/json")
    @POST("getPhuongXa")
    Call<ResponseModel> getPhuongXa(@Header("token") String s,@Body baseGetClass x);

    @Headers("Content-Type: application/json")
    @POST("getApKhuPho")
    Call<ResponseModel> getApKhuPho(@Header("token") String s,@Body baseGetClass x);

    @Headers("Content-Type: application/json")
    @POST("setUser")
    Call<ResponseModel> setUser(@Header("token") String s,@Body setUserModel x);

    @Multipart
    @POST("ImageUploadFile")
    Call<ResponseModel> ImageUploadFile(@Header("token") String s, @Part MultipartBody.Part file);

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<ResponseModel> login(@Header("token") String s,@Body LoginModel login);

    @Headers("Content-Type: application/json")
    @POST("getOTP")
    Call<ResponseModel> getOTP(@Header("token") String s,@Body getOTPModel otp);
}
