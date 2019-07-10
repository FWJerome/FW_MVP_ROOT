package com.fwkj.fw_root_library.delegate;

import android.app.Application;

import com.fwkj.fw_root_library.GlobalManager;
import com.fwkj.fw_root_library.component.DaggerDelegateComponent;
import com.fwkj.fw_root_library.component.DelegateComponent;
import com.fwkj.fw_root_library.inter.IGlobalConfig;
import com.fwkj.fw_root_library.module.DelegateModule;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * @author 付俊杰
 * @email 1025461682@qq.com
 * @phone 17684220995
 * @file description : 全局代理类 网络代理,Crash代理等，核心实现在DelegateModule
 */

public class GlobalDelegate {
    private Application application;
    private DelegateComponent mDelegateComponent;
    @Inject
    AVNetDelegate mAVNetDelegate;


    public GlobalDelegate(Application application) {
        this.application = application;
    }

    /**
     * 开始进行配置文件获取，完成代理对象的所有配置
     *
     * @param config
     */
    public void create(IGlobalConfig config) {
        if (config == null) {
            throw new NullPointerException("config can not be empty");
        }
        mDelegateComponent = DaggerDelegateComponent.builder()
                .delegateModule(new DelegateModule(application, getGlobalManager(config)))
                .build();
        mDelegateComponent.inject(this);
    }

    private GlobalManager getGlobalManager(IGlobalConfig config) {
        GlobalManager.Builder builder = new GlobalManager.Builder();
        //供参数配置类获取application，和builder
        config.beforeInit();
        config.applyOptions(application, builder);
        builder.configImageLocader(application);
        return builder.build();
    }

    public DelegateComponent getDelegateComponent() {
        return mDelegateComponent;
    }

    public void init() {

    }
}
