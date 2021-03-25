package com.tengfei.f9framework.setting;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 *
 * @author ztf
 */
public class F9SettingsComponent {

    private final JPanel myMainPanel;
    private final JBTextField webRootPathText = new JBTextField();
    private final JBTextField customModuleNameText = new JBTextField();
    private final JBTextField customizeProjectNameText = new JBTextField();
    private final JBTextField glProjectNameText = new JBTextField();
    private final JBTextField qyProjectNameText = new JBTextField();
    private final JBTextField qyDeployHostText = new JBTextField();
    private final JBTextField glDeployHostText = new JBTextField();
    private final JBTextField glProjectPagePathText = new JBTextField();
    private final JBTextField qyProjectPagePathText = new JBTextField();
    private final JBCheckBox enableF9ChromeSupport = new JBCheckBox("Enable F9 Chrome Support");

    public F9SettingsComponent() {
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Web Root Path: "), webRootPathText, 1, false)
                .addLabeledComponent(new JBLabel("Custom Module Name: "), customModuleNameText, 1, false)
                .addLabeledComponent(new JBLabel("Customize Project Name: "), customizeProjectNameText, 1, false)
                .addLabeledComponent(new JBLabel("Gl Project Name: "), glProjectNameText, 1, false)
                .addLabeledComponent(new JBLabel("Qy Project Name: "), qyProjectNameText, 1, false)
                .addLabeledComponent(new JBLabel("Gl Deploy Host: "), glDeployHostText, 1, false)
                .addLabeledComponent(new JBLabel("Qy Deploy Host: "), qyDeployHostText, 1, false)
                .addLabeledComponent(new JBLabel("Qy Project Page Path: "), glProjectPagePathText, 1, false)
                .addLabeledComponent(new JBLabel("Qy Project Page Path: "), qyProjectPagePathText, 1, false)
                .addComponent(enableF9ChromeSupport, 1)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return webRootPathText;
    }

    public String getWebRootPathText() {
        return webRootPathText.getText();
    }

    public void setWebRootPathText(String text) {
        webRootPathText.setText(text);
    }

    public String getCustomModuleNameText() {
        return customModuleNameText.getText();
    }

    public void setCustomModuleNameText(String text) {
        this.customModuleNameText.setText(text);
    }

    public String getCustomizeProjectNameText() {
        return customizeProjectNameText.getText();
    }

    public void setCustomizeProjectNameText(String text) {
        this.customizeProjectNameText.setText(text);
    }

    public String getGlProjectNameText() {
        return glProjectNameText.getText();
    }

    public void setGlProjectNameText(String text) {
        this.glProjectNameText.setText(text);
    }

    public String getQyProjectNameText() {
        return qyProjectNameText.getText();
    }

    public void setQyProjectNameText(String text) {
        this.qyProjectNameText.setText(text);
    }

    public String getQyDeployHostText() {
        return qyDeployHostText.getText();
    }

    public void setQyDeployHostText(String text) {
        this.qyDeployHostText.setText(text);
    }

    public String getGlDeployHostText() {
        return glDeployHostText.getText();
    }

    public void setGlDeployHostText(String text) {
        this.glDeployHostText.setText(text);
    }

    public String getGlProjectPagePathText() {
        return glProjectPagePathText.getText();
    }

    public void setGlProjectPagePathText(String text) {
        this.glProjectPagePathText.setText(text);
    }

    public String getQyProjectPagePathText() {
        return qyProjectPagePathText.getText();
    }

    public void setQyProjectPagePathText(String text) {
        this.qyProjectPagePathText.setText(text);
    }

    public boolean getEnableF9ChromeSupport() {
        return enableF9ChromeSupport.isSelected();
    }

    public void setEnableF9ChromeSupport(boolean enable) {
        this.enableF9ChromeSupport.setSelected(enable);
    }
}
