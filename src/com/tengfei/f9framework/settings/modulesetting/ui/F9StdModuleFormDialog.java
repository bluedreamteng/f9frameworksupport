package com.tengfei.f9framework.settings.modulesetting.ui;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ui.configuration.ChooseModulesDialog;
import com.intellij.openapi.ui.ComponentValidator;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.EmptyRunnable;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.FieldPanel;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.internal.StringUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.util.Arrays;
import java.util.List;

/**
 * @author ztf
 */
public class F9StdModuleFormDialog extends DialogWrapper {
    private Project project;
    private JPanel myPanel;
    private FieldPanel moduleNameFiled;
    private JTextField deployHost;
    private JTextField productCustomizeName;

    public F9StdModuleFormDialog(@NotNull Project project, @NotNull String title) {
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
//        moduleNameFiled.setEditable(false);
        deployHost = new JTextField();
        myPanel = FormBuilder.createFormBuilder().addLabeledComponent("模块名称:", moduleNameFiled).addComponent(deployHost).getPanel();
        setTitle(title);
        init();
        setSize(460, 330);
        initValidate();
    }


    private void initValidate() {
        new ComponentValidator(project).withValidator(v -> {
            String pt = moduleNameFiled.getText();
            if(StringUtil.isBlank(pt)) {
                v.updateInfo(new ValidationInfo("hello",moduleNameFiled));
            } else {
                v.updateInfo(null);
            }
        }).installOn(moduleNameFiled);

        moduleNameFiled.getTextField().getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                ComponentValidator.getInstance(moduleNameFiled).ifPresent(v -> v.revalidate());
            }
        });
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
        super.doOKAction();


    }

    @Override
    public boolean isAutoAdjustable() {
        return false;
    }
}
