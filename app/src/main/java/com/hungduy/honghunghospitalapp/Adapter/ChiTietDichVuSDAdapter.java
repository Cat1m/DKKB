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

import com.hungduy.honghunghospitalapp.Model.getModel.getDichVuSD;
import com.hungduy.honghunghospitalapp.R;
import com.hungduy.honghunghospitalapp.Utility.UtilityHHH;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ChiTietDichVuSDAdapter extends RecyclerView.Adapter<ChiTietDichVuSDAdapter.ViewHolder> {
    private ArrayList<getDichVuSD> dichVuSDS;
    private Activity activity;
    private Bundle bundle;
    public ChiTietDichVuSDAdapter(ArrayList<getDichVuSD> dichVuSDS, Activity activity, Bundle bundle) {
        this.dichVuSDS = dichVuSDS;
        this.activity = activity;
        this.bundle = bundle;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle,txtNoiDung;
        public Button btnChiTiet;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtNoiDung = itemView.findViewById(R.id.txtNoiDung);
            btnChiTiet = itemView.findViewById(R.id.btnChiTiet);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View KhachHangView = inflater.inflate(R.layout.item_lich_su_kham, parent, false);
        return new ViewHolder(KhachHangView);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        getDichVuSD toa = dichVuSDS.get(position);
        String tt = "",dg = "";
        NumberFormat formatter = new DecimalFormat("#,###");
        try {
            tt =  formatter.format(UtilityHHH.toDouble(toa.getThanhtien()));
            dg =  formatter.format(UtilityHHH.toDouble(toa.getDongia()));
        }catch (Exception e){
            tt = toa.getThanhtien();
            dg = toa.getDongia();
        }

        holder.txtTitle.setText("Tên dv: "+ toa.getTen());
        holder.txtNoiDung.setMaxLines(10);
        holder.txtNoiDung.setLineSpacing(1f,1.3f);
        holder.txtNoiDung.setText("Số lượng: " +toa.getSoluong()+
                "\nĐơn giá: " + tt +"VNĐ"+
                "\nThành tiền: " + dg+"VNĐ"+
                (toa.getGhichu().isEmpty() ? "" : "\nGhi chú: "+toa.getGhichu() ));
        holder.btnChiTiet.setVisibility(View.GONE);
    }
    @Override
    public int getItemCount() {
        return dichVuSDS.size();
    }
}
