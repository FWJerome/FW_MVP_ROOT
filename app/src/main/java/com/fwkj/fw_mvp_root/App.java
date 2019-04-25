package com.fwkj.fw_mvp_root;

import com.fwkj.fw_root_library.Root_App;
import com.fwkj.fw_root_library.inter.IGlobalConfig;

public class App extends Root_App {
    @Override
    protected IGlobalConfig getConfig() {
        return new Global();
    }
}
