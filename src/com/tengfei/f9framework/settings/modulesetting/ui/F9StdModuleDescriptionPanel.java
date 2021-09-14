package com.tengfei.f9framework.settings.modulesetting.ui;

import com.intellij.util.ui.FormBuilder;
import com.tengfei.f9framework.settings.modulesetting.F9StandardModuleSetting;

import javax.swing.*;

/**
 * @author ztf
 */
public class F9StdModuleDescriptionPanel {
    private final JPanel myPanel;

    public JTextField nameTextFiled = new JTextField();

    public JTextField deployHostTextField =new JTextField();

    public JTextField productCustomizeNameTextField =new JTextField();


    public F9StdModuleDescriptionPanel() {
        myPanel = FormBuilder.createFormBuilder().addLabeledComponent("模块名称:",nameTextFiled)
                .addLabeledComponent("部署端口:",deployHostTextField)
                .addLabeledComponent("产品个性化目录:",productCustomizeNameTextField)
                .getPanel();
    }

    public JPanel getPanel() {
        return myPanel;
    }

    public void setStdModuleSetting(F9StandardModuleSetting stdModuleSetting) {
        reset();
        nameTextFiled.setText(stdModuleSetting.getName());
        deployHostTextField.setText(stdModuleSetting.getDeployHost());
        productCustomizeNameTextField.setText(stdModuleSetting.getProductCustomizeName());
    }

    public void reset() {
        nameTextFiled.setText(null);
        deployHostTextField.setText(null);
        productCustomizeNameTextField.setText(null);
    }
}
