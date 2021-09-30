package com.hungduy.honghunghospital.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hungduy.honghunghospital.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeLoginedFragment extends BaseFragment {
    private TextView txtUser;
    private CircleImageView imgUser;

    public HomeLoginedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView(view);

        txtUser.setText(FullName);
        if(urlImage != ""){
            Picasso.get().load(urlImage).placeholder(R.drawable.user).into(imgUser);
        }
    }

    private void mapView(View v) {
        txtUser = v.findViewById(R.id.txtUser);
        imgUser = v.findViewById(R.id.imgUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logined_home, container, false);
    }
}