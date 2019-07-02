package com.fwkj.fw_mvp_root.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.fwkj.fw_mvp_root.contract.MainActivityContract;
import com.fwkj.fw_mvp_root.contract.UserBean;
import com.fwkj.fw_mvp_root.model.MainActivityModel;
import com.fwkj.fw_root_library.net.BaseObserver;
import com.fwkj.fw_root_library.transformer.TransformerUtils;
import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.android.ActivityEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class MainActivityPresenter {
    private MainActivityContract.view view;
    private MainActivityModel model;

    public MainActivityPresenter(MainActivityContract.view view, MainActivityModel model) {
        this.view = view;
        this.model = model;
    }

    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 有对话框的
     * @Date 8:50 2019/5/26
     * @Param
     **/
    public void netDialog() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                Thread.sleep(5000);
                emitter.onNext(true);
                emitter.onComplete();
            }
        })
                .compose(TransformerUtils.<Boolean>transformerUtil((LifecycleProvider<ActivityEvent>) view, true))
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void fOnNext(Boolean pO) {
                        ToastUtils.showLong("请求结束");
                    }
                });
    }

    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 没有对话框
     * @Date 8:51 2019/5/26
     * @Param
     **/
    public void noNetDialog() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                Thread.sleep(5000);
                emitter.onNext(true);
                emitter.onComplete();
            }
        })
                .compose(TransformerUtils.<Boolean>transformerUtil((LifecycleProvider<ActivityEvent>) view, false))
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void fOnNext(Boolean pO) {
                        ToastUtils.showLong("请求结束");
                    }
                });
    }

    public void getCode() {
        model.getCode()
                .compose(TransformerUtils.<UserBean>transformerUtil((LifecycleProvider<ActivityEvent>) view))
                .subscribe(new BaseObserver<UserBean>() {
                    @Override
                    public void fOnNext(UserBean pO) {
                    }
                });
    }
}
