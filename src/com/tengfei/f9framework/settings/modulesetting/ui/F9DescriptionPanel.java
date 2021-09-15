package com.tengfei.f9framework.settings.modulesetting.ui;

import com.tengfei.f9framework.settings.modulesetting.F9CustomizeModuleSetting;
import com.tengfei.f9framework.settings.modulesetting.F9ModuleSetting;
import com.tengfei.f9framework.settings.modulesetting.F9StandardModuleSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * @author ztf
 */
public class F9DescriptionPanel {
    public static final String STD = "std";
    public static final String CUS = "cus";
    public static final String NULL = "null";
    private final JPanel myPanel;

    private final CardLayout cardLayout = new CardLayout();

    private final F9StdModuleDescriptionPanel stdModuleDescriptionPanel;

    private final F9CusModuleDescriptionPanel cusModuleDescriptionPanel;

    private final JPanel nullPanel = new JPanel();

    public F9DescriptionPanel() {
        myPanel = new JPanel();
        myPanel.setLayout(cardLayout);
        stdModuleDescriptionPanel = new F9StdModuleDescriptionPanel();
        cusModuleDescriptionPanel = new F9CusModuleDescriptionPanel();
        myPanel.add(STD, stdModuleDescriptionPanel.getPanel());
        myPanel.add(CUS, cusModuleDescriptionPanel.getPanel());
        myPanel.add(NULL, nullPanel);
        cardLayout.show(myPanel, NULL);
    }

    /**
     *
     * @param moduleSetting
     */
    public void setModuleDescription(@Nullable F9ModuleSetting moduleSetting) {
        if(moduleSetting == null) {
            return;
        }
        if(moduleSetting instanceof F9StandardModuleSetting) {
            F9StandardModuleSetting stdModuleSetting = (F9StandardModuleSetting) moduleSetting;
            stdModuleDescriptionPanel.setStdModuleSetting(stdModuleSetting);
            cardLayout.show(myPanel, STD);
        } else if(moduleSetting instanceof F9CustomizeModuleSetting) {
            F9CustomizeModuleSetting cusModuleSetting = (F9CustomizeModuleSetting) moduleSetting;
            cusModuleDescriptionPanel.setCusModuleSetting(cusModuleSetting);
            cardLayout.show(myPanel, CUS);
        }
    }

    @NotNull
    public JPanel getPanel() {
        return myPanel;
    }






}
