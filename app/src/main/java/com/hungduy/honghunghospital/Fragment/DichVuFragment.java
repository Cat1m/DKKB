package com.hungduy.honghunghospital.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hungduy.honghunghospital.R;

public class DichVuFragment extends Fragment {

    private String mParam1;
    private String mParam2;

    public DichVuFragment() {
    }

    public static DichVuFragment newInstance(String param1, String param2) {
        DichVuFragment fragment = new DichVuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dich_vu, container, false);
    }
}