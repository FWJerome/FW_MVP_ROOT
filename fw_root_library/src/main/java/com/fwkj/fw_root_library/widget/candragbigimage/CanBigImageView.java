package com.fwkj.fw_root_library.widget.candragbigimage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fwkj.fw_root_library.GlideApp;

import razerdp.basepopup.BasePopupWindow;

@SuppressLint("AppCompatCustomView")
public class CanBigImageView extends ImageView implements View.OnClickListener {

    private PicBigPopup mPicBigPopup;
    private String url;

    public CanBigImageView(Context context) {
        this(context, null);
    }

    public CanBigImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanBigImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mPicBigPopup == null) {
            mPicBigPopup = new PicBigPopup(getContext());
            mPicBigPopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mPicBigPopup = null;
                }
            });
            mPicBigPopup.showPopupWindow();
        } else {
            mPicBigPopup.dismiss();
            mPicBigPopup = null;
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    class PicBigPopup extends BasePopupWindow {
        private BaseDragZoomImageView mImageView;

        PicBigPopup(Context context) {
            super(context);
            try {
                GlideApp.with(getContext()).load(url).into(mImageView);
            } catch (Exception e) {
            }
        }

        @Override
        public View onCreateContentView() {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout.setBackgroundColor(Color.parseColor("#000000"));
            linearLayout.setGravity(Gravity.CENTER);

            mImageView = new BaseDragZoomImageView(getContext());
            mImageView.setScaleType(ScaleType.MATRIX);
            mImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            linearLayout.addView(mImageView);
            return linearLayout;
        }

    }
}
