package com.tengfei.f9framework.setting;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Provides controller functionality for project settings.
 * @author ztf
 */
public class F9SettingsConfigurable implements Configurable {

    private F9SettingsComponent f9SettingsComponent;
    private final Project project;


    public F9SettingsConfigurable(Project project) {
        this.project = project;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "F9 Framework Support";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return f9SettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        f9SettingsComponent = new F9SettingsComponent();
        return f9SettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        F9SettingsState settings = F9SettingsState.getInstance(project);
        return f9SettingsComponent.getEnableF9ChromeSupport() != settings.enableChromeSupport;
    }

    @Override
    public void apply() {
        F9SettingsState settings = F9SettingsState.getInstance(project);
        settings.enableChromeSupport = f9SettingsComponent.getEnableF9ChromeSupport();

    }

    @Override
    public void reset() {
        F9SettingsState settings = F9SettingsState.getInstance(project);
        f9SettingsComponent.setEnableF9ChromeSupport(settings.enableChromeSupport);
    }

    @Override
    public void disposeUIResources() {
        f9SettingsComponent = null;
    }

}
