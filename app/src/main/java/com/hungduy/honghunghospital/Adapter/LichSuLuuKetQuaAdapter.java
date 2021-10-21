package com.hungduy.honghunghospital.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungduy.honghunghospital.Activity.KetQuaActivity;
import com.hungduy.honghunghospital.Activity.WebviewActivity;
import com.hungduy.honghunghospital.Database.DAO.KetQuaLuuDAO;
import com.hungduy.honghunghospital.Database.Model.KetQuaLuu;
import com.hungduy.honghunghospital.Model.getModel.getTinTuc;
import com.hungduy.honghunghospital.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LichSuLuuKetQuaAdapter extends RecyclerView.Adapter<LichSuLuuKetQuaAdapter.ViewHolder> {
    private ArrayList<KetQuaLuu> ketQuaLuus;
    // Lưu Context để dễ dàng truy cập
    private Context mContext;
    private Activity activity;

    public LichSuLuuKetQuaAdapter(ArrayList<KetQuaLuu> ketQuaLuus, Context mContext, Activity activity) {
        this.ketQuaLuus = ketQuaLuus;
        this.mContext = mContext;
        this.activity = activity;
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
        View KhachHangView = inflater.inflate(R.layout.item_ket_qua_luu, parent, false);
        ViewHolder viewHolder = new ViewHolder(KhachHangView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        KetQuaLuu kq = ketQuaLuus.get(position);
        String title = "";
        switch (kq.getLoai()){
            case 1:
                title = "Đăng kí khám bệnh";
                break;
            case 2:
                title = "Liên hệ công tác";
                break;
            case 3:
                title = "Đăng kí cho người thân";
                break;
            case 4:
                title = "Khai báo y tế nội bộ";
                break;
            default:
                title = "Không xác định";
                break;
        }
        holder.txtTitle.setText(title);
        holder.txtNoiDung.setText(kq.getKetQua().replace("<br/>",""));
        holder.btnChiTiet.setTag(kq.getMaQR());
        holder.btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getTag().equals(kq.getMaQR())){
                    Intent i = new Intent(activity, KetQuaActivity.class);
                    i.putExtra("isTestCovid", kq.getTestNhanh() == 1);
                    i.putExtra("noidungkham",kq.getKetQua());
                    i.putExtra("QR",kq.getMaQR());
                    i.putExtra("isReview",true);
                    activity.startActivity(i);
                    activity.finish();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return ketQuaLuus.size();
    }


}
