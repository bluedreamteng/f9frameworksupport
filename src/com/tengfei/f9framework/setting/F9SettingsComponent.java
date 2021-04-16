package com.tengfei.f9framework.setting;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 *
 * @author ztf
 */
public class F9SettingsComponent {

    private final JPanel myMainPanel;
    private final JBCheckBox enableF9ChromeSupport = new JBCheckBox("Enable F9 Chrome Support");

    public F9SettingsComponent() {
        myMainPanel = FormBuilder.createFormBuilder()
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
}
