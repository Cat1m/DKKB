package com.hungduy.honghunghospital.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungduy.honghunghospital.Activity.KetQuaActivity;
import com.hungduy.honghunghospital.Model.extModel.getMaBN;
import com.hungduy.honghunghospital.Model.getModel.getLichSuKham;
import com.hungduy.honghunghospital.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MaBenhNhanAdapter extends RecyclerView.Adapter<MaBenhNhanAdapter.ViewHolder> {
    private ArrayList<getMaBN> maBNS;
    private Activity activity;
    private Bundle bundle;
    private mabenhnhanAdapterOnClick onClick;
    public MaBenhNhanAdapter(ArrayList<getMaBN> maBNS, Activity activity, Bundle bundle,mabenhnhanAdapterOnClick onClick) {
        this.maBNS = maBNS;
        this.activity = activity;
        this.bundle = bundle;
        this.onClick = onClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View itemview;
        public TextView txtTitle,txtNoiDung;
        public Button btnChiTiet;
        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtNoiDung = itemView.findViewById(R.id.txtNoiDung);
            btnChiTiet = itemView.findViewById(R.id.btnChiTiet);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View KhachHangView = inflater.inflate(R.layout.item_lich_su_kham, parent, false);
        ViewHolder viewHolder = new ViewHolder(KhachHangView);
        return viewHolder;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        getMaBN mabn = maBNS.get(position);
        holder.txtTitle.setText(mabn.getTen());
        holder.txtNoiDung.setMaxLines(10);
        holder.txtNoiDung.setLineSpacing(1f,1.3f);
        holder.txtNoiDung.setText("Mã bệnh nhân: "+mabn.getMa()+
                "\nNăm sinh: "+ mabn.getNam());
        holder.btnChiTiet.setText("Chọn");
        holder.btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick(mabn);
            }
        });
    }
    @Override
    public int getItemCount() {
        return maBNS.size();
    }

    public interface mabenhnhanAdapterOnClick{
        void onClick(getMaBN bn);
    }
}
