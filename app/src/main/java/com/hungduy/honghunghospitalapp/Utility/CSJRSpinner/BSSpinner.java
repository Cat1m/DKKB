package com.hungduy.honghunghospitalapp.Utility.CSJRSpinner;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.hungduy.honghunghospitalapp.Model.extModel.getBSCoHinh;
import com.hungduy.honghunghospitalapp.R;

import java.util.ArrayList;
import java.util.List;

import jrizani.jrspinner.MultipleDialog;

public class BSSpinner extends AppCompatEditText {
    private BSDialog dialog;
    private ArrayList<getBSCoHinh> items;
    private int expandTint;
    private MultipleDialog multiDialog;
    private String title = "Select";
    /**
     * listener to listen when item click (used when use non multiple spinner)
     */
    private OnItemClickListener onItemClickListener;
    private Drawable expandDrawable;
    private int selected = -1;
    private boolean multiple = false;
    private List<Integer> multipleSelected = new ArrayList<>();
    private OnSelectMultipleListener onSelectMultipleListener;
    private TextWatcher watcher;

    public BSSpinner(Context context) {
        super(context);
        init(null);
    }

    public BSSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BSSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setLongClickable(false);
        setFocusable(false);
        setSingleLine(true);
        expandTint = ContextCompat.getColor(getContext(), R.color.jrspinner_color_default);
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JRSpinner);
            if (typedArray != null) {
                title = typedArray.getString(R.styleable.JRSpinner_jrs_title) == null ? "Select" : typedArray.getString(
                        R.styleable.JRSpinner_jrs_title);
                expandTint = typedArray.getColor(R.styleable.JRSpinner_jrs_icon_tint, expandTint);
                multiple = typedArray.getBoolean(R.styleable.JRSpinner_jrs_multiple, false);
                typedArray.recycle();
            }
        }
        expandDrawable = ContextCompat.getDrawable(getContext(), R.drawable.jrspinnericon_expand);
        expandDrawable.setColorFilter(new PorterDuffColorFilter(expandTint, PorterDuff.Mode.SRC_IN));
        setIcon();
    }

    private void setIcon() {
        Drawable[] drawables = getCompoundDrawables();

        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], expandDrawable, drawables[3]);
    }

    public void setExpandTint(int expandTint) {
        this.expandTint = expandTint;
        postInvalidate();
    }

    public void setItems(ArrayList<getBSCoHinh> items) {
        this.items = items;
        postInvalidate();
    }

    public void setTitle(String title) {
        this.title = title;
        postInvalidate();
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnSelectMultipleListener(OnSelectMultipleListener onSelectMultipleListener) {
        this.onSelectMultipleListener = onSelectMultipleListener;
    }

    @Override
    public boolean isSuggestionsEnabled() {
        return false;
    }

    @Override
    public void postInvalidate() {
        super.postInvalidate();
        expandDrawable.setColorFilter(new PorterDuffColorFilter(expandTint, PorterDuff.Mode.SRC_IN));
        setIcon();
    }

    /**
     * set the selected item position when use non multiple spinner
     *
     * @param selected the position
     */
    protected void setSelected(int selected) {
        this.selected = selected;
    }

    /**
     * method to add search listener
     * @param watcher search box text watcher
     */
    public void addSearchListener(TextWatcher watcher){
        this.watcher = watcher;
    }


    /**
     *  set selected position. Can use for set default selected position
     * @param position selected position
     */
    public void select(int position){
        if (!multiple){
            selected = position;
            setText(items.get(position).getTen());
        }else{
            multipleSelected.add(position);
            setText(items.get(position).getTen());
        }

        if (onItemClickListener != null){
            onItemClickListener.onItemClick(selected);
        }

        if (onSelectMultipleListener != null){
            onSelectMultipleListener.onMultipleSelected(multipleSelected);
        }
    }

    /**
     * call when click on spinner view and show the dialog
     */
    @Override
    public boolean performClick() {
        if (!multiple) {
            dialog = BSDialog.newInstance(title, items, selected);
            if (watcher != null){
                dialog.addSearchListener(watcher);
            }
            dialog.setListener(onItemClickListener, BSSpinner.this);
            dialog.show(findActivity(getContext()).getSupportFragmentManager(), dialog.getTag());
        } else {
           /* multiDialog = MultipleDialog.newInstance(title, items, multipleSelected);
            if (watcher != null){
                multiDialog.addSearchListener(watcher);
            }
            multiDialog.setListener(onSelectMultipleListener, JRSpinner.this);
            multiDialog.show(findActivity(getContext()).getSupportFragmentManager(), multiDialog.getTag());*/
        }
        return super.performClick();
    }

    public void clear() {
        if (multiple) {
            multipleSelected.clear();
        } else {
            selected = -1;
        }
        setText("");
    }

    public static <T extends FragmentActivity> T findActivity(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }

        if (context instanceof FragmentActivity) {
            return (T) context;
        } else {
            ContextWrapper contextWrapper = (ContextWrapper) context;
            Context baseContext = contextWrapper.getBaseContext();
            if (baseContext == null) {
                throw new IllegalStateException("Activity was not found as base context of view!");
            }
            return findActivity(baseContext);
        }
    }

    protected void setSelected(List<Integer> selected) {
        multipleSelected.clear();
        multipleSelected.addAll(selected);
    }

    public boolean isHaveItems(){
        return items != null && items.size() > 0;
    }

    public void updateItems(ArrayList<getBSCoHinh> newItems){
        items = newItems;
        selected = -1;
        multipleSelected = new ArrayList<>();
        if (multiple){
           // multiDialog.updateItems(newItems);
        }else{
            dialog.updateItems(newItems);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnSelectMultipleListener {
        void onMultipleSelected(List<Integer> selectedPosition);
    }
}
