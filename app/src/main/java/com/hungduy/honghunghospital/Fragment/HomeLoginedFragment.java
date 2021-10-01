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
import com.hungduy.honghunghospital.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeLoginedFragment extends BaseFragment {

    private Button btn_home_1;

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
    }

    private void mapView(View v) {
        btn_home_1 = v.findViewById(R.id.btn_home_1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_logined_home, container, false);
    }
}