package com.tengfei.f9framework.settings.normalsetting;

import com.intellij.openapi.util.EmptyRunnable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.FieldPanel;
import com.intellij.ui.TitledSeparator;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;
import com.tengfei.f9framework.util.F9ChooseFileUtil;

import javax.swing.*;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 *
 * @author ztf
 */
public class F9SettingsComponent {

    private final JPanel myMainPanel;
    private final JBCheckBox enableF9ChromeSupport = new JBCheckBox("Enable F9 Chrome Support");
    private FieldPanel htmlPostfixLibraryPath = null;
    private FieldPanel javaPostfixLibraryPath = null;
    private FieldPanel htmlAttributeDictionaryPath = null;


    public F9SettingsComponent() {
        htmlPostfixLibraryPath = new FieldPanel("", null, (actionEvent) -> {
            VirtualFile virtualFile = F9ChooseFileUtil.chooseFile();
            if (virtualFile != null) {
                htmlPostfixLibraryPath.setText(virtualFile.getPresentableUrl());
            }
        }, EmptyRunnable.getInstance());

        javaPostfixLibraryPath = new FieldPanel("", null, (actionEvent) -> {
            VirtualFile virtualFile = F9ChooseFileUtil.chooseFile();
            if (virtualFile != null) {
                javaPostfixLibraryPath.setText(virtualFile.getPresentableUrl());
            }
        }, EmptyRunnable.getInstance());

        htmlAttributeDictionaryPath = new FieldPanel("", null, (actionEvent) -> {
            VirtualFile virtualFile = F9ChooseFileUtil.chooseFile();
            if (virtualFile != null) {
                htmlAttributeDictionaryPath.setText(virtualFile.getPresentableUrl());
            }
        }, EmptyRunnable.getInstance());
        myMainPanel = FormBuilder.createFormBuilder()
                .addComponent(new TitledSeparator("Application Setting"))
                .addLabeledComponent("Html Postfix Template Library Path:", htmlPostfixLibraryPath)
                .addLabeledComponent("Java Postfix Template Library Path:", javaPostfixLibraryPath)
                .addLabeledComponent("Html Attribute Dictionary Path:", htmlAttributeDictionaryPath)
                .addComponent(new TitledSeparator("Project Setting"))
                .addComponent(enableF9ChromeSupport, 1)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return enableF9ChromeSupport;
    }

    public boolean getEnableF9ChromeSupport() {
        return enableF9ChromeSupport.isSelected();
    }

    public void setEnableF9ChromeSupport(boolean enable) {
        this.enableF9ChromeSupport.setSelected(enable);
    }

    public void setHtmlPostfixLibraryPath(String path) {
        htmlPostfixLibraryPath.setText(path);
    }

    public void setJavaPostfixLibraryPath(String path) {
        javaPostfixLibraryPath.setText(path);
    }

    public void setHtmlAttributeDictionaryPath(String path) {
        htmlAttributeDictionaryPath.setText(path);
    }

    public String getHtmlPostfixLibraryPath() {
        return htmlPostfixLibraryPath.getText();
    }

    public String getJavaPostfixLibraryPath() {
        return javaPostfixLibraryPath.getText();
    }

    public String getHtmlAttributeDictionaryPath() {
        return htmlAttributeDictionaryPath.getText();
    }
}
