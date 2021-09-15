package com.tengfei.f9framework.settings.modulesetting.ui;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ui.configuration.ChooseModulesDialog;
import com.intellij.openapi.util.EmptyRunnable;
import com.intellij.ui.FieldPanel;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author ztf
 */
public class F9StdModuleFormPanel extends JDialog{
    private JPanel myPanel;

    private FieldPanel moduleNameFiled;
    private JTextField deployHost;
    private JTextField productCustomizeName;

    public F9StdModuleFormPanel(Project project) {
        moduleNameFiled =  new FieldPanel("模块名称", "Choose Module", (actionEvent) -> {
            ChooseModulesDialog chooseModulesDialog = new ChooseModulesDialog(project, Arrays.asList(ModuleManager.getInstance(project).getModules()), "choose  module", null);
            chooseModulesDialog.setSingleSelectionMode();
            chooseModulesDialog.show();
            List<Module> chosenElements = chooseModulesDialog.getChosenElements();
            if (!chosenElements.isEmpty()) {
                Module module = chosenElements.get(0);
                moduleNameFiled.setText(module.getName());
            }
        }, EmptyRunnable.getInstance());
        myPanel = FormBuilder.createFormBuilder().addComponent(moduleNameFiled).getPanel();
    }


    public JPanel getPanel() {
        return myPanel;
    }
}
