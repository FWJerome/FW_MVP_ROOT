package ${packageName}.component;

import com.fwkj.fw_root_library.component.DelegateComponent;
import com.fwkj.fw_root_library.scope.ActivityScope;

import dagger.Component;
import ${packageName}.view.${activityClass};

/**
 * @Des 注射器 主要说明的是Dagger注入的流程
 * 首先V 需要 P，那么直接在 V使用Dagger注解进行声明要注入的P,但是每个P都要持有这个V，那么就需要在构造P的时候传入V，那么就避免不了要使用一个module来进行处理。V注入P，Dagger会去找到要使用的P的构造函数，找到后发现需要一个对于的构参数去构造，于是就去module里面找，找到了返回同样类型的值后就直接使用
 */
@ActivityScope
@Component(modules = ${activityClass}Module.class,dependencies = DelegateComponent.class)
public interface ${activityClass}Component {
    void inject(${activityClass} p${activityClass});
}
