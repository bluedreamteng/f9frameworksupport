package com.tengfei.f9framework.settings.modulesetting.ui;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ui.configuration.ChooseModulesDialog;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.EmptyRunnable;
import com.intellij.ui.FieldPanel;
import com.intellij.util.ui.FormBuilder;
import com.tengfei.f9framework.settings.modulesetting.F9CustomizeModuleSetting;
import com.tengfei.f9framework.settings.modulesetting.F9ProjectSetting;
import com.tengfei.f9framework.settings.modulesetting.F9StandardModuleSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.internal.StringUtil;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class F9CusModuleAddFormDialog extends DialogWrapper {
    private final Project project;
    private final JPanel myPanel;
    private final F9StandardModuleSetting standardModuleSetting;
    private FieldPanel moduleNameFiled;
    private final JTextField standardModuleNameField = new JTextField();
    private final JTextField customizeNameField = new JTextField();

    public F9CusModuleAddFormDialog(@NotNull Project project, @NotNull String title, @NotNull F9StandardModuleSetting standardModuleSetting) {
        super(project);
        this.project = project;
        this.standardModuleSetting = standardModuleSetting;
        moduleNameFiled = new FieldPanel(null, null, (actionEvent) -> {
            ChooseModulesDialog chooseModulesDialog = new ChooseModulesDialog(project, Arrays.asList(ModuleManager.getInstance(project).getModules()), "Choose Module", null);
            chooseModulesDialog.setSingleSelectionMode();
            chooseModulesDialog.show();
            List<Module> chosenElements = chooseModulesDialog.getChosenElements();
            if (!chosenElements.isEmpty()) {
                Module module = chosenElements.get(0);
                moduleNameFiled.setText(module.getName());
            }
        }, EmptyRunnable.getInstance());
        moduleNameFiled.setEditable(false);
        standardModuleNameField.setText(standardModuleSetting.getName());
        standardModuleNameField.setEditable(false);
        myPanel = FormBuilder.createFormBuilder().addLabeledComponent("模块名称:", moduleNameFiled)
                .addLabeledComponent("标版模块:", standardModuleNameField)
                .addLabeledComponent("个性化目录:", customizeNameField)
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
            return new ValidationInfo("模块名不能为空",moduleNameFiled.getTextField());
        }

        if(StringUtil.isBlank(customizeNameField.getText())) {
            return new ValidationInfo("个性化目录不能为空", customizeNameField);
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
        String moduleName = moduleNameFiled.getText();
        String standardModuleName = standardModuleNameField.getText();
        String customizeName = customizeNameField.getText();
        F9CustomizeModuleSetting f9CustomizeModuleSetting = new F9CustomizeModuleSetting();
        f9CustomizeModuleSetting.setName(moduleName);
        f9CustomizeModuleSetting.setStandardName(standardModuleName);
        f9CustomizeModuleSetting.setCustomizeProjectPath(customizeName);
        F9ProjectSetting.getInstance(project).addCusModuleSetting(standardModuleSetting,f9CustomizeModuleSetting);
        super.doOKAction();
    }

    @Override
    public boolean isAutoAdjustable() {
        return false;
    }
}
