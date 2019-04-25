package com.fwkj.fw_root_library.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;

import java.util.List;

public abstract class PermissionDialog {
    //PermissionConstants.STORAGE, PermissionConstants.LOCATION, PermissionConstants.CAMERA, PermissionConstants.PHONE
    public void getPermission(final Context pContext, @PermissionConstants.Permission String... permissions) {
        PermissionUtils.permission(permissions)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(final ShouldRequest shouldRequest) {
                        if (pContext == null) {
                            return;
                        }
                        LogUtils.d("申请权限");
                        new AlertDialog.Builder(pContext)
                                .setTitle("请求获取以下权限")
                                .setMessage("请前往设置打开相关权限")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        shouldRequest.again(true);
                                    }
                                })
                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        shouldRequest.again(false);
                                    }
                                })
                                .setCancelable(false)
                                .create()
                                .show();
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        LogUtils.d("拥有权限" + permissionsGranted);
                        hasThis();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            if (pContext == null) {
                                return;
                            }
                            LogUtils.d("设置需要获取 " + permissionsDeniedForever + ", " + permissionsDenied);
                            new AlertDialog.Builder(pContext)
                                    .setTitle("请求获取以下权限")
                                    .setMessage("请前往设置打开相关权限")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            PermissionUtils.launchAppDetailsSettings();
                                        }
                                    })
                                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setCancelable(false)
                                    .create()
                                    .show();
                        }
                    }
                })
                .theme(new PermissionUtils.ThemeCallback() {
                    @Override
                    public void onActivityCreate(Activity activity) {
                        ScreenUtils.setFullScreen(activity);
                    }
                })
                .request();
    }

    public abstract void hasThis();
}

