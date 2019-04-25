<?xml version="1.0"?>
<recipe>
 <instantiate from="root/res/layout/simple.xml.ftl"
                 to="${escapeXmlAttribute(resOut)}/layout/activity_${extractLetters(activityClass?lower_case)}.xml" />

 <instantiate from="root/src/app_package/SimpleActivity.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/view/${activityClass}.java" />
    <open file="${escapeXmlAttribute(srcOut)}/view/${activityClass}.java" />

	<instantiate from="root/src/app_package/SimpleContract.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/contract/${activityClass}Contract.java" />
	<instantiate from="root/src/app_package/SimplePresenter.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/presenter/${activityClass}Presenter.java" />
	<instantiate from="root/src/app_package/SimpleModel.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/model/${activityClass}Model.java" />
	<instantiate from="root/src/app_package/SimpleComponent.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/component/${activityClass}Component.java" />
	<instantiate from="root/src/app_package/SimpleModule.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/component/${activityClass}Module.java" />
</recipe>
