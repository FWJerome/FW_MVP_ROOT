package com.fwkj.fw_mvp_root;

import android.app.Application;

import androidx.annotation.NonNull;

import com.fwkj.fw_root_library.GlobalManager;
import com.fwkj.fw_root_library.inter.IGlobalConfig;
import com.fwkj.fw_root_library.logging.LoggingInterceptor;

import okhttp3.Interceptor;

public class Global implements IGlobalConfig {
    @Override
    public void applyOptions(Application application, @NonNull GlobalManager.Builder builder) {
        builder
                .baseUrl("https://www.dabeicar.com/api/")
                .debugLog(true)
                .allowBlankjUtilcode(application)
                .setInterceptor(new LoggingInterceptor.BeforeRequestInter() {
                    @Override
                    public LoggingInterceptor.Builder intercept(Interceptor.Chain chain, LoggingInterceptor.Builder builder) {
                        builder.addHeader("123",System.currentTimeMillis()+"123");
                        return builder;
                    }
                })
                .build();
    }

    @Override
    public void beforeInit() {

    }
}
