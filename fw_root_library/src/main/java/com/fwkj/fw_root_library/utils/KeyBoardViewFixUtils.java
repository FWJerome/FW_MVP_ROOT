package com.fwkj.fw_root_library.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ScreenUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 通过软键盘监听，判断焦点在哪个监听view上去进行适配
 */
public class KeyBoardViewFixUtils {

    private Activity activity;
    private EditText[] views;
    private ScrollView scroll;

    public KeyBoardViewFixUtils(Activity activity) {
        this.activity = activity;
    }

    public KeyBoardViewFixUtils attchView(EditText... views) {
        this.views = views;
        return this;
    }

    @SuppressLint("CheckResult")
    public void fix() {
        KeyboardUtils.registerSoftInputChangedListener(activity, new KeyboardUtils.OnSoftInputChangedListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onSoftInputChanged(final int height) {
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(height);
                        emitter.onComplete();
                    }
                }).flatMap(new Function<Integer, ObservableSource<EditText>>() {
                    @Override
                    public ObservableSource<EditText> apply(Integer integer) throws Exception {
                        EditText e = null;
                        for (EditText editText : views) {
                            if (editText.isFocused()) {
                                e = editText;
                                e.setTag(integer);
                                break;
                            }
                        }
                        final EditText finalE = e;
                        return Observable.create(new ObservableOnSubscribe<EditText>() {
                            @Override
                            public void subscribe(ObservableEmitter<EditText> emitter) throws Exception {
                                if (finalE == null) {
                                    emitter.onError(new Exception());
                                } else {
                                    emitter.onNext(finalE);
                                }
                            }
                        });
                    }
                }).subscribe(new Observer<EditText>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(EditText editText) {
                        if (((Integer) editText.getTag()) == 0) {
                            //还原视图
                            final FrameLayout contentView = activity.findViewById(android.R.id.content);
                            ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
                            layoutParams.height = ScreenUtils.getScreenHeight();
                            contentView.setLayoutParams(layoutParams);
                            return;
                        }
                        int[] location = new int[2];
                        editText.getLocationOnScreen(location);
                        int x = location[0];
                        //当前EditTextY坐标
                        int y = location[1];
                        //获取edittext自身高度，加上y也就是左下角的y坐标值
                        y += editText.getBottom();
                        //软键盘高度
                        int softHeight = (int) editText.getTag();
                        //当y大于屏幕高度减去软键盘高度说明需要上移，反之
                        if (y > ScreenUtils.getScreenHeight() - softHeight) {
                            //当是scroll存在的时候
                            if (scroll != null) {
                                scroll.scrollTo(0, y-(ScreenUtils.getScreenHeight() - softHeight));
                            }
                            final FrameLayout contentView = activity.findViewById(android.R.id.content);
                            ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
                            layoutParams.height = ScreenUtils.getScreenHeight() - (y - (ScreenUtils.getScreenHeight() - softHeight) + editText.getBottom() + 40);
                            contentView.setLayoutParams(layoutParams);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });
    }

    /**
     * 注销
     */
    public void unFix() {
//        KeyboardUtils.unregisterSoftInputChangedListener(activity);
    }

    public void attchView(ScrollView scroll, EditText... views) {
        this.scroll = scroll;
        this.views = views;
    }
}
