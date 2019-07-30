package ${packageName}.component;

import dagger.Module;
import dagger.Provides;
import ${packageName}.contract.${activityClass}Contract;
import ${packageName}.presenter.${activityClass}Presenter;
import ${packageName}.model.${activityClass}Model;
/**
*Created by 付俊杰
*email:1025461682@qq.com
*phone:17684220995
*/
@Module
public class ${activityClass}Module {
    private ${activityClass}Contract.view mView;

    public ${activityClass}Module(${activityClass}Contract.view pView) {
        mView = pView;
    }

    @Provides
    public ${activityClass}Presenter getP() {
        return new ${activityClass}Presenter(mView,new ${activityClass}Model());
    }
}
