package com.tengfei.f9framework.settings.modulesetting.ui;

import com.intellij.ui.CheckedTreeNode;
import com.tengfei.f9framework.settings.modulesetting.F9StandardModuleSetting;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class F9StdModuleSettingNode extends CheckedTreeNode {
    private F9StandardModuleSetting standardModuleSetting;

    public F9StdModuleSettingNode(@NotNull F9StandardModuleSetting standardModuleSetting) {
        this.standardModuleSetting = standardModuleSetting;
    }

    @NotNull
    public F9StandardModuleSetting getStandardModuleSetting() {
        return standardModuleSetting;
    }

    @Override
    public String toString() {
        return standardModuleSetting.getName();
    }
}
