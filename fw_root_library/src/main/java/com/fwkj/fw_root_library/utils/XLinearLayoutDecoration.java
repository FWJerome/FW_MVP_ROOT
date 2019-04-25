package com.fwkj.fw_root_library.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class XLinearLayoutDecoration extends RecyclerView.ItemDecoration {
    private int spacing;
    private int orieation;

    public XLinearLayoutDecoration(int spacing) {
        this.spacing = spacing;
    }

    public XLinearLayoutDecoration(int spacing, int orieation) {
        this.spacing = spacing;
        this.orieation = orieation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (orieation == LinearLayoutManager.HORIZONTAL) {
            outRect.left = spacing / 2;
            outRect.right = spacing / 2;
        } else {
            outRect.bottom = spacing;
        }
    }
}
