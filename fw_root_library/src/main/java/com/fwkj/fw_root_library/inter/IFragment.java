package com.fwkj.fw_root_library.inter;

import android.view.View;

import com.fwkj.fw_root_library.component.DelegateComponent;

import androidx.annotation.LayoutRes;

public interface IFragment {
    /**
     * 可以进行初始化
     */
    void init();

    /**
     * 获取全局的component供子fragment使用
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
