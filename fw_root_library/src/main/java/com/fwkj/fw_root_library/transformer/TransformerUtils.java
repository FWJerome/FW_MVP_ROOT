package com.fwkj.fw_root_library.transformer;

import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.android.ActivityEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class TransformerUtils {
    public static <T> ObservableTransformer<T, T> transformerUtil(final LifecycleProvider<ActivityEvent> view) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .compose(view.<T>bindToLifecycle())
                        .compose(CommScheduleProvider.<T>rxSchedulerHelper())
                        .compose(ResponseTransformer.<T>handleResult());
            }
        };
    }
}
