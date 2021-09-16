package com.tengfei.f9framework.settings.modulesetting;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ztf
 */
@State(
        name = "com.tengfei.f9framework.setting.F9ProjectSetting",
        storages = {@Storage("f9projectSetting.xml")}
)
public class F9ProjectSetting implements PersistentStateComponent<F9ProjectSetting> {
    public Set<F9StandardModuleSetting> standardModules = new HashSet<>();


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

    /**
     * 增加标版模块设置
     * @param stdModuleSetting 标版模块设置
     * @return true成功 false 失败
     */
    public boolean addStdModuleSetting(@NotNull F9StandardModuleSetting stdModuleSetting) {
        if(standardModules.contains(stdModuleSetting)) {
            return false;
        }
        standardModules.add(stdModuleSetting);
        return true;
    }

    public boolean addCusModuleSetting(@NotNull F9StandardModuleSetting parent,@NotNull F9CustomizeModuleSetting cusModuleSetting){
        if(!standardModules.contains(parent)) {
            return false;
        }
        if(parent.getCustomizeModuleList().contains(cusModuleSetting)) {
            return false;
        }
        parent.getCustomizeModuleList().add(cusModuleSetting);
        return true;
    }

    public boolean removeStdModuleSetting(@NotNull F9StandardModuleSetting stdModuleSetting) {
        return standardModules.remove(stdModuleSetting);
    }

    public boolean removeCusModuleSetting(@NotNull F9StandardModuleSetting parent,@NotNull F9CustomizeModuleSetting cusModuleSetting) {
        if(!standardModules.contains(parent)) {
            return false;
        }
        return parent.getCustomizeModuleList().remove(cusModuleSetting);
    }



}
