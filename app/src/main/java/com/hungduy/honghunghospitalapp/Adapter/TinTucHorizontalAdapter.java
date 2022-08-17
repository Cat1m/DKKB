package com.hungduy.honghunghospitalapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungduy.honghunghospitalapp.Activity.WebviewActivity;
import com.hungduy.honghunghospitalapp.Model.getModel.getTinTuc;
import com.hungduy.honghunghospitalapp.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class TinTucHorizontalAdapter extends RecyclerView.Adapter<TinTucHorizontalAdapter.ViewHolder> {
    private ArrayList<getTinTuc> tinTucs;
    // Lưu Context để dễ dàng truy cập
    private Context mContext;
    private Activity activity;

    public TinTucHorizontalAdapter(ArrayList<getTinTuc> tintuc, Context mContent, Activity activity){
        this.tinTucs = tintuc;
        this.mContext = mContent;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View itemview;
        public TextView txtNoiDung;
        public View ItemView;
        public ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            txtNoiDung = itemView.findViewById(R.id.txtNoiDung);
            txtNoiDung.setTextColor(Color.WHITE);
            img = itemView.findViewById(R.id.img);
            ItemView = itemView.findViewById(R.id.ItemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View KhachHangView = inflater.inflate(R.layout.item_tin_tuc_h, parent, false);
        ViewHolder viewHolder = new ViewHolder(KhachHangView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        getTinTuc tintuc = tinTucs.get(position);
        holder.txtNoiDung.setText(tintuc.getMota());
        holder.ItemView.setTag(tintuc.getMa());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(tintuc.getUrl()).get();
                    String img = doc.select("meta[property=og:image]").first().attr("content");
                    if (img.isEmpty()) {
                        img = doc.select("meta[name=twitter:image]").first().attr("content");
                    } else if (img.isEmpty()) {
                        img = "https://honghunghospital.com.vn/wp-content/uploads/2020/03/Hong-Hung-Logo-FA-01-e1585021504155.png";
                    }
                    //  Log.d("TinTucAdapter",img);
                    String finalImg = img;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Picasso.get().load(finalImg).into(holder.img);
                        }
                    });
                } catch (Exception e) {

                }
            }
        }).start();
        holder.ItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag().equals(tintuc.getMa())) {
                    Intent intent = new Intent(activity, WebviewActivity.class);
                    intent.putExtra("url", tintuc.getUrl());
                    activity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tinTucs.size();
    }


}
