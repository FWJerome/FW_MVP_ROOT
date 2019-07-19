package com.jerome.ftablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FTabView<T extends FTabEntity> extends RecyclerView {

    private FTabAdapter<T> mCustomAdapter;

    public FTabView(@NonNull Context context) {
        this(context, null);
    }

    public FTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(new LinearLayoutManager(context, HORIZONTAL, false));

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FTabView);
        //属性
        typedArray.recycle();

        mCustomAdapter = new FTabAdapter<>(R.layout.adapter_tab);
    }

    public FTabAdapter<T> getCustomAdapter() {
        return mCustomAdapter;
    }

    public void setCustomAdapter(FTabAdapter<T> customAdapter) {
        mCustomAdapter = customAdapter;
    }

    public static class Builder<T extends FTabEntity> {
        List<T> datas;
        int pace;
        int paceColor;
        int items;

        public void build(FTabView<T> tabView) {
            if (paceColor == 0) {
                paceColor = Color.parseColor("#ffffff");
            }
            //这里进行recycleview的刷新
            if (items == 0) {
                items = 4;
            }
            FTabAdapter<T> customAdapter = tabView.getCustomAdapter();
            tabView.setAdapter(customAdapter);
            tabView.addItemDecoration(new FCustomDecoration(pace, paceColor));
            customAdapter.setNewData(datas);
        }

        public Builder<T> setDatas(List<T> datas) {
            this.datas = datas;
            return this;
        }

        public Builder<T> showSpaceDp2Px(int pace) {
            this.pace = pace;
            return this;
        }

        public Builder<T> showSpaceColor(int paceColor) {
            this.paceColor = paceColor;
            return this;
        }

        public Builder<T> showItems(int items) {
            this.items = items;
            return this;
        }
    }
}
