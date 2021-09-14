package com.tengfei.f9framework.settings.modulesetting;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.ui.ToolbarDecorator;
import com.tengfei.f9framework.settings.modulesetting.ui.F9DescriptionPanel;
import com.tengfei.f9framework.settings.modulesetting.ui.F9ModuleSettingCheckBoxTree;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author ztf
 */
public class F9ModuleSettingConfigurable implements Configurable {

    private final Project project;


    public F9ModuleSettingConfigurable(Project project) {
        this.project = project;
    }

    private final JSplitPane myPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    private F9DescriptionPanel descriptionPanel = new F9DescriptionPanel();

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
        myPanel.add(ToolbarDecorator.createDecorator(new F9ModuleSettingCheckBoxTree(project))
                .setAddActionUpdater(e -> true)
                .setAddAction(button -> System.out.println("hello world"))
                .setEditActionUpdater(e -> true)
                .setEditAction(button -> System.out.println("hello world"))
                .setRemoveActionUpdater(e -> true)
                .setRemoveAction(button -> System.out.println("hello world"))
                .createPanel());
        myPanel.add(descriptionPanel.getPanel());
        return myPanel;
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
