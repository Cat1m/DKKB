package com.hungduy.honghunghospital.Utility;

import android.app.Activity;
import android.util.Log;

import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.R;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallbackResponse implements Callback<ResponseModel> {
    private Activity a;

    public Activity getA() {
        return a;
    }

    public void setA(Activity a) {
        this.a = a;
    }

    public CallbackResponse(@NotNull Activity a) {
        this.a = a;
    }


    @Override
    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
        if(!response.isSuccessful()){
            try{
                new FancyGifDialog.Builder(a)
                        .setTitle("Đã có lỗi xảy ra")
                        .setMessage("Server error")
                        .setPositiveBtnBackground("#FF4081")
                        .setPositiveBtnText("Đồng ý")
                        .setGifResource(R.drawable.connection_error)
                        .build();
            }catch (Exception ex){
                Log.d("CallBack",ex.getMessage());
            }
        }else {
            if(!response.body().getStatus().equals("OK")){
                try{
                    new FancyGifDialog.Builder(a)
                            .setTitle("Thông báo!")
                            .setMessage(response.body().getMessenge())
                            .setPositiveBtnBackground("#FF4081")
                            .setPositiveBtnText("Đồng ý")
                            .setGifResource(R.drawable.connection_error)
                            .build();
                }catch (Exception ex){
                    Log.d("CallBack",ex.getMessage());
                }
            }else{
                success(response);
            }
        }
    }

    abstract public void success(Response<ResponseModel> response);

    @Override
    public void onFailure(Call<ResponseModel> call, Throwable t) {
        try {
            new FancyGifDialog.Builder(a)
                    .setTitle("Thông báo!")
                    .setMessage("Kết nối với máy chủ thất bại. Vui lòng thử lại sau")
                    .setPositiveBtnBackground("#FF4081")
                    .setPositiveBtnText("Đồng ý")
                    .setGifResource(R.drawable.connection_error)
                    .build();
        }catch (Exception ex){
            Log.d("CallBack",ex.getMessage());
        }
    }

}
