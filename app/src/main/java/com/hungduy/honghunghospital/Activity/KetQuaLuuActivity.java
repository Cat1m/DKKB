package com.hungduy.honghunghospital.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungduy.honghunghospital.Adapter.KhaiBaoYTeAdapter;
import com.hungduy.honghunghospital.Adapter.LichSuLuuKetQuaAdapter;
import com.hungduy.honghunghospital.Database.Model.KetQuaLuu;
import com.hungduy.honghunghospital.R;

import java.util.ArrayList;

public class KetQuaLuuActivity extends BaseActivity {
    private RecyclerView viewRecycler;
    private ArrayList<KetQuaLuu> ketQuaLuus;
    private LichSuLuuKetQuaAdapter lichSuLuuKetQuaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua_luu);
        mapView();

        ketQuaLuus = new ArrayList<>();


        new Thread(new Runnable() {
            @Override
            public void run() {
                ketQuaLuus.clear();
                ketQuaLuus = (ArrayList<KetQuaLuu>) ketQuaLuuDAO.getAll();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                lichSuLuuKetQuaAdapter = new LichSuLuuKetQuaAdapter(ketQuaLuus, getApplicationContext(), KetQuaLuuActivity.this);
                viewRecycler.setAdapter(lichSuLuuKetQuaAdapter);
                viewRecycler.setLayoutManager(linearLayoutManager);
                Log.d(TAG, "size KetQuaLuu:"+ lichSuLuuKetQuaAdapter.getItemCount());

            }
        }).start();



    }


    private void mapView() {
        viewRecycler = findViewById(R.id.viewRecycler);
    }
}