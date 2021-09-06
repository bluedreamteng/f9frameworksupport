package com.tengfei.f9framework.settings.normalsetting;

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
        name = "com.tengfei.f9framework.projectsetting.F9ProjectSettingsState",
        storages = {@Storage("f9framework_project.xml")}
)
public class F9ProjectSettingsState implements PersistentStateComponent<F9ProjectSettingsState> {

    public boolean enableChromeSupport = false;

    public static F9ProjectSettingsState getInstance(Project project) {
        return ServiceManager.getService(project, F9ProjectSettingsState.class);
    }

    @Nullable
    @Override
    public F9ProjectSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull F9ProjectSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
