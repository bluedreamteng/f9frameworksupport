package com.tengfei.f9framework.settings.modulesetting.ui;

import com.intellij.ui.CheckedTreeNode;
import com.tengfei.f9framework.settings.modulesetting.F9CustomizeModuleSetting;
import com.tengfei.f9framework.settings.modulesetting.F9StandardModuleSetting;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class F9CusModuleSettingNode extends CheckedTreeNode {
    private F9CustomizeModuleSetting customizeModuleSetting;

    public F9CusModuleSettingNode(@NotNull F9CustomizeModuleSetting customizeModuleSetting) {
        this.customizeModuleSetting = customizeModuleSetting;
    }

    @Override
    public String toString() {
        return customizeModuleSetting.getName();
    }
}
