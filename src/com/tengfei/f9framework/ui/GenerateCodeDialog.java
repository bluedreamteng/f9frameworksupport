package com.tengfei.f9framework.ui;

import com.intellij.database.psi.DbTable;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ui.configuration.ChooseModulesDialog;
import com.intellij.psi.PsiPackage;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.notification.F9Notifier;
import com.tengfei.f9framework.service.TableCodeGenerateService;
import com.tengfei.f9framework.util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class GenerateCodeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField moduleText;
    private JButton chooseModuleButton;
    private JTextField packageText;
    private JButton choosePackageButton;
    private JCheckBox entity;
    private JCheckBox service;
    private JCheckBox isCreateDefaultPackage;

    private final Project project;
    private final DbTable dbTable;
    private final TableCodeGenerateService codeGenerateService;


    public GenerateCodeDialog(Project project, DbTable dbTable) {
        this.project = project;
        this.dbTable = dbTable;
        codeGenerateService = TableCodeGenerateService.getInstance(project);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Generate Code");

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        //my code
        chooseModuleButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChooseModulesDialog chooseModulesDialog = new ChooseModulesDialog(project, Arrays.asList(ModuleManager.getInstance(project).getModules()), "choose  module", null);
                chooseModulesDialog.setSingleSelectionMode();
                chooseModulesDialog.show();
                List<Module> chosenElements = chooseModulesDialog.getChosenElements();
                if (!chosenElements.isEmpty()) {
                    Module module = chosenElements.get(0);
                    moduleText.setText(module.getName());
                }

            }
        });

        choosePackageButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PackageChooserDialog packageChooserDialog = new PackageChooserDialog("choose package", project);
                packageChooserDialog.show();
                PsiPackage selectedPackage = packageChooserDialog.getSelectedPackage();
                if (selectedPackage != null) {
                    packageText.setText(selectedPackage.getQualifiedName());
                }
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
        if (StringUtil.isNotBlank(getSelectedModule()) && getSelectedPackage() != null) {
            PathConfig pathConfig = new PathConfig(getSelectedModule(), getSelectedPackage(), isCreateDefaultPackage());
            TableInfo tableInfo = new TableInfo(dbTable,pathConfig);
            if (isEntitySelected()) {
                codeGenerateService.generateEntityByTableInfoAndPathConfig(tableInfo, pathConfig);
                F9Notifier.notifyMessage(project, "entity生成完毕");
            }

            if (isServiceSelected()) {
                //生成service
                codeGenerateService.generateServiceByTableInfoAndPathConfig(tableInfo, pathConfig);
                F9Notifier.notifyMessage(project, "service生成完毕");
            }
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void open() {
        setPreferredSize(new Dimension(640, 360));
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public String getSelectedModule() {
        return moduleText.getText();
    }

    public String getSelectedPackage() {
        return packageText.getText();
    }

    public boolean isEntitySelected() {
        return entity.isSelected();
    }

    public boolean isServiceSelected() {
        return service.isSelected();
    }

    public boolean isCreateDefaultPackage() {
        return isCreateDefaultPackage.isSelected();
    }
}
