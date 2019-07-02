package com.fwkj.fw_mvp_root.model;

import com.fwkj.fw_mvp_root.contract.MainActivityContract;
import com.fwkj.fw_mvp_root.contract.UserBean;
import com.fwkj.fw_root_library.BaseModel;

import io.reactivex.Observable;

public class MainActivityModel extends BaseModel implements MainActivityContract.model {
    public MainActivityModel() {
    }

    @Override
    public Observable<UserBean> getCode() {
        return mRetrofit.create(UserServices.class).getCode("17684220995");
    }
}