package com.fwkj.fw_root_library.component;

import com.fwkj.fw_root_library.BaseModel;
import com.fwkj.fw_root_library.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = DelegateComponent.class)
public interface BaseModelComponent {
    void inject(BaseModel model);
}
