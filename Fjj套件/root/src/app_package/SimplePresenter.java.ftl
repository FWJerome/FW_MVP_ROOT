package ${packageName}.presenter;
import ${packageName}.contract. ${activityClass}Contract;
import ${packageName}.model. ${activityClass}Model;
import javax.inject.Inject;
public class ${activityClass}Presenter{
    private ${activityClass}Contract.view view;
    private ${activityClass}Model model;

    public ${activityClass}Presenter(${activityClass}Contract.view view,${activityClass}Model model) {
        this.view = view;
	this.model = model;
    }
}
