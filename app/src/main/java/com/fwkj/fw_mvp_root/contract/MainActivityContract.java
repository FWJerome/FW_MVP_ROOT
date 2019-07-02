package com.fwkj.fw_mvp_root.contract;

import io.reactivex.Observable;

public interface MainActivityContract {
    interface view {

    }

    interface model {

        Observable<UserBean> getCode();

    }
}
