package com.hungduy.honghunghospitalapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungduy.honghunghospitalapp.Model.getModel.getChiTietToa;
import com.hungduy.honghunghospitalapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChiTietToaAdapter extends RecyclerView.Adapter<ChiTietToaAdapter.ViewHolder> {
    private ArrayList<getChiTietToa> chiTietToas;
    private Activity activity;
    private Bundle bundle;
    public ChiTietToaAdapter(ArrayList<getChiTietToa> chiTietToas, Activity activity, Bundle bundle) {
        this.chiTietToas = chiTietToas;
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
        getChiTietToa toa = chiTietToas.get(position);
        holder.txtTitle.setText("Tên thuốc: "+ toa.getTen());
        holder.txtNoiDung.setMaxLines(10);
        holder.txtNoiDung.setLineSpacing(1f,1.3f);
        holder.txtNoiDung.setText("Số lượng: " +toa.getSoluong()+ " "+toa.getDonvi()+
              // "\nNgày : " +toa.getNgayuong()+
                "\nSố ngày uống: " + toa.getNgayuong()+
                (toa.getGhichu().isEmpty() ? "" : "\nGhi chú: "+toa.getGhichu() ));
        holder.btnChiTiet.setVisibility(View.GONE);
    }
    @Override
    public int getItemCount() {
        return chiTietToas.size();
    }
}
