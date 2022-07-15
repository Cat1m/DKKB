package com.hungduy.honghunghospitalapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.hungduy.honghunghospitalapp.Activity.BaseKhaiBaoYTeActivity;
import com.hungduy.honghunghospitalapp.Model.extModel.CauHoiKhaiBaoYTeEXT;
import com.hungduy.honghunghospitalapp.Model.getModel.getCauHoiKhaiBaoYTe;
import com.hungduy.honghunghospitalapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class KhaiBaoYTeAdapter extends RecyclerView.Adapter<KhaiBaoYTeAdapter.ViewHolder> {
    private ArrayList<getCauHoiKhaiBaoYTe> cauHoiKhaiBaoYTes;
    // Lưu Context để dễ dàng truy cập
    private Context mContext;
    private Activity activity;

    public KhaiBaoYTeAdapter(ArrayList<getCauHoiKhaiBaoYTe> khachhang, Context mContent, Activity activity){
        this.cauHoiKhaiBaoYTes = khachhang;
        this.mContext = mContent;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View itemview;
        public TextView txtCauHoi;
        public RadioButton btnKhong,btnCo;
        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            txtCauHoi = itemView.findViewById(R.id.txtCauHoi);
            btnKhong = itemView.findViewById(R.id.btnKhong);
            btnCo = itemView.findViewById(R.id.btnCo);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View KhachHangView = inflater.inflate(R.layout.item_cau_hoi_khai_bao_y_te, parent, false);

        ViewHolder viewHolder = new ViewHolder(KhachHangView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        getCauHoiKhaiBaoYTe ch = cauHoiKhaiBaoYTes.get(position);
        holder.txtCauHoi.setText(ch.getCauhoi());
        holder.btnKhong.setChecked(true);
        BaseKhaiBaoYTeActivity activitys = (BaseKhaiBaoYTeActivity) this.activity;
        holder.btnKhong.setTag(ch.getMa());
        holder.btnCo.setTag(ch.getMa());
        activitys.updateCauTraLoi(new CauHoiKhaiBaoYTeEXT(ch,"Không"));
        holder.btnKhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getTag().equals(ch.getMa())){
                    activitys.updateCauTraLoi(new CauHoiKhaiBaoYTeEXT(ch,"Không"));
                }
            }
        });
        holder.btnCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getTag().equals(ch.getMa())) {
                    activitys.updateCauTraLoi(new CauHoiKhaiBaoYTeEXT(ch, "Có"));
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return cauHoiKhaiBaoYTes.size();
    }


}
