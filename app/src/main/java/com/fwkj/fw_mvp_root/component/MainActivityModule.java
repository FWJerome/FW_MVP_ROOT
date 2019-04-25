package com.fwkj.fw_mvp_root.component;

import dagger.Module;
import dagger.Provides;

import com.fwkj.fw_mvp_root.contract.MainActivityContract;
import com.fwkj.fw_mvp_root.presenter.MainActivityPresenter;
import com.fwkj.fw_mvp_root.model.MainActivityModel;

@Module
public class MainActivityModule {
    private MainActivityContract.view mView;

    public MainActivityModule(MainActivityContract.view pView) {
        mView = pView;
    }

    @Provides
    public MainActivityPresenter getP() {
        return new MainActivityPresenter(mView, new MainActivityModel());
    }
}
