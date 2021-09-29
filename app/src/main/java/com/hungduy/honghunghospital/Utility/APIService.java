package com.hungduy.honghunghospital.Utility;



import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.getModel.baseGetClass;
import com.hungduy.honghunghospital.Model.getModel.getOTPModel;
import com.hungduy.honghunghospital.Model.getModel.get_Token_Ma;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers("Content-Type: application/json")
    @POST("getOTP")
    Call<ResponseModel> getOTP(@Body getOTPModel x);

    @Headers("Content-Type: application/json")
    @POST("getTinhThanh")
    Call<ResponseModel> getTinhThanh(@Body baseGetClass x);

    @Headers("Content-Type: application/json")
    @POST("getQuanHuyen")
    Call<ResponseModel> getQuanHuyen(@Body get_Token_Ma x);

    @Headers("Content-Type: application/json")
    @POST("getPhuongXa")
    Call<ResponseModel> getPhuongXa(@Body get_Token_Ma x);

    @Headers("Content-Type: application/json")
    @POST("getApKhuPho")
    Call<ResponseModel> getApKhuPho(@Body get_Token_Ma x);
}
