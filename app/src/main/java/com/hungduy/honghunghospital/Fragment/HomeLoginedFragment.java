package com.hungduy.honghunghospital.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hungduy.honghunghospital.Activity.KhaiBaoYTeActivity;
import com.hungduy.honghunghospital.Activity.KhaiBaoYTeCongTacActivity;
import com.hungduy.honghunghospital.Activity.KhaiBaoYTeNguoiThanActivity;
import com.hungduy.honghunghospital.Activity.KhaiBaoYTeNoiBoActivity;
import com.hungduy.honghunghospital.Activity.MainActivity;
import com.hungduy.honghunghospital.Activity.SplashActivity;
import com.hungduy.honghunghospital.R;
import com.hungduy.honghunghospital.Utility.FragmentUtils;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeLoginedFragment extends BaseFragment {

    private Button btn_home_1,btn_home_2,btn_home_3,btn_home_4,btn_home_5,btn_home_6;

    public HomeLoginedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView(view);
        btn_home_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), KhaiBaoYTeActivity.class);
                i.putExtra("FullName",FullName);
                i.putExtra("urlImage",urlImage);
                i.putExtra("token",token);
                startActivity(i);
            }
        });

        btn_home_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), KhaiBaoYTeNguoiThanActivity.class);
                i.putExtra("FullName",FullName);
                i.putExtra("urlImage",urlImage);
                i.putExtra("token",token);
                startActivity(i);
            }
        });

        btn_home_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), KhaiBaoYTeCongTacActivity.class);
                i.putExtra("FullName",FullName);
                i.putExtra("urlImage",urlImage);
                i.putExtra("token",token);
                startActivity(i);
            }
        });

        btn_home_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), KhaiBaoYTeNoiBoActivity.class);
                i.putExtra("FullName",FullName);
                i.putExtra("urlImage",urlImage);
                i.putExtra("token",token);
                startActivity(i);
            }
        });

        if(noibo){
            btn_home_5.setVisibility(View.VISIBLE);
        }else{
            btn_home_5.setVisibility(View.GONE);
        }

        btn_home_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStringPreferences(preferences, "username", username);
                setStringPreferences(preferences, "password", password);
                Intent splash = new Intent(getActivity(), SplashActivity.class);
                startActivity(splash);
                getActivity().finish();

            }
        });

    }

    private void mapView(View v) {
        btn_home_1 = v.findViewById(R.id.btn_home_1);
        btn_home_2 = v.findViewById(R.id.btn_home_2);
        btn_home_3 = v.findViewById(R.id.btn_home_3);
        btn_home_4 = v.findViewById(R.id.btn_home_4);
        btn_home_5 = v.findViewById(R.id.btn_home_5);
        btn_home_6 = v.findViewById(R.id.btn_home_6);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_logined_home, container, false);
    }
}