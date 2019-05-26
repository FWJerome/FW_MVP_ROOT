package com.fwkj.fw_mvp_root;

import android.util.Log;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private String TAG = "ExampleUnitTest";

    @Test
    public void addition_isCorrect() {
        List<String> aList = Arrays.asList("a1", "b1", "c1", "d1");
        List<String> bList = Arrays.asList("a2", "b2", "c2", "d2");
        Observable.just(aList, bList)
                .concatMap(new Function<List<String>, ObservableSource<List<File>>>() {
                    @Override
                    public ObservableSource<List<File>> apply(final List<String> strings) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<List<File>>() {
                            @Override
                            public void subscribe(ObservableEmitter<List<File>> emitter) throws Exception {
                                List<File> files = new ArrayList<>();
                                for (String s : strings) {
                                    files.add(new File(s));
                                }
                                emitter.onNext(files);
                                emitter.onComplete();
                            }
                        });
                    }
                })
                .subscribe(new Observer<List<File>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<File> files) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}