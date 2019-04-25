package com.fwkj.fw_root_library;

import android.app.Application;

import com.fwkj.fw_root_library.component.DaggerBaseModelComponent;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class BaseModel {
    @Inject
    protected Retrofit mRetrofit;
    @Inject
    protected Application mApplication;

    public BaseModel() {
        DaggerBaseModelComponent
                .builder()
                .delegateComponent(Root_App.getDelegateComponent())
                .build()
                .inject(this);
    }
}
