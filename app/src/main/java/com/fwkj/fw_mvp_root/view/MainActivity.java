package com.fwkj.fw_mvp_root.view;

import android.os.Bundle;

import com.fwkj.fw_mvp_root.R;
import com.fwkj.fw_mvp_root.component.DaggerMainActivityComponent;
import com.fwkj.fw_mvp_root.component.MainActivityModule;
import com.fwkj.fw_mvp_root.contract.MainActivityContract;
import com.fwkj.fw_mvp_root.presenter.MainActivityPresenter;
import com.fwkj.fw_root_library.BaseActivity;
import com.fwkj.fw_root_library.component.DelegateComponent;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainActivityContract.view {
    @Inject
    MainActivityPresenter mMainActivityPresenter;

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initComponent(DelegateComponent component) {
        DaggerMainActivityComponent.builder()
                .delegateComponent(component)
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_mainactivity;
    }
}
