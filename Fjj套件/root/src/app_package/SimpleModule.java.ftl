package ${packageName}.component;

import dagger.Module;
import dagger.Provides;
import ${packageName}.contract.${activityClass}Contract;
import ${packageName}.presenter.${activityClass}Presenter;
import ${packageName}.model.${activityClass}Model;

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
