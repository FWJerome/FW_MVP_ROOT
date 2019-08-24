package com.fwkj.fw_root_library.transformer;

import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.android.ActivityEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class TransformerUtils {
    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 带对话框的请求
     * @Date 8:48 2019/5/26
     * @Param
     **/
    public static <T> ObservableTransformer<T, T> transformerUtil(final LifecycleProvider view) {
        return transformerUtil(view, false);
    }

    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 自己控制对话框的请求
     * @Date 8:48 2019/5/26
     * @Param
     **/
    public static <T> ObservableTransformer<T, T> transformerUtil(final LifecycleProvider view, final Boolean isShowDialog) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .compose(view.<T>bindToLifecycle())
                        .compose(CommScheduleProvider.<T>rxSchedulerHelper(isShowDialog))
                        .compose(ResponseTransformer.<T>handleResult());
            }
        };
    }
}
