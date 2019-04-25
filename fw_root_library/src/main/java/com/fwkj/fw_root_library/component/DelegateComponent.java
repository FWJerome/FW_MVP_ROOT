package com.fwkj.fw_root_library.component;

import android.app.Application;

import com.fwkj.fw_root_library.delegate.AVNetDelegate;
import com.fwkj.fw_root_library.delegate.GlobalDelegate;
import com.fwkj.fw_root_library.module.DelegateModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * @author 付俊杰
 * @email 1025461682@qq.com
 * @phone 17684220995
 * @file description : 代理的对象注入
 */
@Singleton
@Component(modules = DelegateModule.class)
public interface DelegateComponent {
    /**
     * 获取全局learnCloud代理对象
     *
     * @return
     */
    AVNetDelegate getDelege();

    /**
     * 全局application对象
     *
     * @return
     */
    Application application();

    /**
     * 全局的Retrofit
     */
    Retrofit getRetrofit();

    /**
     * @param delegate
     */
    void inject(GlobalDelegate delegate);
}
