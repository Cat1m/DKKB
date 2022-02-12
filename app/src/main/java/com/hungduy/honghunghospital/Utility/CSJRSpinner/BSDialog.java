package com.hungduy.honghunghospital.Utility.CSJRSpinner;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungduy.honghunghospital.Model.extModel.getBSCoHinh;
import com.hungduy.honghunghospital.R;

import java.util.ArrayList;

public class BSDialog extends DialogFragment {
    private ArrayList<getBSCoHinh> data;
    private String title;
    private EditText etSearch;
    private RecyclerView recyclerView;
    private TextView tvTitle;
    private BSAdapter adapter;
    private ImageView reset;
    private View root;
    private CardView card;
    private BSSpinner view;
    private BSSpinner.OnItemClickListener listener;
    private int selected;
    private TextWatcher watcher;

    public BSDialog() {
    }

    public void setListener(BSSpinner.OnItemClickListener listener, BSSpinner view) {
        this.listener = listener;
        this.view = view;
    }
    public void addSearchListener(TextWatcher watcher){
        this.watcher = watcher;
    }
    public static BSDialog newInstance(String title, ArrayList<getBSCoHinh> data, int selected) {
        BSDialog instance = new BSDialog();
        Bundle arguments = new Bundle();
        arguments.putSerializable("data", data);
        arguments.putString("title", title);
        arguments.putInt("selected", selected);
        instance.setArguments(arguments);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);

        if (getArguments() != null && getArguments().getSerializable("data") != null && getArguments().getString("title") != null) {
            try{
                data = (ArrayList<getBSCoHinh>) getArguments().getSerializable("data");
            }catch (Exception e){

            }
            title = getArguments().getString("title");
            selected = getArguments().getInt("selected");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jrspinner_layout_dialog, container, false);
        etSearch = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvTitle = view.findViewById(R.id.title);
        reset = view.findViewById(R.id.reset);
        root = view.findViewById(R.id.root);
        card = view.findViewById(R.id.card);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (data != null) {
            tvTitle.setText(title);
            adapter = new BSAdapter(false, new BSAdapter.Listener() {
                @Override
                public void onClick(Pair<Integer, getBSCoHinh> item, int position) {
                    BSDialog.this.view.setText(item.second.getTen());
                    BSDialog.this.view.setSelected(item.first);
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                    dismiss();
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter.update(data, selected);
            recyclerView.setAdapter(adapter);
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    adapter.update(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (!editable.toString().isEmpty()) {
                        reset.setVisibility(View.VISIBLE);
                    } else {
                        reset.setVisibility(View.GONE);
                    }
                }
            });

            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.reset();
                    etSearch.setText("");
                }
            });

            if (watcher != null){
                etSearch.addTextChangedListener(watcher);
            }

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //do nothing
                }
            });
        } else {
            dismiss();
        }
    }

    public void updateItems(final ArrayList<getBSCoHinh> newItems) {
        adapter.update(newItems, -1);
    }


}
