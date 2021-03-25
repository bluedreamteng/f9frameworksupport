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
        boolean modified = !f9SettingsComponent.getWebRootPathText().equals(settings.webRootPath);
        modified |= !f9SettingsComponent.getCustomizeProjectNameText().equals(settings.customizeProjectName);
        modified |= !f9SettingsComponent.getCustomModuleNameText().equals(settings.customModuleName);
        modified |= !f9SettingsComponent.getGlProjectNameText().equals(settings.glProjectName);
        modified |= !f9SettingsComponent.getQyProjectNameText().equals(settings.qyProjectName);
        modified |= !f9SettingsComponent.getQyDeployHostText().equals(settings.qyDeployHost);
        modified |= !f9SettingsComponent.getGlDeployHostText().equals(settings.glDeployHost);
        modified |= !f9SettingsComponent.getQyProjectPagePathText().equals(settings.qyProjectPagePath);
        modified |= !f9SettingsComponent.getGlProjectPagePathText().equals(settings.glProjectPagePath);
        modified |= f9SettingsComponent.getEnableF9ChromeSupport() != settings.enableChromeSupport;

        return modified;
    }

    @Override
    public void apply() {
        F9SettingsState settings = F9SettingsState.getInstance(project);
        settings.webRootPath = f9SettingsComponent.getWebRootPathText();
        settings.customizeProjectName = f9SettingsComponent.getCustomizeProjectNameText();
        settings.customModuleName = f9SettingsComponent.getCustomModuleNameText();
        settings.glProjectName = f9SettingsComponent.getGlProjectNameText();
        settings.qyProjectName = f9SettingsComponent.getQyProjectNameText();
        settings.qyDeployHost = f9SettingsComponent.getQyDeployHostText();
        settings.glDeployHost = f9SettingsComponent.getGlDeployHostText();
        settings.qyProjectPagePath = f9SettingsComponent.getQyProjectPagePathText();
        settings.glProjectPagePath = f9SettingsComponent.getGlProjectPagePathText();
        settings.enableChromeSupport = f9SettingsComponent.getEnableF9ChromeSupport();

    }

    @Override
    public void reset() {
        F9SettingsState settings = F9SettingsState.getInstance(project);
        f9SettingsComponent.setWebRootPathText(settings.webRootPath);
        f9SettingsComponent.setCustomizeProjectNameText(settings.customizeProjectName);
        f9SettingsComponent.setCustomModuleNameText(settings.customModuleName);
        f9SettingsComponent.setGlProjectNameText(settings.glProjectName);
        f9SettingsComponent.setQyProjectNameText(settings.qyProjectName);
        f9SettingsComponent.setQyDeployHostText(settings.qyDeployHost);
        f9SettingsComponent.setGlDeployHostText(settings.glDeployHost);
        f9SettingsComponent.setQyProjectPagePathText(settings.qyProjectPagePath);
        f9SettingsComponent.setGlProjectPagePathText(settings.glProjectPagePath);
        f9SettingsComponent.setEnableF9ChromeSupport(settings.enableChromeSupport);
    }

    @Override
    public void disposeUIResources() {
        f9SettingsComponent = null;
    }

}
