package com.hungduy.honghunghospital.Utility;



import com.hungduy.honghunghospital.Model.ResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers("Content-Type: application/json")
    @GET("getNhomGiaoDich")
    Call<ResponseModel> getNhomGiaoDich();

    @Headers("Content-Type: application/json")
    @GET("getTinhThanh")
    Call<ResponseModel> getTinhThanh();



}
