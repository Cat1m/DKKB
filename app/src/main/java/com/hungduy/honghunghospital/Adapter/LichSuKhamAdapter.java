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

import com.hungduy.honghunghospital.Activity.ChiTietToaThuocActivity;
import com.hungduy.honghunghospital.Activity.KetQuaActivity;
import com.hungduy.honghunghospital.Database.Model.KetQuaLuu;
import com.hungduy.honghunghospital.Model.getModel.getLichSuKham;
import com.hungduy.honghunghospital.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LichSuKhamAdapter extends RecyclerView.Adapter<LichSuKhamAdapter.ViewHolder> {
    private ArrayList<getLichSuKham> lichSuKhams;
    private Activity activity;
    private Bundle bundle;

    public LichSuKhamAdapter(ArrayList<getLichSuKham> lichSuKhams, Activity activity,Bundle bundle) {
        this.lichSuKhams = lichSuKhams;
        this.activity = activity;
        this.bundle = bundle;
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
        getLichSuKham ls = lichSuKhams.get(position);
        holder.txtTitle.setText(ls.getTen());
        holder.txtNoiDung.setMaxLines(10);
        holder.txtNoiDung.setLineSpacing(1f,1.3f);
        holder.txtNoiDung.setText("Ngày khám: "+ls.getNgaykham()+
                "\nNgày sinh: "+ls.getNgaysinh()+
                "\nChuẩn đoán: "+ls.getChuandoan());
        holder.btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, ChiTietToaThuocActivity.class);
                i.putExtras(bundle);
                i.putExtra("makham",ls.getSttPcs());
                activity.startActivity(i);
            }
        });
    }
    @Override
    public int getItemCount() {
        return lichSuKhams.size();
    }


}
