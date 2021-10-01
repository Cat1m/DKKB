package com.hungduy.honghunghospital.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.hungduy.honghunghospital.Activity.LogoActivity;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.APIService;
import com.hungduy.honghunghospital.Utility.ApiUtils;
import com.hungduy.honghunghospital.Utility.UtilityHHH;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public  abstract class BaseFragment extends Fragment {
    protected APIService mAPIService;
    private FirebaseAnalytics mFirebaseAnalytics;
    protected static String preferences = "preferences";
    protected String username,password, branchID ,FullName,urlImage,branchName,token,TAG;
    protected String APIKey;
    protected int padding;
    protected Drawable shape_edittext_error ;
    protected Drawable shape_edittext_have_focus ;
    protected ImageView imgLogoBVHH;
    private TextView txtUser;
    private CircleImageView imgUser;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAPIService = ApiUtils.getAPIService();// register API services
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        TAG = this.getClass().getSimpleName();
        shape_edittext_error = AppCompatResources.getDrawable(getActivity().getApplicationContext(), R.drawable.shape_edittext_error);
        shape_edittext_have_focus = AppCompatResources.getDrawable(getActivity(), R.drawable.shape_edittext_have_focus);
        if (getArguments() != null) {
            username = getArguments().getString("username");
            password = getArguments().getString("password");
            FullName = getArguments().getString("FullName");
            urlImage = getArguments().getString("urlImage");
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
                .setPositiveBtnBackground("#FF4081")
                .setPositiveBtnText("Đồng ý")
                .setGifResource(gif)
                .OnPositiveClicked(okclick)
                .build();
    }

    protected void ThongBao(Activity activity, String title, String Mes, int gif){
        new FancyGifDialog.Builder(activity)
                .setTitle(title)
                .setMessage(Mes)
                .setPositiveBtnBackground("#FF4081")
                .setPositiveBtnText("Đồng ý")
                .setGifResource(gif)
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
      //  view.setPadding(0,padding,0,0);
        try {
            txtUser = view.findViewById(R.id.txtUser);
            imgUser = view.findViewById(R.id.imgUser);

            if(!FullName.isEmpty()){
                txtUser.setText(FullName);
            }else{
                txtUser.setText("*");
            }

            if(!urlImage.isEmpty()){
                Picasso.get().load(urlImage).placeholder(R.drawable.user).into(imgUser);
            }

        }catch (Exception x){
            txtUser = null;
            imgUser = null;
        }
        try {
            imgLogoBVHH = view.findViewById(R.id.imgLogoBVHH);
            imgLogoBVHH.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), LogoActivity.class);
                    startActivity(i);
                }
            });
        }catch (Exception ex){

        }
    }
}
