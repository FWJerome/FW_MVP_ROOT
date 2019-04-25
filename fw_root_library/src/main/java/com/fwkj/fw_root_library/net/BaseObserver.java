package com.fwkj.fw_root_library.net;

import com.blankj.utilcode.util.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 付俊杰
 * @time 2018/7/17 15:22
 * @email 1025461682@qq.com
 * @phone 17684220995
 * @Des
 */
public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T pO) {
        fOnNext(pO);
    }

    @Override
    public void onError(Throwable e) {
        try {
            ApiException e1 = (ApiException) e;
            ToastUtils.showLong(e1.getDisplayMessage());
        } catch (Exception ex) {
            ToastUtils.showLong(ex.getMessage());
        }
    }

    @Override
    public void onComplete() {
    }

    public abstract void fOnNext(T pO);
}
