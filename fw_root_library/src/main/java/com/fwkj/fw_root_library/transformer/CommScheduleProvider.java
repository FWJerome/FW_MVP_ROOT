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
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(getIo())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                GlobalDialog.hide();
                                LogUtils.d("关闭进度框");
                            }
                        });
            }
        };
    }

    private static Scheduler getIo() {
        GlobalDialog.show();
        LogUtils.d("显示进度框");
        return Schedulers.io();
    }
}
