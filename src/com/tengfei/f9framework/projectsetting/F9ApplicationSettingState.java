package com.tengfei.f9framework.projectsetting;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
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
        name = "com.tengfei.f9framework.projectsetting.F9ApplicationSettingState",
        storages = {@Storage("f9framework_application.xml")}
)
public class F9ApplicationSettingState implements PersistentStateComponent<F9ApplicationSettingState> {

    public String htmlPostfixLibraryPath = "";
    public String javaPostfixLibraryPath = "";
    public String htmlAttributeDictionaryPath = "";

    public static F9ApplicationSettingState getInstance() {
        return ServiceManager.getService(F9ApplicationSettingState.class);
    }

    @Nullable
    @Override
    public F9ApplicationSettingState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull F9ApplicationSettingState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
