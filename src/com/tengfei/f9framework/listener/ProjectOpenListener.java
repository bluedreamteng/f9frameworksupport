package com.tengfei.f9framework.listener;

import com.intellij.ide.startup.ProjectLoadListener;
import com.intellij.openapi.project.Project;
import com.tengfei.f9framework.notification.F9Notifier;
import com.tengfei.f9framework.settings.modulesetting.F9CustomizeModuleSetting;
import com.tengfei.f9framework.settings.modulesetting.F9ProjectSetting;
import com.tengfei.f9framework.settings.modulesetting.F9StandardModuleSetting;

public class ProjectOpenListener implements ProjectLoadListener {
    private Project project;
    public ProjectOpenListener(Project project) {
        this.project = project;
    }

    @Override
    public void postStartUpActivitiesPassed() {
        F9ProjectSetting projectSetting = F9ProjectSetting.getInstance(project);
        if (projectSetting.standardModules.size() != 0) {
            F9Notifier.notifyMessage(project, "已检测到f9模块配置文件");
            return;
        }
        //生成配置文件模板
        F9StandardModuleSetting standardModule = new F9StandardModuleSetting();
        standardModule.name = "smart-site";
        standardModule.deployHost = "localhost:8011";
        standardModule.productCustomizeName = "zhgd";

        F9CustomizeModuleSetting customizeModule = new F9CustomizeModuleSetting();
        customizeModule.name = "szjs-custom-sfzhgd";
        customizeModule.customizeProjectPath = "szjs_sfzhgd";
        standardModule.customizeModuleList.add(customizeModule);
        projectSetting.standardModules.add(standardModule);

        F9Notifier.notifyMessage(project, "f9文件配置模板已生成");
    }
}
