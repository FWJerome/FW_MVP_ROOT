package com.fwkj.fw_root_library.delegate;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.blankj.utilcode.util.ObjectUtils;

import java.util.List;

import javax.inject.Inject;

public class AVNetDelegate {
    @Inject
    public AVNetDelegate(Application application, String avId, String avKey, List<Class<? extends AVObject>> avObjects) {
        if (!ObjectUtils.isEmpty(avId) && !ObjectUtils.isEmpty(avKey)) {
            if (avObjects != null) {
                for (Class avObject : avObjects) {
                    AVObject.registerSubclass(avObject);
                }
            }
            AVOSCloud.initialize(application, avId, avKey);
        }
    }
}
