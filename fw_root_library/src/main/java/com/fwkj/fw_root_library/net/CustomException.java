package com.fwkj.fw_root_library.net;

import android.net.ParseException;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

public class CustomException {
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;

    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;

    /**
     * 网络错误
     */
    public static final int NETWORK_ERROR = 1002;

    /**
     * 协议错误
     */
    public static final int HTTP_ERROR = 1003;

    public static ApiException handleException(Throwable e) {
        LogUtils.e(e);
        ApiException ex = null;
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //解析错误
            ex = new ApiException(PARSE_ERROR, "解析错误");
            return ex;
        } else if (e instanceof ConnectException) {
            //网络错误
            ex = new ApiException(NETWORK_ERROR, "网络异常");
            return ex;
        } else if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
            //连接错误
            ex = new ApiException(NETWORK_ERROR, "服务器连接不上了");
            return ex;
        } else if (e instanceof HttpException) {
            String msg;
            if (((HttpException) e).code() == 500) {
                msg = "服务器发生错误";
            } else if (((HttpException) e).code() == 404) {
                msg = "请求地址不存在" + ((HttpException) e).response().toString();
            } else if (((HttpException) e).code() == 403) {
                msg = "请求被服务器拒绝";
            } else if (((HttpException) e).code() == 307) {
                msg = "请求被重定向到其他页面";
            } else {
                msg = ((HttpException) e).message();
            }
            ex = new ApiException(UNKNOWN, msg);
            return ex;
        } else {
            //未知错误
            ex = new ApiException(UNKNOWN, e.getMessage());
            return ex;
        }
    }
}
