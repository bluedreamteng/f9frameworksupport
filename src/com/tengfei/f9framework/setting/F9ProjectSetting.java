package com.tengfei.f9framework.setting;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.tengfei.f9framework.util.StringUtil;
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

    public static F9ProjectSetting getInstance(Project project) {
        F9ProjectSetting f9ProjectSetting = ServiceManager.getService(project, F9ProjectSetting.class);
        boolean valid = f9ProjectSetting.checkValid();
        if(valid) {
            return f9ProjectSetting;
        } else {
            throw new RuntimeException("the project setting is not valid,please check your settings");
        }

    }


    public List<F9StandardModule> standardModules = new ArrayList<>();


    @Nullable
    @Override
    public F9ProjectSetting getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull F9ProjectSetting state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public boolean checkValid() {
        for (F9StandardModule standardModule : standardModules) {
            if (StringUtil.isBlank(standardModule.name) || StringUtil.isBlank(standardModule.deployHost)) {
                return false;
            }
            for (F9CustomizeModule customizeModule : standardModule.customizeModuleList) {
                if (StringUtil.isBlank(customizeModule.webRoot)
                        || StringUtil.isBlank(customizeModule.name)
                        || StringUtil.isBlank(customizeModule.customizeProjectPath)) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<F9CustomizeModule> findCustomizeListOfStandardModule(String standardModuleName) {
        for (F9StandardModule standardModule : standardModules) {
            if(standardModule.getName().equals(standardModuleName)) {
                return standardModule.customizeModuleList;
            }
        }
        return new ArrayList<>();
    }

}
