package com.jerome.ftablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

public class FTabView<T extends FTabEntity> extends RecyclerView {

    private FTabAdapter<T> mCustomAdapter;
    private FCustomDecoration mFCustomDecoration = new FCustomDecoration();

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

    public FCustomDecoration getFCustomDecoration() {
        return mFCustomDecoration;
    }

    public void setFCustomDecoration(FCustomDecoration FCustomDecoration) {
        mFCustomDecoration = FCustomDecoration;
    }

    public void setCustomAdapter(FTabAdapter<T> customAdapter) {
        mCustomAdapter = customAdapter;
    }

    public static class Builder<T extends FTabEntity> {
        private FCustomDecoration customDecoration;
        private List<T> datas;
        private FTabView<T> tabView;
        private FTabAdapter<T> customAdapter;

        public Builder(FTabView<T> tabView) {
            this.tabView = tabView;
            customAdapter = tabView.getCustomAdapter();
            customDecoration = tabView.getFCustomDecoration();
        }

        public void build() {
            tabView.setAdapter(customAdapter);
            tabView.addItemDecoration(customDecoration);
            customAdapter.setNewData(datas);
        }

        public Builder<T> setDatas(List<T> datas) {
            this.datas = datas;
            return this;
        }

        public Builder<T> showSpaceDp2Px(int pace) {
            customDecoration.setPace(pace);
            return this;
        }

        public Builder<T> showSpaceColor(int paceColor) {
            customDecoration.setPaceColor(paceColor);
            return this;
        }

        public Builder<T> showItems(int items) {
            customDecoration.setItems(items);
            return this;
        }

        public Builder<T> spaceTopAndBottom(int topAndBottom) {
            customDecoration.setTopAndBottom(topAndBottom);
            return this;
        }

        public Builder addOnClickListener(BaseQuickAdapter.OnItemClickListener clickListener) {
            customAdapter.setOnItemClickListener(clickListener);
            return this;
        }
    }
}
