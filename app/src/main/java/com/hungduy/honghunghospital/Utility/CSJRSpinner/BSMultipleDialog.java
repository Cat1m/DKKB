package com.hungduy.honghunghospital.Utility.CSJRSpinner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.List;

public class BSMultipleDialog  extends DialogFragment {
    private ArrayList<getBSCoHinh> data;
    private String title;
    private EditText etSearch;
    private RecyclerView recyclerView;
    private TextView tvTitle;
    private BSAdapter adapter;
    private ImageView reset;
    private View root;
    private Button btnSelect;
    private CardView card;
    private BSSpinner view;
    private BSSpinner.OnSelectMultipleListener listener;
    private List<Integer> selected;
    private TextWatcher watcher;

    public BSMultipleDialog() {
    }

    public void setListener(BSSpinner.OnSelectMultipleListener listener, BSSpinner view) {
        this.listener = listener;
        this.view = view;
    }

    public void addSearchListener(TextWatcher watcher){
        this.watcher = watcher;
    }

    public static BSMultipleDialog newInstance(String title, String[] data, List<Integer> selected) {
        BSMultipleDialog instance = new BSMultipleDialog();
        Bundle arguments = new Bundle();
        arguments.putSerializable("data", data);
        arguments.putString("title", title);
        arguments.putIntegerArrayList("selected", (ArrayList<Integer>) selected);
        instance.setArguments(arguments);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);

        if (getArguments() != null && getArguments().getStringArray("data") != null && getArguments().getString("title") != null) {
            data = (ArrayList<getBSCoHinh>) getArguments().getSerializable("data");
            title = getArguments().getString("title");
            selected = new ArrayList<>(getArguments().getIntegerArrayList("selected"));
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
        btnSelect = view.findViewById(R.id.btn_select);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        android.app.Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSelect.setVisibility(View.VISIBLE);
        if (data != null) {
            tvTitle.setText(title);
            adapter = new BSAdapter(true, new BSAdapter.Listener() {
                @Override
                public void onClick(Pair<Integer, getBSCoHinh> item, int position) {
//                    MultipleDialog.this.view.setText(item.second);
//                    MultipleDialog.this.view.setSelected(item.first);
                    if (listener != null) {
                        if (selected.contains(item.first)) {
                            for (int i = 0; i < selected.size(); i++) {
                                if (selected.get(i).equals(item.first)) {
                                    selected.remove(i);
                                    adapter.removeSelect(item.first);
                                    break;
                                }
                            }
                        } else {
                            selected.add(item.first);
                            adapter.addSelect(item.first);
                        }
                    }
//                    dismiss();
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

            btnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StringBuilder text = new StringBuilder();
                    for (int i = 0; i < data.size(); i++) {
                        if (text.length() == 0 && selected.contains(i)) {
                            text.append(data.get(i).getTen());
                        } else if (selected.contains(i)) {
                            text.append(", ").append(data.get(i).getTen());
                        }
                    }
                    BSMultipleDialog.this.view.setText(text);
                    BSMultipleDialog.this.view.setSelected(selected);
                    if (listener != null) {
                        listener.onMultipleSelected(selected);
                    }
                    dismiss();
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

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        selected.clear();
    }

    public void updateItems(final ArrayList<getBSCoHinh> newItems) {
        adapter.update(newItems, new ArrayList<Integer>());
    }
}
