package com.tengfei.f9framework.settings.modulesetting.ui;

import com.intellij.ui.ToolbarDecorator;

import javax.swing.*;

public class TestForm {
    private JPanel mainPanel;
    private JPanel treePanel;
    private JPanel tablePanel;
    private JTable table1;

    public TestForm() {
        JPanel panel = ToolbarDecorator.createDecorator(new JTree())
                .setAddActionUpdater(e -> true)
                .setAddAction(button -> System.out.println("hello wrold"))
                .setEditActionUpdater(e -> true)
                .setEditAction(button -> System.out.println("hello wrold"))
                .setRemoveActionUpdater(e -> true)
                .setRemoveAction(button -> System.out.println("hello wrold"))
                .createPanel();
        mainPanel.add(panel);
    }

    public JPanel getPanel() {
        return mainPanel;
    }
}
