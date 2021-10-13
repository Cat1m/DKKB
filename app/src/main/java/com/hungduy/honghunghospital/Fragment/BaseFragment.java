package com.hungduy.honghunghospital.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hungduy.honghunghospital.Activity.LogoActivity;
import com.hungduy.honghunghospital.Activity.UpdateUserActivity;
import com.hungduy.honghunghospital.Database.DAO.BacSiDAO;
import com.hungduy.honghunghospital.Database.DAO.CauHoiKhaiBaoYTeDAO;
import com.hungduy.honghunghospital.Database.DAO.DanTocDAO;
import com.hungduy.honghunghospital.Database.DAO.DichVuDAO;
import com.hungduy.honghunghospital.Database.DAO.KhuPhoDAO;
import com.hungduy.honghunghospital.Database.DAO.LoaiDichVuDAO;
import com.hungduy.honghunghospital.Database.DAO.PhuongXaDAO;
import com.hungduy.honghunghospital.Database.DAO.QuanHuyenDAO;
import com.hungduy.honghunghospital.Database.DAO.QuocGiaDAO;
import com.hungduy.honghunghospital.Database.DAO.TinTucDAO;
import com.hungduy.honghunghospital.Database.DAO.TinhThanhDAO;
import com.hungduy.honghunghospital.Database.DAO.UserDataDAO;
import com.hungduy.honghunghospital.Database.LocalDB;
import com.hungduy.honghunghospital.Database.Model.DichVu;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.APIService;
import com.hungduy.honghunghospital.Utility.ApiUtils;
import com.hungduy.honghunghospital.Utility.AppConfigString;
import com.hungduy.honghunghospital.Utility.ConnectivityStatusReceiver;
import com.hungduy.honghunghospital.Utility.UtilityHHH;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
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
    protected LocalDB database;
    protected TinhThanhDAO TTdao;
    protected QuocGiaDAO QGdao;
    protected QuanHuyenDAO QHdao;
    protected PhuongXaDAO PXdao;
    protected KhuPhoDAO KPdao;
    protected UserDataDAO USRdao;
    protected CauHoiKhaiBaoYTeDAO KBYTdao;
    protected TinTucDAO tinTucDAO;
    protected BacSiDAO bacSiDAO;
    protected DanTocDAO dantocDAO;
    protected DichVuDAO dichvuDAO;
    protected LoaiDichVuDAO loaiDichVuDAO;
    protected boolean OfflineMode;
    protected boolean noibo;
    protected Dialog dialog_loading;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAPIService = ApiUtils.getAPIService();// register API services
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        TAG = this.getClass().getSimpleName();
        shape_edittext_error = AppCompatResources.getDrawable(getActivity().getApplicationContext(), R.drawable.shape_edittext_error);
        shape_edittext_have_focus = AppCompatResources.getDrawable(getActivity(), R.drawable.shape_edittext_have_focus);

        database = LocalDB.getInstance(getContext());
        TTdao = database.tinhThanhDAO();
        QGdao = database.quocGiaDAO();
        QHdao = database.quanHuyenDAO();
        PXdao = database.phuongXaDAO();
        KPdao = database.khuPhoDAO();
        USRdao = database.userDataDAO();
        KBYTdao = database.kbytdao();
        tinTucDAO = database.tinTucDAO();
        bacSiDAO = database.bacSiDAO();
        dantocDAO = database.danTocDAO();
        dichvuDAO = database.dichVuDAO();
        loaiDichVuDAO = database.loaiDichVuDAO();

        dialog_loading = new Dialog(getActivity());
        dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog_loading.getWindow() != null)
            dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.setCancelable(false);
        dialog_loading.setContentView(R.layout.item_loadding);
        ImageView img = dialog_loading.findViewById(R.id.imgLoading);
        Glide.with(this).load(getResources().getDrawable(R.drawable.loading_color)).placeholder(R.drawable.logo_hungduymedical).into(img);

        if (getArguments() != null) {
            username = getArguments().getString("username");
            password = getArguments().getString("password");
            FullName = getArguments().getString("FullName");
            urlImage = getArguments().getString("urlImage");
            padding = getArguments().getInt("padding");
            token = getArguments().getString("token");
            OfflineMode = getArguments().getBoolean("OfflineMode");
            noibo = getArguments().getBoolean("noibo");
        }
        String currentYearMonth = new SimpleDateFormat("yyyyMM", Locale.getDefault()).format(new Date());
        try {
            APIKey = UtilityHHH.getSha512fromString("HHHApp4P1"+currentYearMonth);
        }catch (Exception ex){
        }
        noibo = getBooleanPreferences(preferences,"noibo");

    }

    public void Connected(){

    };
    public void Disconect(){

    };

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

            if(FullName != null && !FullName.isEmpty()){
                txtUser.setText(FullName);
            }else{
                txtUser.setText("*");
            }

            if(urlImage != null &&!urlImage.isEmpty()){
                Picasso.get().load(urlImage).placeholder(R.drawable.user).into(imgUser);
            }else{
                String usernamePreferences = getStringPreferences(preferences,"username");
                String passwordPreferences = getStringPreferences(preferences,"password");
                if(!(usernamePreferences.isEmpty() || passwordPreferences.isEmpty())){
                    try {
                        ContextWrapper cw = new ContextWrapper(getActivity());
                        File directory = cw.getDir(AppConfigString.ImageDIR, Context.MODE_PRIVATE);
                        File UserImage = new File(directory, AppConfigString.UserImageName);
                        Picasso.get().load(UserImage).placeholder(R.drawable.avatar_user_empty).into(imgUser);
                    } catch (Exception ex) {
                        Log.d(TAG,ex.getMessage());
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String hoten = USRdao.getConfig(AppConfigString.HoTen).getConfigInfo();
                                if(!hoten.isEmpty() && txtUser.getText().toString().equals("*")){
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            txtUser.setText(hoten);
                                            imgUser.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent i = new Intent(getActivity(), UpdateUserActivity.class);
                                                    i.putExtra("FullName",FullName);
                                                    i.putExtra("urlImage",urlImage);
                                                    startActivity(i);
                                                }
                                            });
                                        }
                                    });
                                }
                            }catch (Exception ex){
                                Log.e(TAG,ex.getMessage());
                            }
                        }
                    }).start();
                }
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
                    i.putExtra("FullName",FullName);
                    i.putExtra("urlImage",urlImage);
                    i.putExtra("token",token);
                    startActivity(i);
                }
            });
        }catch (Exception ex){

        }
        try{
            if(!txtUser.getText().toString().isEmpty() && !txtUser.getText().toString().equals("*") ){
                imgUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getActivity(), UpdateUserActivity.class);
                        i.putExtra("FullName",FullName);
                        i.putExtra("urlImage",urlImage);
                        i.putExtra("token",token);
                        startActivity(i);
                    }
                });
            }
        }catch (Exception ex){

        }
    }
}
