package com.fwkj.fw_mvp_root.presenter;

import com.fwkj.fw_mvp_root.contract.MainActivityContract;
import com.fwkj.fw_mvp_root.model.MainActivityModel;

import javax.inject.Inject;

public class MainActivityPresenter {
    private MainActivityContract.view view;
    private MainActivityModel model;

    public MainActivityPresenter(MainActivityContract.view view, MainActivityModel model) {
        this.view = view;
        this.model = model;
    }
}
