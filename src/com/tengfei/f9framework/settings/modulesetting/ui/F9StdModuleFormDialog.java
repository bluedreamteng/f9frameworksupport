package com.tengfei.f9framework.settings.modulesetting.ui;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ui.configuration.ChooseModulesDialog;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.EmptyRunnable;
import com.intellij.ui.FieldPanel;
import com.intellij.util.ui.FormBuilder;
import com.tengfei.f9framework.settings.modulesetting.F9StandardModuleSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author ztf
 */
public class F9StdModuleFormDialog extends DialogWrapper {
    private JPanel myPanel;

    private FieldPanel moduleNameFiled;
    private JTextField deployHost;
    private JTextField productCustomizeName;

    public F9StdModuleFormDialog(@NotNull Project project, @NotNull String title) {
        super(project);
        moduleNameFiled =  new FieldPanel(null, null, (actionEvent) -> {
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
        myPanel = FormBuilder.createFormBuilder().addLabeledComponent("模块名称:",moduleNameFiled).getPanel();
        setTitle(title);
        init();
        setSize(460,330);
    }

    /**
     * Factory method. It creates panel with dialog options. Options panel is located at the
     * center of the dialog's content pane. The implementation can return {@code null}
     * value. In this case there will be no options panel.
     */
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
        super.doOKAction();
    }

    @Override
    public boolean isAutoAdjustable() {
        return false;
    }
}
