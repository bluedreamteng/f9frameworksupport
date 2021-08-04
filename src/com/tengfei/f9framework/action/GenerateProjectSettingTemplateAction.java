package com.tengfei.f9framework.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.tengfei.f9framework.notification.F9Notifier;
import com.tengfei.f9framework.module.setting.F9CustomizeModuleSetting;
import com.tengfei.f9framework.module.setting.F9ProjectSetting;
import com.tengfei.f9framework.module.setting.F9StandardModuleSetting;
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
        F9StandardModuleSetting standardModule = new F9StandardModuleSetting();
        standardModule.name = "smart-site";
        standardModule.deployHost = "localhost:8011";
        standardModule.productCustomizeName = "zhgd";

        F9CustomizeModuleSetting customizeModule = new F9CustomizeModuleSetting();
        customizeModule.name = "szjs-custom-sfzhgd";
        customizeModule.customizeProjectPath = "szjs_sfzhgd";
        standardModule.customizeModuleList.add(customizeModule);
        projectSetting.standardModules.add(standardModule);

        F9Notifier.notifyMessage(project, "文件模板生成完成");

    }
}
