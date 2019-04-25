package com.fwkj.fw_root_library.utils;

import android.content.Context;
import android.widget.ImageView;

import com.fwkj.fw_root_library.GlideApp;

public class ImageLoader {
    private static ImageLoader mImageLoader;
    private static Context mContext;

    public ImageLoader(Context context) {
        mContext = context;
    }

    public static void getInstance(Context context) {
        if (mImageLoader == null) {
            synchronized (ImageLoader.class) {
                if (mImageLoader == null) {
                    mImageLoader = new ImageLoader(context);
                }
            }
        }
    }

    public static void loadImage(Object url, ImageView imageView) {
        GlideApp.with(mContext).load(url).into(imageView);
    }

    public static void loadImageCircle(String url, ImageView imageView) {
        if (url != null) {
            url = url.replaceAll("\\\\", "/");
        }
        GlideApp.with(mContext)
                .load(url)
                .fitCenter()
                .circleCrop()
                .into(imageView);
    }
}
