package com.hungduy.honghunghospital.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hungduy.honghunghospital.R;

public class BacSiFragment extends BaseFragment {

    public BacSiFragment() {
    }

    public static BacSiFragment newInstance(String param1, String param2) {
        BacSiFragment fragment = new BacSiFragment();
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

        return inflater.inflate(R.layout.fragment_bac_si, container, false);
    }
}