package com.tengfei.f9framework.projectsetting.f9modulesetting;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ztf
 */
@State(
        name = "com.tengfei.f9framework.setting.F9ProjectSetting",
        storages = {@Storage("f9projectSetting.xml")}
)
public class F9ProjectSetting implements PersistentStateComponent<F9ProjectSetting> {
    public List<F9StandardModuleSetting> standardModules = new ArrayList<>();


    public static F9ProjectSetting getInstance(Project project) {
        return ServiceManager.getService(project, F9ProjectSetting.class);
    }


    @Nullable
    @Override
    public F9ProjectSetting getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull F9ProjectSetting state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public List<F9StandardModuleSetting> getStandardModules() {
        if(standardModules.isEmpty()) {
            //引导用户去配置
            F9StandardModuleSetting standardModule = new F9StandardModuleSetting();
            standardModule.name = "smart-site";
            standardModule.deployHost = "localhost:8011";
            standardModule.productCustomizeName = "zhgd";

            F9CustomizeModuleSetting customizeModule = new F9CustomizeModuleSetting();
            customizeModule.name = "szjs-custom-sfzhgd";
            customizeModule.customizeProjectPath = "szjs_sfzhgd";
            standardModule.customizeModuleList.add(customizeModule);
            standardModules.add(standardModule);
        }
        return standardModules;
    }
}
