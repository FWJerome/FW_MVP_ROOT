package com.fwkj.fw_root_library.net;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author 付俊杰
 * @email 1025461682@qq.com
 * @phone 17684220995
 * @file description :
 */

public class RequestBodyUtils {
    /**
     * 下发规则是key,value
     * 所以传的参数如 "user" "付俊杰"  ....
     *
     * @param objects
     */
    public static Map<String, RequestBody> getRequestBodyMap(final Context context, Object... objects) {
        Map<String, RequestBody> map = new HashMap<>();
        if (objects.length % 2 != 0) {
            ToastUtils.showShort("参数数量不一致");
            return map;
        }
        for (int i = 0; i < objects.length; i++) {
            if (!(objects[i] instanceof String)) {
                ToastUtils.showShort("第" + (i + 1) + "个网络请求key不是字符串");
            }
            String key = (String) objects[i];
            i++;
            if (objects[i] instanceof File) {
                File file = (File) objects[i];
                map.put(key + "\"; filename=\"" + file + "\"", RequestBody.create(MediaType.parse("image/png"), file));
            } else {
                map.put(key, RequestBody.create(MediaType.parse("text/plain"), objects[i] + ""));
            }
        }
        return map;
    }
}
