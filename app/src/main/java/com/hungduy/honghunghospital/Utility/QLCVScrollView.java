package com.hungduy.honghunghospital.Utility;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class QLCVScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener = null;

    public QLCVScrollView(Context context)
    {
        super(context);
    }

    public QLCVScrollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public QLCVScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener  scrollViewListener)
    {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    public void onScrollChanged(int x, int y, int oldx, int oldy)
    {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null)
        {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}
