package com.fwkj.fw_root_library;

import android.app.Application;

import com.fwkj.fw_root_library.component.DelegateComponent;
import com.fwkj.fw_root_library.delegate.GlobalDelegate;
import com.fwkj.fw_root_library.inter.IGlobalConfig;

import me.yokeyword.fragmentation.Fragmentation;

public abstract class Root_App extends Application {
    private static GlobalDelegate mDelegate;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mDelegate == null) {
            mDelegate = new GlobalDelegate(this);
        }
        mDelegate.create(getConfig());
        mDelegate.init();
        Fragmentation.builder()
                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .install();
    }

    /**
     * 获取配置
     *
     * @return
     */
    protected abstract IGlobalConfig getConfig();

    public static DelegateComponent getDelegateComponent() {
        return mDelegate.getDelegateComponent();
    }
}
