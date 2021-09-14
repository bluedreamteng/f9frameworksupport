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

    public F9CusModuleDescriptionPanel() {
        myPanel = FormBuilder.createFormBuilder().addLabeledComponent("模块名称",nameTextField)
                .addLabeledComponent("产品个性化目录",customizeProjectPathTextFiled)
                .getPanel();
    }

    public JPanel getPanel() {
        return myPanel;
    }

    public void setCusModuleSetting(@NotNull F9CustomizeModuleSetting cusModuleSetting) {
        nameTextField.setText(cusModuleSetting.getName());
        customizeProjectPathTextFiled.setText(cusModuleSetting.getCustomizeProjectPath());
    }

    public void reset() {
        nameTextField.setText(null);
        customizeProjectPathTextFiled.setText(null);
    }
}
