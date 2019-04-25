package com.fwkj.fw_root_library.module;

import android.app.Application;

import com.fwkj.fw_root_library.GlobalManager;
import com.fwkj.fw_root_library.delegate.AVNetDelegate;
import com.fwkj.fw_root_library.net.NetWorkManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author 付俊杰
 * @email 1025461682@qq.com
 * @phone 17684220995
 * @file description : 基本核心的准备类都在这里进行new便于全局使用
 */
@Module
public class DelegateModule {
    private GlobalManager manager;
    private Application application;

    public DelegateModule(Application application, GlobalManager manager) {
        this.application = application;
        this.manager = manager;
    }

    @Singleton
    @Provides
    public AVNetDelegate getAVNetManager() {
        return new AVNetDelegate(application, manager.getAvid(), manager.getAvkey(), manager.getAvObjects());
    }

    @Singleton
    @Provides
    public Retrofit getRetrofit() {
        NetWorkManager instance = NetWorkManager.getInstance(application);
        instance.init(manager.isDebugLog(), manager.getUrl());
        return instance.getRetrofit();
    }

    @Singleton
    @Provides
    public Application getApplication() {
        return application;
    }
}
