package com.tengfei.f9framework.projectsetting;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * Provides controller functionality for project settings.
 *
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
        F9ProjectSettingsState projectSettingsState = F9ProjectSettingsState.getInstance(project);
        F9ApplicationSettingState applicationSettingState = F9ApplicationSettingState.getInstance();
        return (f9SettingsComponent.getEnableF9ChromeSupport() != projectSettingsState.enableChromeSupport) ||
                !f9SettingsComponent.getHtmlAttributeDictionaryPath().equals(applicationSettingState.htmlAttributeDictionaryPath) ||
                !f9SettingsComponent.getHtmlPostfixLibraryPath().equals(applicationSettingState.htmlPostfixLibraryPath) ||
                !f9SettingsComponent.getJavaPostfixLibraryPath().equals(applicationSettingState.javaPostfixLibraryPath);
    }

    @Override
    public void apply() {
        F9ProjectSettingsState projectSettings = F9ProjectSettingsState.getInstance(project);
        projectSettings.enableChromeSupport = f9SettingsComponent.getEnableF9ChromeSupport();
        F9ApplicationSettingState applicationSettingState = F9ApplicationSettingState.getInstance();
        applicationSettingState.htmlAttributeDictionaryPath = f9SettingsComponent.getHtmlAttributeDictionaryPath();
        applicationSettingState.htmlPostfixLibraryPath = f9SettingsComponent.getHtmlPostfixLibraryPath();
        applicationSettingState.javaPostfixLibraryPath = f9SettingsComponent.getJavaPostfixLibraryPath();
    }

    @Override
    public void reset() {
        F9ProjectSettingsState settings = F9ProjectSettingsState.getInstance(project);
        F9ApplicationSettingState applicationSettingState = F9ApplicationSettingState.getInstance();
        f9SettingsComponent.setEnableF9ChromeSupport(settings.enableChromeSupport);
        f9SettingsComponent.setHtmlPostfixLibraryPath(applicationSettingState.htmlPostfixLibraryPath);
        f9SettingsComponent.setJavaPostfixLibraryPath(applicationSettingState.javaPostfixLibraryPath);
        f9SettingsComponent.setHtmlAttributeDictionaryPath(applicationSettingState.htmlAttributeDictionaryPath);

    }

    @Override
    public void disposeUIResources() {
        f9SettingsComponent = null;
    }

}
