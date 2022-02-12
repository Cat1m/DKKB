package com.hungduy.honghunghospital.Utility.CSJRSpinner;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hungduy.honghunghospital.Model.extModel.getBSCoHinh;
import com.hungduy.honghunghospital.R;

import java.util.ArrayList;
import java.util.List;

public class BSAdapter extends RecyclerView.Adapter<BSAdapter.ViewHolder>  {
    private List<Pair<Integer, getBSCoHinh>> items = new ArrayList<>();
    private List<Pair<Integer, getBSCoHinh>> allItems = new ArrayList<>();
    private List<Pair<Integer, getBSCoHinh>> tempItems = new ArrayList<>();

    private boolean multiple;
    /**
     * the listener when item clicked
     */
    private Listener listener;
    /**
     * selected position when use non multiple spinner
     */
    private int selected;
    /**
     * selected positions when use multiple spinner
     */
    private List<Integer> multipleSelected;

    /**
     * the constructor to create adapter object
     * @param multiple property to know is this multiple spinner or no
     * @param listener the listener
     */
    public BSAdapter(boolean multiple, Listener listener) {
        this.multiple = multiple;
        this.listener = listener;
    }
    public void update(ArrayList<getBSCoHinh> items, int selected) {
        this.selected = selected;
        this.items.clear();
        this.allItems.clear();
        this.tempItems.clear();
        for (int i = 0; i < items.size(); i++) {
            this.items.add(new Pair<>(i, items.get(i)));
            this.allItems.add(new Pair<>(i, items.get(i)));
        }
        notifyDataSetChanged();
    }
    public void update(ArrayList<getBSCoHinh> items, List<Integer> selected) {
        multipleSelected = new ArrayList<>(selected);
        this.items.clear();
        this.allItems.clear();
        this.tempItems.clear();
        for (int i = 0; i < items.size(); i++) {
            this.items.add(new Pair<>(i, items.get(i)));
            this.allItems.add(new Pair<>(i, items.get(i)));
        }
        notifyDataSetChanged();
    }
    public void update(String query) {
        for (Pair<Integer, getBSCoHinh> item : allItems) {
            if (item.second.getTen().contains(query.toLowerCase())) {
                tempItems.add(item);
            }
        }

        items.clear();
        items.addAll(tempItems);
        tempItems.clear();
        notifyDataSetChanged();
    }
    public void reset() {
        items.clear();
        items.addAll(allItems);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.jrspinner_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Pair<Integer, getBSCoHinh> item = items.get(position);
        viewHolder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addSelect(int selected) {
        multipleSelected.add(selected);
        notifyDataSetChanged();
    }

    public void removeSelect(int selected) {
        for (int i = 0; i < multipleSelected.size(); i++) {
            if (multipleSelected.get(i).equals(selected)) {
                multipleSelected.remove(i);
                break;
            }
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView label;
        private ImageView ivSelected,imgDoctor;

        ViewHolder(View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.label);
            ivSelected = itemView.findViewById(R.id.selected);
            imgDoctor = itemView.findViewById(R.id.imgDoctor);
        }

        void bind(final Pair<Integer, getBSCoHinh> item) {
            if ((multiple && multipleSelected.contains(item.first)) || (!multiple && item.first == selected)) {
                ivSelected.setVisibility(View.VISIBLE);
            } else {
                ivSelected.setVisibility(View.GONE);
            }
            try {
                imgDoctor.setVisibility(View.VISIBLE);
                //Log.d("TAG", "bind: "+item.second.getUrl());
                Glide.with(this.itemView).load(item.second.getUrl()).placeholder(R.drawable.logo_honghung).centerCrop().into(imgDoctor);
            }catch (Exception e){

            }
            label.setText(item.second.getTen());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(item, item.first);
                }
            });
        }
    }

    interface Listener {
        void onClick(Pair<Integer, getBSCoHinh> item, int position);
    }
}
