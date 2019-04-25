package com.fwkj.fw_root_library.utils;

import com.blankj.utilcode.util.ObjectUtils;

public class InputTextUtils {

    /**
     * 判断输入框是否输入的方法
     *
     * @param values 奇数获取的值  偶数是未输入要提示的文字
     * @return
     */
    public static String isEmpty(Object... values) {
        String msg = null;
        if (values.length % 2 != 0) {
            try {
                throw new IllegalAccessException("请成对传值");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }
        for (int i = 0; i < values.length; i++) {
            if (ObjectUtils.isEmpty(values[i])) {
                msg = (String) values[i + 1];
                break;
            }
        }
        return msg;
    }
}
