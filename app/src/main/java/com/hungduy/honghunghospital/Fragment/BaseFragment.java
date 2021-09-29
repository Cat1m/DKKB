package com.hungduy.honghunghospital.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.hungduy.honghunghospital.FancyGifDialog.FancyGifDialog;
import com.hungduy.honghunghospital.FancyGifDialog.FancyGifDialogListener;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.APIService;
import com.hungduy.honghunghospital.Utility.ApiUtils;
import com.hungduy.honghunghospital.Utility.UtilityHHH;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public  abstract class BaseFragment extends Fragment {
    protected APIService mAPIService;
    private FirebaseAnalytics mFirebaseAnalytics;
    protected static String preferences = "preferences";
    protected String username,password, branchID ,FullName,urlImage,branchName,token,TAG;
    protected String APIKey;
    protected int padding;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAPIService = ApiUtils.getAPIService();// register API services
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        TAG = this.getClass().getSimpleName();
        if (getArguments() != null) {
            username = getArguments().getString("username");
            password = getArguments().getString("password");
            FullName = getArguments().getString("FullName");
            urlImage = getArguments().getString("urlImage");
            branchID = getArguments().getString("branchID");
            branchName = getArguments().getString("branchName");
            padding = getArguments().getInt("padding");
            token = getArguments().getString("token");
        }
        String currentYearMonth = new SimpleDateFormat("yyyyMM", Locale.getDefault()).format(new Date());
        try {
            APIKey = UtilityHHH.getSha512fromString("HHHApp4P1"+currentYearMonth);
        }catch (Exception ex){
        }
    }

    protected void ThongBao(Activity activity, String title, String Mes, int gif, FancyGifDialogListener okclick){
        new FancyGifDialog.Builder(activity)
                .setTitle(title)
                .setMessage(Mes)
                .setPositiveBtnBackground(R.color.design_default_color_error)
                .setPositiveBtnText("Đồng ý")
                .setGifResource(gif)
                .OnPositiveClicked(okclick)
                .build();
    }

    protected boolean getBooleanPreferences(String name,String key){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(name, MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }
    protected boolean getBooleanPreferencesTrue(String name,String key){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(name, MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, true);
    }
    protected void setBooleanPreferences(String name,String key,boolean value){
        SharedPreferences.Editor edit = getActivity().getSharedPreferences(name, MODE_PRIVATE).edit();
        edit.putBoolean(key,value);
        edit.commit();
    }
    protected String getStringPreferences(String name,String key){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(name, MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
    protected void setStringPreferences(String name,String key,String value){
        SharedPreferences.Editor edit = getActivity().getSharedPreferences(name, MODE_PRIVATE).edit();
        edit.putString(key,value);
        edit.commit();
    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setPadding(0,padding,0,0);
    }
}
