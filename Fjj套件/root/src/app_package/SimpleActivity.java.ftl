package ${packageName}.view;

import com.fwkj.fw_root_library.BaseActivity;
import com.fwkj.fw_root_library.component.DelegateComponent;

import javax.inject.Inject;

import ${packageName}.R;
import ${packageName}.component.Dagger${activityClass}Component;
import ${packageName}.component.${activityClass}Module;
import ${packageName}.contract.${activityClass}Contract;
import ${packageName}.presenter.${activityClass}Presenter;

public class ${activityClass} extends BaseActivity implements ${activityClass}Contract.view {
    @Inject
    ${activityClass}Presenter m${activityClass}Presenter;
    
    @Override
    public void initView(Bundle savedInstanceState) {
        
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initComponent(DelegateComponent component) {
	Dagger${activityClass}Component.builder()
			.delegateComponent(component)
			.${extractLetters(activityClass?uncap_first)}Module(new ${activityClass}Module(this))
			.build()
			.inject(this);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_${extractLetters(activityClass?lower_case)};
    }
}
