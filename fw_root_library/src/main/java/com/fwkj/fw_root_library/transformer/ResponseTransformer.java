package com.fwkj.fw_root_library.transformer;

import com.fwkj.fw_root_library.net.ApiException;
import com.fwkj.fw_root_library.net.CustomException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * @author 付俊杰
 * @time 2018/7/17 13:50
 * @email 1025461682@qq.com
 * @phone 17684220995
 * @Des 对返回的数据进行处理，区分异常的情况。
 */

public class ResponseTransformer {
    public static <T> ObservableTransformer<T, T> handleResult() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .onErrorResumeNext(new ErrorResumeFunction<T>())
                        .flatMap(new ResponseFunction<T>());
            }
        };
    }


    /**
     * 异常，比如本地无无网络请求，Json数据解析错误，服务器的异常等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<T>> {
        @Override
        public ObservableSource<T> apply(Throwable throwable) throws Exception {
            ApiException exception = CustomException.handleException(throwable);
            return Observable.error(exception);
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<T, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(T tResponse) {
            return Observable.just(tResponse);
        }
    }


}
