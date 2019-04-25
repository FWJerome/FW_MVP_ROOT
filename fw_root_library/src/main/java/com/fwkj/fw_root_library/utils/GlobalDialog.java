package com.fwkj.fw_root_library.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ScreenUtils;

public class GlobalDialog {
    public static Dialog mDialog;

    public static void show() {
        if (mDialog == null) {
            mDialog = new AlertDialog.Builder(ActivityUtils.getTopActivity()).setView(new ProgressBar(ActivityUtils.getTopActivity())).setCancelable(false).create();
        }
        try {
            if (!mDialog.isShowing()) {
                mDialog.show();
                Window window = mDialog.getWindow();
                WindowManager.LayoutParams params = window.getAttributes();
//        params.width = activity.getWindowManager().getDefaultDisplay().getRight();
                params.width = ScreenUtils.getScreenWidth() / 4;
                window.setAttributes(params);
            }
        } catch (Exception ignored) {
        }
    }

    public static void hide() {
        if (mDialog == null) {
            return;
        }
        try {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
                mDialog = null;
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * 视图意外崩溃处理
     */
    public static void accident() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
