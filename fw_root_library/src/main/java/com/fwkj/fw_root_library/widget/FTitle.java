package com.fwkj.fw_root_library.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.fwkj.fw_root_library.R;

public class FTitle extends LinearLayout {

    private AppCompatImageView mImgLeft;
    private AppCompatTextView mTvRight;
    private AppCompatTextView mTvTitle;
    private AppCompatImageView mImgRight;

    public FTitle(Context context) {
        this(context, null);
    }

    public FTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FTitle);

        int leftIcon = typedArray.getResourceId(R.styleable.FTitle_leftIcon, -1);
        boolean leftIconVisible = typedArray.getBoolean(R.styleable.FTitle_leftIconVisibility, false);

        int rightIcon = typedArray.getResourceId(R.styleable.FTitle_rightIcon, -1);
        boolean rightIconVisible = typedArray.getBoolean(R.styleable.FTitle_rightIconVisibility, false);

        String rightText = typedArray.getString(R.styleable.FTitle_rightText);
        int rightColor = typedArray.getColor(R.styleable.FTitle_rightTextColor, -1);
        boolean rightVisible = typedArray.getBoolean(R.styleable.FTitle_rightTextVisibility, false);
        float rightTextSize = typedArray.getDimension(R.styleable.FTitle_rightTextSize, -1);

        String title = typedArray.getString(R.styleable.FTitle_title);
        int titleColor = typedArray.getColor(R.styleable.FTitle_titleTextColor, -1);
        boolean titleVisible = typedArray.getBoolean(R.styleable.FTitle_titleTextVisibility, false);
        float titleTextSize = typedArray.getDimension(R.styleable.FTitle_titleTextSize, -1);
        typedArray.recycle();

        View view = View.inflate(context, R.layout.widget_ftitle, this);
        mImgLeft = view.findViewById(R.id.imgLeft);
        mImgRight = view.findViewById(R.id.imgRight);
        mTvTitle = view.findViewById(R.id.tvTitle);
        mTvRight = view.findViewById(R.id.tvRight);

        mImgLeft.setVisibility(leftIconVisible ? VISIBLE : GONE);
        mTvTitle.setVisibility(titleVisible ? VISIBLE : GONE);
        mTvRight.setVisibility(rightVisible ? VISIBLE : GONE);
        mImgRight.setVisibility(rightIconVisible ? VISIBLE : GONE);

        if (leftIcon != -1) {
            mImgLeft.setImageResource(leftIcon);
        }
        if (rightIcon != -1) {
            mImgRight.setImageResource(rightIcon);
        }

        mTvTitle.setText(title);
        mTvTitle.setTextColor(titleColor);
        mTvTitle.setTextSize(ConvertUtils.px2sp(titleTextSize));

        mTvRight.setText(rightText);
        mTvRight.setTextColor(rightColor);
        mTvRight.setTextSize(ConvertUtils.px2sp(rightTextSize));

        ConstraintLayout.LayoutParams imgLeftLayoutParams = (ConstraintLayout.LayoutParams) mImgLeft.getLayoutParams();
        imgLeftLayoutParams.width = ConvertUtils.dp2px(50);
        imgLeftLayoutParams.height = ConvertUtils.dp2px(55);
        mImgLeft.setPadding(ConvertUtils.dp2px(10), ConvertUtils.dp2px(10), ConvertUtils.dp2px(10), ConvertUtils.dp2px(10));
        mImgLeft.setLayoutParams(imgLeftLayoutParams);

        ConstraintLayout.LayoutParams imgRightLayoutParams = (ConstraintLayout.LayoutParams) mImgRight.getLayoutParams();
        imgRightLayoutParams.width = ConvertUtils.dp2px(50);
        imgRightLayoutParams.height = ConvertUtils.dp2px(55);
        mImgRight.setPadding(ConvertUtils.dp2px(10), ConvertUtils.dp2px(10), ConvertUtils.dp2px(10), ConvertUtils.dp2px(10));
        mImgRight.setLayoutParams(imgRightLayoutParams);

        setPadding(ConvertUtils.dp2px(10), ConvertUtils.dp2px(10), ConvertUtils.dp2px(10), ConvertUtils.dp2px(10));
    }

    public void setLeftFinish(final Activity activity) {
        mImgLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    public void setOnRightTextClickListener(OnClickListener onClickListener) {
        mTvRight.setOnClickListener(onClickListener);
    }

    public void setLeftOnClickListener(OnClickListener onClickListener) {
        mImgLeft.setOnClickListener(onClickListener);
    }

    public void setRightIconVisibility(boolean b) {
        mImgRight.setVisibility(b ? VISIBLE : GONE);
    }

    public void setTitle(String s) {
        mTvTitle.setText(s);
    }

    public ImageView getIvRight() {
        return mImgRight;
    }

    public void setOnRightImageViewClickListener(OnClickListener onClickListener) {
        mImgRight.setOnClickListener(onClickListener);
    }

    public void setRightVisible(boolean b) {
        mTvRight.setVisibility(b ? VISIBLE : GONE);
    }
}
