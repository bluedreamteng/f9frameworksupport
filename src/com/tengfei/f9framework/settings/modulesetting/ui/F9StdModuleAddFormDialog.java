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
import com.tengfei.f9framework.settings.modulesetting.F9ProjectSetting;
import com.tengfei.f9framework.settings.modulesetting.F9StandardModuleSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.internal.StringUtil;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author ztf
 */
public class F9StdModuleAddFormDialog extends DialogWrapper {
    private final Project project;
    private final JPanel myPanel;
    F9StandardModuleSetting f9StandardModuleSetting = new F9StandardModuleSetting();
    private FieldPanel moduleNameFiled;
    private final JTextField deployHostField = new JTextField();
    private final JTextField productCustomizeNameField = new JTextField();

    public F9StdModuleAddFormDialog(@NotNull Project project, @NotNull String title) {
        super(project);
        this.project = project;
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
        myPanel = FormBuilder.createFormBuilder().addLabeledComponent("????????????:", moduleNameFiled)
                .addLabeledComponent("????????????:",deployHostField)
                .addLabeledComponent("?????????????????????:",productCustomizeNameField)
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
            return new ValidationInfo("?????????????????????",moduleNameFiled.getTextField());
        }

        if(StringUtil.isBlank(deployHostField.getText())) {
            return new ValidationInfo("????????????????????????",deployHostField);
        }

        String moduleName = moduleNameFiled.getText();
        String deployHost = deployHostField.getText();
        String productCusName = productCustomizeNameField.getText();
        f9StandardModuleSetting.setName(moduleName);
        f9StandardModuleSetting.setDeployHost(deployHost);
        f9StandardModuleSetting.setProductCustomizeName(productCusName);

        if(F9ProjectSetting.getInstance(project).standardModules.contains(f9StandardModuleSetting)) {
            return new ValidationInfo("?????????????????????");
        }

        return super.doValidate();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return myPanel;
    }



    @Override
    protected void doOKAction() {
        F9ProjectSetting.getInstance(project).addStdModuleSetting(f9StandardModuleSetting);
        super.doOKAction();

    }


    @Override
    public boolean isAutoAdjustable() {
        return false;
    }
}
