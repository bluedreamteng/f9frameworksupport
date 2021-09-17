package com.tengfei.f9framework.settings.modulesetting.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.util.ui.FormBuilder;
import com.tengfei.f9framework.settings.modulesetting.F9StandardModuleSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.internal.StringUtil;

import javax.swing.*;

/**
 * @author ztf
 */
public class F9StdModuleEditFormDialog extends DialogWrapper {
    private final Project project;
    private final JPanel myPanel;
    private JTextField moduleNameFiled = new JTextField();
    private final JTextField deployHostField = new JTextField();
    private final JTextField productCustomizeNameField = new JTextField();

    private F9StandardModuleSetting standardModuleSetting;

    public F9StdModuleEditFormDialog(@NotNull Project project, @NotNull String title, @NotNull F9StandardModuleSetting standardModuleSetting) {
        super(project);
        this.project = project;
        this.standardModuleSetting = standardModuleSetting;
        moduleNameFiled.setText(standardModuleSetting.getName());
        deployHostField.setText(standardModuleSetting.getDeployHost());
        productCustomizeNameField.setText(standardModuleSetting.getProductCustomizeName());
        moduleNameFiled.setEditable(false);
        myPanel = FormBuilder.createFormBuilder().addLabeledComponent("模块名称:", moduleNameFiled)
                .addLabeledComponent("部署端口:",deployHostField)
                .addLabeledComponent("产品个性化目录:",productCustomizeNameField)
                .getPanel();
        setTitle(title);
        init();
        setSize(660, 500);

    }


    /**
     * Validates user input and returns {@code null} if everything is fine
     * or validation description with component where problem has been found.
     *
     * @return {@code null} if everything is OK or validation descriptor
     * @see <a href="https://jetbrains.design/intellij/principles/validation_errors/">Validation errors guidelines</a>
     */
    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if(StringUtil.isBlank(moduleNameFiled.getText())) {
            return new ValidationInfo("模块名不能为空",moduleNameFiled);
        }

        if(StringUtil.isBlank(deployHostField.getText())) {
            return new ValidationInfo("部署端口不能为空",deployHostField);
        }

        return super.doValidate();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return myPanel;
    }

    public void open() {
        show();
    }

    @Override
    protected void doOKAction() {
        String deployHost = deployHostField.getText();
        String productCusName = productCustomizeNameField.getText();
        standardModuleSetting.setDeployHost(deployHost);
        standardModuleSetting.setProductCustomizeName(productCusName);
        super.doOKAction();
    }


    @Override
    public boolean isAutoAdjustable() {
        return false;
    }
}
