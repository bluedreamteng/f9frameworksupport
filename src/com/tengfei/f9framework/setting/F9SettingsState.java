package com.tengfei.f9framework.setting;

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
 * @author ztf
 */
@State(
        name = "com.tengfei.f9framework.setting.F9SettingsState",
        storages = {@Storage("f9framework.xml")}
)
public class F9SettingsState implements PersistentStateComponent<F9SettingsState> {

    public String webRootPath = "webapp";
    public String customizeProjectName = "szjs_sfzhgd";
    public String glProjectName = "smart-site";
    public String qyProjectName = "site-brain";
    public String qyDeployHost = "localhost:8012";
    public String glDeployHost = "localhost:8011";
    public String qyProjectPagePath = "qy/szjs_sfzhgd";
    public String glProjectPagePath ="gl/szjs_sfzhgd";
    public String customModuleName = "szjs_custom_sfzhgd";
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
