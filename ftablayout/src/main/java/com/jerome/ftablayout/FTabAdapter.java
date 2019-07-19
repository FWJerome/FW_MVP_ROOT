package com.jerome.ftablayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class FTabAdapter<T extends FTabEntity> extends BaseQuickAdapter<T, BaseViewHolder> {
    FTabAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FTabEntity item) {
        helper.setText(R.id.tv, item.getName());
    }
}
