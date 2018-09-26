package com.example.apoorvdubey.udacitymoviestageone.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class AutoFitGridLayoutAdapter extends GridLayoutManager {

    private int width;
    private boolean mWidthChanged = true;

    public AutoFitGridLayoutAdapter(Context context, int columnWidth) {
        super(context, 1);
        setColumnWidth(columnWidth);
    }

    public void setColumnWidth(int newColumnWidth) {
        if (newColumnWidth > 0 && newColumnWidth != width) {
            width = newColumnWidth;
            mWidthChanged = true;
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (mWidthChanged && width > 0) {
            int totalSpace;
            if (getOrientation() == VERTICAL) {
                totalSpace = getWidth() - getPaddingRight() - getPaddingLeft();
            } else {
                totalSpace = getHeight() - getPaddingTop() - getPaddingBottom();
            }
            int spanCount = Math.max(1, totalSpace / width);
            setSpanCount(spanCount);
            mWidthChanged = false;
        }
        super.onLayoutChildren(recycler, state);
    }
}