package com.fwkj.fw_root_library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.fwkj.fw_root_library.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class FTabLayout extends RecyclerView {
    public FTabLayout(@NonNull Context context) {
        this(context, null);
    }

    public FTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.FTabLayout);

        CharSequence[] array = attributes.getTextArray(R.styleable.FTabLayout_titles);
        //MeasureSpec.EXACTLY 等值于 LayoutParams.MATCH_PARENT
        float dimension = attributes.getDimension(R.styleable.FTabLayout_vpHeight, -1);
        attributes.recycle();
    }
}
