package com.fwkj.fw_root_library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.blankj.utilcode.util.ObjectUtils;
import com.fwkj.fw_root_library.GlideApp;
import com.fwkj.fw_root_library.GlideRequest;

public class ImageLoader {
    private Builder builder;
    private GlideRequest<Drawable> mGlideRequest;

    public ImageLoader(Builder builder) {
        this.builder = builder;
        init();
    }

    @SuppressLint("CheckResult")
    public void init() {
        if (!ObjectUtils.isEmpty(builder.tagetImage)) {
            Object tagetImage = builder.tagetImage;
            if (tagetImage instanceof String) {
                builder.tagetImage = ((String) builder.tagetImage).replaceAll("\\\\", "/");
            }
        }
        mGlideRequest = GlideApp.with(builder.context)
                .load(builder.tagetImage)
                .error(builder.errImage)
                .fitCenter()
                .placeholder(builder.placeImage);
        if (builder.isCircle) {
            mGlideRequest.circleCrop();
        }
    }

    public void create() {
        mGlideRequest.into(builder.tagetImageView);
    }

    public static class Builder {
        boolean isCircle;
        //占位
        int placeImage;
        int errImage;
        Object tagetImage;
        ImageView tagetImageView;
        Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder tagetImageUrl(Object tagetImage) {
            this.tagetImage = tagetImage;
            return this;
        }

        public Builder placeImage(int placeImage) {
            this.placeImage = placeImage;
            return this;
        }

        public Builder errImage(int errImage) {
            this.errImage = errImage;
            return this;
        }

        public Builder isCircle(boolean isCircle) {
            this.isCircle = isCircle;
            return this;
        }

        public Builder setTagetImageView(ImageView tagetImageView) {
            this.tagetImageView = tagetImageView;
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }
    }
}
