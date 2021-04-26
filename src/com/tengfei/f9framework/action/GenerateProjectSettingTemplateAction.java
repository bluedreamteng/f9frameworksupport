package com.tengfei.f9framework.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.tengfei.f9framework.notification.F9Notifier;
import com.tengfei.f9framework.setting.F9CustomizeModule;
import com.tengfei.f9framework.setting.F9ProjectSetting;
import com.tengfei.f9framework.setting.F9StandardModule;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class GenerateProjectSettingTemplateAction extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = getEventProject(e);
        F9ProjectSetting projectSetting = F9ProjectSetting.getInstance(project);
        if (projectSetting.standardModules.size() != 0) {
            F9Notifier.notifyError(project, "文件模板已经存在");
            return;
        }
        //生成配置文件模板
        F9StandardModule standardModule = new F9StandardModule();
        standardModule.name = "smart-site";
        standardModule.deployHost = "localhost:8011";

        F9CustomizeModule customizeModule = new F9CustomizeModule();
        customizeModule.name = "szjs-custom-sfzhgd";
        customizeModule.customizeProjectPath = "szjs_sfzhgd";
        customizeModule.webRoot = "gl/szjs_sfzhgd";
        standardModule.customizeModuleList.add(customizeModule);
        projectSetting.standardModules.add(standardModule);

        F9Notifier.notifyMessage(project, "文件模板生成完成");

    }
}
