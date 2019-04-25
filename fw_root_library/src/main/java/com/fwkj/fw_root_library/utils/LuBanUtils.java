package com.fwkj.fw_root_library.utils;

import android.content.Context;

import com.fwkj.fw_root_library.transformer.ResponseTransformer;
import com.fwkj.fw_root_library.transformer.SchedulerProvider;
import com.fwkj.fw_root_library.transformer.TransformerUtils;
import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import top.zibin.luban.Luban;

public class LuBanUtils {
    public static void compress(final Context context, final File file, final CompressListener listener) {
        Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> emitter) throws Exception {
                File newFile = Luban.with(context).load(file).get().get(0);
                emitter.onNext(newFile);
                emitter.onComplete();
            }
        })
                .compose(SchedulerProvider.getInstance().<File>applySchedulers(new SchedulerProvider.IoThingInter() {
                    @Override
                    public void io() {
                        GlobalDialog.show();
                    }
                }))
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        GlobalDialog.hide();
                    }
                })
                .compose(ResponseTransformer.<File>handleResult())
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(File file) {
                        listener.result(file);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface CompressListener {
        void result(File file);
    }
}
