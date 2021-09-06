package com.tengfei.f9framework.settings.modulesetting;

import com.intellij.codeInsight.CodeInsightBundle;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.util.PlatformIcons;
import com.intellij.util.ui.FormBuilder;
import com.tengfei.f9framework.settings.modulesetting.ui.TestForm;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class F9ModuleSettingConfigurable implements Configurable {
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
        return new TestForm().getPanel();
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

    private  static AnActionButton duplicateAction() {
        AnActionButton button = new AnActionButton(CodeInsightBundle.messagePointer("action.AnActionButton.text.duplicate"), PlatformIcons.COPY_ICON) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                System.out.println("hello wrold");
            }

            @Override
            public void updateButton(@NotNull AnActionEvent e) {
                e.getPresentation().setEnabled(true);
            }
        };
        return button;
    }
}
