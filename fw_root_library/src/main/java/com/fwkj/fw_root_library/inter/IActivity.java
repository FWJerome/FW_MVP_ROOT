package com.fwkj.fw_root_library.inter;


import android.os.Bundle;

import com.fwkj.fw_root_library.component.DelegateComponent;

import androidx.annotation.LayoutRes;

public interface IActivity {
    /**
     * 初始化View
     * @param savedInstanceState
     */
    void initView(Bundle savedInstanceState);

    /**
     * 初始化事件
     */
    void initEvent();

    /**
     * 获取全局的component供子activity使用
     *
     * @param component
     */
    void initComponent(DelegateComponent component);

    /**
     * 获取视图xml
     *
     * @return
     */
    @LayoutRes
    int initLayout();
}
