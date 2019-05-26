package com.fwkj.fw_root_library.transformer;

import com.blankj.utilcode.util.LogUtils;
import com.fwkj.fw_root_library.utils.GlobalDialog;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class CommScheduleProvider {
    /**
     * 统一线程处理
     *
     * @param <T>
     * @param isShowDialog
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper(final Boolean isShowDialog) {    //compose简化线程
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(getIo(isShowDialog))
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                if (isShowDialog) {
                                    GlobalDialog.hide();
                                    LogUtils.d("关闭进度框");
                                }
                            }
                        });
            }
        };
    }

    private static Scheduler getIo(Boolean isShowDialog) {
        if (isShowDialog) {
            GlobalDialog.show();
            LogUtils.d("显示进度框");
        }
        return Schedulers.io();
    }
}
