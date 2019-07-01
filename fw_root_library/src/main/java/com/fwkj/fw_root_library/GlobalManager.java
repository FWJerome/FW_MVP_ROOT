package com.fwkj.fw_root_library;

import android.app.Application;

import androidx.annotation.RequiresPermission;

import com.avos.avoscloud.AVObject;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.Utils;
import com.fwkj.fw_root_library.utils.ImageLoader;

import java.util.List;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * @author 付俊杰
 * @email 1025461682@qq.com
 * @phone 17684220995
 * @file description : 全局配置管理者
 */
public class GlobalManager {
    private List<Class<? extends AVObject>> avObjects;
    private String avid;
    private String avkey;
    private boolean debugLog;
    private String url;

    public GlobalManager(Builder builder) {
        this.avid = builder.avid;
        this.avObjects = builder.avObjects;
        this.avkey = builder.avkey;
        this.debugLog = builder.debugLog;
        this.url = builder.url;
    }

    public boolean isDebugLog() {
        return debugLog;
    }

    public String getUrl() {
        return url;
    }

    public String getAvid() {
        return avid == null ? "" : avid;
    }

    public String getAvkey() {
        return avkey == null ? "" : avkey;
    }

    public List<Class<? extends AVObject>> getAvObjects() {
        return avObjects;
    }

    public static class Builder {
        String avid;
        String avkey;
        List<Class<? extends AVObject>> avObjects;
        boolean debugLog;
        String url;

        public Builder avaccount(String avid, String avkey, List<Class<? extends AVObject>> avObjects) {
            this.avid = avid;
            this.avkey = avkey;
            this.avObjects = avObjects;
            return this;
        }

        public GlobalManager build() {
            return new GlobalManager(this);
        }

        public Builder allowBlankjUtilcode(Application application) {
            Utils.init(application);
            return this;
        }

        public Builder debugLog(boolean b) {
            debugLog = b;
            return this;
        }

        public Builder baseUrl(String url) {
            this.url = url;
            return this;
        }

        public void configImageLocader(Application application) {
            ImageLoader.getInstance(application);
        }

        @RequiresPermission(WRITE_EXTERNAL_STORAGE)
        public Builder crashFile(Application application, final String crashfile) {
            CrashUtils.init(crashfile);
            return this;
        }
    }
}
