package com.tengfei.f9framework.settings.modulesetting;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.ui.GuiUtils;
import com.intellij.ui.ToolbarDecorator;
import com.tengfei.f9framework.settings.modulesetting.ui.F9DescriptionPanel;
import com.tengfei.f9framework.settings.modulesetting.ui.F9ModuleSettingCheckBoxTree;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * @author ztf
 */
public class F9ModuleSettingConfigurable implements Configurable {

    private final Project project;

    private final JPanel myPanel = new JPanel();
    private final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    private F9DescriptionPanel descriptionPanel = new F9DescriptionPanel();

    private final F9ModuleSettingCheckBoxTree moduleSettingCheckBoxTree;


    public F9ModuleSettingConfigurable(Project project) {
        this.project = project;

        moduleSettingCheckBoxTree = new F9ModuleSettingCheckBoxTree(project) {
            @Override
            protected void selectionChanged() {
                selectTreeNode();
            }
        };
        myPanel.setLayout(new GridLayout(1,1));
        splitPane.add(ToolbarDecorator.createDecorator(moduleSettingCheckBoxTree)
                .setAddActionUpdater(e -> true)
                .setAddAction(button -> moduleSettingCheckBoxTree.addModuleSetting())
                .setEditActionUpdater(e -> moduleSettingCheckBoxTree.getSelectedModuleSetting() != null)
                .setEditAction(button -> System.out.println("hello world"))
                .setRemoveActionUpdater(e -> moduleSettingCheckBoxTree.getSelectedModuleSetting() != null)
                .setRemoveAction(button -> System.out.println("hello world"))
                .createPanel());
        splitPane.add(descriptionPanel.getPanel());
        myPanel.add(splitPane);
        GuiUtils.replaceJSplitPaneWithIDEASplitter(myPanel);

    }

    /**
     * Returns the visible name of the configurable component.
     * Note, that this method must return the display name
     * that is equal to the display name declared in XML
     * to avoid unexpected errors.
     *
     * @return the visible name of the configurable component
     */
    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "F9 Module Setting";
    }

    /**
     * Creates new Swing form that enables user to configure the settings.
     * Usually this method is called on the EDT, so it should not take a long time.
     * <p>
     * Also this place is designed to allocate resources (subscriptions/listeners etc.)
     *
     * @return new Swing form to show, or {@code null} if it cannot be created
     * @see #disposeUIResources
     */
    @Nullable
    @Override
    public JComponent createComponent() {
        return myPanel;
    }

    private void selectTreeNode() {
        F9ModuleSetting selectedModuleSetting = moduleSettingCheckBoxTree.getSelectedModuleSetting();
        descriptionPanel.setModuleDescription(selectedModuleSetting);
    }


    /**
     * Indicates whether the Swing form was modified or not.
     * This method is called very often, so it should not take a long time.
     *
     * @return {@code true} if the settings were modified, {@code false} otherwise
     */
    @Override
    public boolean isModified() {
        return false;
    }

    /**
     * Stores the settings from the Swing form to the configurable component.
     * This method is called on EDT upon user's request.
     *
     * @throws ConfigurationException if values cannot be applied
     */
    @Override
    public void apply() throws ConfigurationException {

    }
}
