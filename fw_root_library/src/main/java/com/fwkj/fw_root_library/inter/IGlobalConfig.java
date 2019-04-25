package com.fwkj.fw_root_library.inter;

import android.app.Application;

import com.fwkj.fw_root_library.GlobalManager;

import androidx.annotation.NonNull;

public interface IGlobalConfig {

    void applyOptions(Application application, @NonNull GlobalManager.Builder builder);

    /**
     * 优先于AV初始化要执行的事，例如子类化等
     */
    void beforeInit();
}
