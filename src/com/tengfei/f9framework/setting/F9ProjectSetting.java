package com.tengfei.f9framework.setting;

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

@State(
        name = "com.tengfei.f9framework.setting.F9ProjectSetting",
        storages = {@Storage("f9projectSetting.xml")}
)
public class F9ProjectSetting implements PersistentStateComponent<F9ProjectSetting> {

    public static F9ProjectSetting getInstance(Project project) {
        return ServiceManager.getService(project, F9ProjectSetting.class);
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

}
