package com.tengfei.f9framework.module.setting;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Supports storing the application settings in a persistent way.
 * The {@link State} and {@link Storage} annotations define the name of the data and the file name where
 * these persistent application settings are stored.
 *
 * @author ztf
 */
@State(
        name = "com.tengfei.f9framework.setting.F9SettingsState",
        storages = {@Storage("f9framework.xml")}
)
public class F9SettingsState implements PersistentStateComponent<F9SettingsState> {

    public boolean enableChromeSupport = false;

    public static F9SettingsState getInstance(Project project) {
        return ServiceManager.getService(project, F9SettingsState.class);
    }

    @Nullable
    @Override
    public F9SettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull F9SettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
