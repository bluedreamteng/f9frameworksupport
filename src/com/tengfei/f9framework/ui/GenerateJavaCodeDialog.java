package com.tengfei.f9framework.ui;

import com.intellij.database.psi.DbTable;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ui.configuration.ChooseModulesDialog;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiPackage;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.notification.F9Notifier;
import com.tengfei.f9framework.service.TableCodeGenerateService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author ztf
 */
public class GenerateJavaCodeDialog extends JDialog {
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
    private JCheckBox addAction;
    private JCheckBox editAction;
    private JCheckBox listAction;
    private JCheckBox detailAction;
    private JCheckBox All;

    private final Project project;
    private final DbTable dbTable;
    private final TableCodeGenerateService codeGenerateService;


    public GenerateJavaCodeDialog(Project project, DbTable dbTable) {
        this.project = project;
        this.dbTable = dbTable;
        codeGenerateService = TableCodeGenerateService.getInstance(project);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Generate Java Code");

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

        All.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                entity.setSelected(All.isSelected());
                service.setSelected(All.isSelected());
                addAction.setSelected(All.isSelected());
                editAction.setSelected(All.isSelected());
                listAction.setSelected(All.isSelected());
                detailAction.setSelected(All.isSelected());
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
        if (StringUtil.isNotEmpty(getSelectedModule()) && getSelectedPackage() != null) {
            PathConfig pathConfig = new PathConfig(getSelectedModule(), getSelectedPackage(), isCreateDefaultPackage());
            TableInfo tableInfo = new TableInfo(dbTable);
            if (isEntitySelected()) {
                codeGenerateService.generateEntityFile(tableInfo, pathConfig);
                F9Notifier.notifyMessage(project, "entity生成完毕");
            }

            if (isServiceSelected()) {
                //生成service
                codeGenerateService.generateServiceFile(tableInfo, pathConfig);
                F9Notifier.notifyMessage(project, "service生成完毕");
            }

            if (isAddActionSelected()) {
                codeGenerateService.generateAddActionFile(tableInfo, pathConfig);
                F9Notifier.notifyMessage(project, "add action 生成完毕");
            }

            if (isEditActionSelected()) {
                codeGenerateService.generateEditActionFile(tableInfo, pathConfig);
                F9Notifier.notifyMessage(project, "edit action 生成完毕");
            }

            if (isListActionSelected()) {
                codeGenerateService.generateListActionFile(tableInfo, pathConfig);
                F9Notifier.notifyMessage(project, "list action 生成完毕");
            }

            if (isDetailActionSelected()) {
                codeGenerateService.generateDetailActionFile(tableInfo, pathConfig);
                F9Notifier.notifyMessage(project, "detail action 生成完毕");
            }

        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void open() {
        pack();
        setLocationRelativeTo(null);
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

    public boolean isAddActionSelected() {
        return addAction.isSelected();
    }

    public boolean isEditActionSelected() {
        return editAction.isSelected();
    }

    public boolean isListActionSelected() {
        return listAction.isSelected();
    }

    public boolean isDetailActionSelected() {
        return detailAction.isSelected();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(530, 340);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(5, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel1.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        moduleText = new JTextField();
        moduleText.setEditable(false);
        panel3.add(moduleText, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        chooseModuleButton = new JButton();
        chooseModuleButton.setText("Choose Module");
        panel3.add(chooseModuleButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        packageText = new JTextField();
        packageText.setEditable(false);
        panel3.add(packageText, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        choosePackageButton = new JButton();
        choosePackageButton.setText("Choose Package");
        panel3.add(choosePackageButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        entity = new JCheckBox();
        entity.setSelected(true);
        entity.setText("Entity");
        panel4.add(entity, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        service = new JCheckBox();
        service.setSelected(true);
        service.setText("Service");
        panel4.add(service, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        All = new JCheckBox();
        All.setSelected(true);
        All.setText("All");
        panel4.add(All, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel5, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        isCreateDefaultPackage = new JCheckBox();
        isCreateDefaultPackage.setSelected(true);
        isCreateDefaultPackage.setText("Create Default Package");
        panel5.add(isCreateDefaultPackage, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel6, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addAction = new JCheckBox();
        addAction.setSelected(true);
        addAction.setText("Add Action");
        panel6.add(addAction, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editAction = new JCheckBox();
        editAction.setSelected(true);
        editAction.setText("Edit Action");
        panel6.add(editAction, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        listAction = new JCheckBox();
        listAction.setSelected(true);
        listAction.setText("List Action");
        panel6.add(listAction, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        detailAction = new JCheckBox();
        detailAction.setSelected(true);
        detailAction.setText("Detail Action");
        panel6.add(detailAction, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
