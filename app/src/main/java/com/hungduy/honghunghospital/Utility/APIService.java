package com.hungduy.honghunghospital.Utility;



import com.hungduy.honghunghospital.Model.LoginModel;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.getModel.baseGetClass;
import com.hungduy.honghunghospital.Model.getModel.getOTPModel;
import com.hungduy.honghunghospital.Model.setModel.setDangKyKham;
import com.hungduy.honghunghospital.Model.setModel.setKhaiBao;
import com.hungduy.honghunghospital.Model.setModel.setNewPassword;
import com.hungduy.honghunghospital.Model.setModel.setNguoiThanDangKyKham;
import com.hungduy.honghunghospital.Model.setModel.setUserModel;
import com.hungduy.honghunghospital.Model.setModel.updateUser;

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
    @POST("ping")
    Call<ResponseModel> ping();

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

    @Headers("Content-Type: application/json")
    @POST("getUserbyToken")
    Call<ResponseModel> getUserbyToken(@Header("token") String s,@Body baseGetClass x);

    @Headers("Content-Type: application/json")
    @POST("updateUser")
    Call<ResponseModel> updateUser(@Header("token") String s,@Body updateUser x);

    @Multipart
    @POST("ImageUploadFile")
    Call<ResponseModel> ImageUploadFile(@Header("token") String s, @Part MultipartBody.Part file);

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<ResponseModel> login(@Header("token") String s,@Body LoginModel login);

    @Headers("Content-Type: application/json")
    @POST("getOTP")
    Call<ResponseModel> getOTP(@Header("token") String s,@Body getOTPModel otp);

    @Headers("Content-Type: application/json")
    @POST("ResetPassword")
    Call<ResponseModel> ResetPassword(@Header("token") String s,@Body setNewPassword otp);

    @Headers("Content-Type: application/json")
    @POST("getAllActiveDoctor")
    Call<ResponseModel> getAllActiveDoctor(@Header("token") String s);

    @Headers("Content-Type: application/json")
    @POST("getDetailActiveDoctor")
    Call<ResponseModel> getDetailActiveDoctor(@Header("token") String s,@Body baseGetClass get);

    @Headers("Content-Type: application/json")
    @POST("getLichLamViecBS")
    Call<ResponseModel> getLichLamViecBS(@Header("token") String s,@Body baseGetClass get);


    @Headers("Content-Type: application/json")
    @POST("getCauHoiKBYT")
    Call<ResponseModel> getCauHoiKBYT(@Header("token") String s);

    @Headers("Content-Type: application/json")
    @POST("getDmChuyenKhoa")
    Call<ResponseModel> getDmChuyenKhoa(@Header("token") String s);


    @Headers("Content-Type: application/json")
    @POST("setDangKyKham")
    Call<ResponseModel> setDangKyKham(@Header("token") String s,@Body setDangKyKham w);

    @Headers("Content-Type: application/json")
    @POST("setDangKyKhamNguoiThan")
    Call<ResponseModel> setDangKyKhamNguoiThan(@Header("token") String s,@Body setNguoiThanDangKyKham w);

    @Headers("Content-Type: application/json")
    @POST("getTinTuc")
    Call<ResponseModel> getTinTuc(@Header("token") String s);

    @Headers("Content-Type: application/json")
    @POST("getPreferencesKey")
    Call<ResponseModel> getPreferencesKey(@Header("token") String s);

    @Headers("Content-Type: application/json")
    @POST("getDanToc")
    Call<ResponseModel> getDanToc(@Header("token") String s);

    @Headers("Content-Type: application/json")
    @POST("getLoaiDichVu")
    Call<ResponseModel> getLoaiDichVu(@Header("token") String s);

    @Headers("Content-Type: application/json")
    @POST("getDichVu")
    Call<ResponseModel> getDichVu(@Header("token") String s);

    @Headers("Content-Type: application/json")
    @POST("getDichVuByMa")
    Call<ResponseModel> getDichVuByMa(@Header("token") String s,@Body baseGetClass x);

    @Headers("Content-Type: application/json")
    @POST("setKhaiBao")
    Call<ResponseModel> setKhaiBao(@Header("token") String s,@Body setKhaiBao x);
}
