package com.hungduy.honghunghospitalapp.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungduy.honghunghospitalapp.Adapter.LichSuLuuKetQuaAdapter;
import com.hungduy.honghunghospitalapp.Database.Model.KetQuaLuu;
import com.hungduy.honghunghospitalapp.R;

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