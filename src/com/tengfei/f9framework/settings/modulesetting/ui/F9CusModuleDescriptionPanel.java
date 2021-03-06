package com.tengfei.f9framework.settings.modulesetting.ui;

import com.intellij.util.ui.FormBuilder;
import com.tengfei.f9framework.settings.modulesetting.F9CustomizeModuleSetting;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author ztf
 */
public class F9CusModuleDescriptionPanel {
    private JPanel myPanel;

    public JTextField nameTextField = new JTextField();
    public JTextField customizeProjectPathTextFiled = new JTextField();
    public JTextField standardModuleTextFiled = new JTextField();

    public F9CusModuleDescriptionPanel() {
        nameTextField.setEnabled(false);
        customizeProjectPathTextFiled.setEnabled(false);
        standardModuleTextFiled.setEnabled(false);
        myPanel = FormBuilder.createFormBuilder().addLabeledComponent("模块名称:",nameTextField)
                .addLabeledComponent("标版目录:",standardModuleTextFiled)
                .addLabeledComponent("个性化目录:",customizeProjectPathTextFiled)
                .getPanel();
    }

    public JPanel getPanel() {
        return myPanel;
    }

    public void setCusModuleSetting(@NotNull F9CustomizeModuleSetting cusModuleSetting) {
        nameTextField.setText(cusModuleSetting.getName());
        customizeProjectPathTextFiled.setText(cusModuleSetting.getCustomizeProjectPath());
        standardModuleTextFiled.setText(cusModuleSetting.getStandardName());
    }

    public void reset() {
        nameTextField.setText(null);
        customizeProjectPathTextFiled.setText(null);
    }
}
