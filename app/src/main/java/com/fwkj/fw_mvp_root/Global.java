package com.fwkj.fw_mvp_root;

import android.app.Application;

import com.fwkj.fw_root_library.GlobalManager;
import com.fwkj.fw_root_library.inter.IGlobalConfig;

import androidx.annotation.NonNull;

public class Global implements IGlobalConfig {
    @Override
    public void applyOptions(Application application, @NonNull GlobalManager.Builder builder) {
        builder
                .baseUrl("https://www.dabeicar.com/api/")

                .allowBlankjUtilcode(application)
                .build();
    }

    @Override
    public void beforeInit() {

    }
}
