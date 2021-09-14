package com.tengfei.f9framework.settings.modulesetting;

/**
 * @author ztf
 */
public class F9CustomizeModuleSetting implements F9ModuleSetting{
    public String name;
    public String customizeProjectPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomizeProjectPath() {
        return customizeProjectPath;
    }

    public void setCustomizeProjectPath(String customizeProjectPath) {
        this.customizeProjectPath = customizeProjectPath;
    }
}
