package com.fwkj.fw_root_library;

import android.os.Bundle;

import com.fwkj.fw_root_library.inter.IActivity;
import com.fwkj.fw_root_library.utils.GlobalDialog;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import androidx.annotation.Nullable;

/**
 * @author 付俊杰
 * @email 1025461682@qq.com
 * @phone 17684220995
 * @file description : 所有activity都需要继承此
 */
public abstract class BaseActivity extends RxAppCompatActivity implements IActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent(Root_App.getDelegateComponent());
        if (initLayout() != 0) {
            setContentView(initLayout());
        } else {
            throw new IllegalArgumentException("please initLayout() return layout");
        }
        initView(savedInstanceState);
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalDialog.accident();
    }
}
