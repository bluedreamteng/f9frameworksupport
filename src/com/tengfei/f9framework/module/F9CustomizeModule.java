package com.tengfei.f9framework.module;

/**
 * @author ztf
 */
public class F9CustomizeModule {
    private String name;
    private String webRoot;
    private F9StandardModule standardModule;
    private String customizeProjectPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebRoot() {
        return webRoot;
    }

    public void setWebRoot(String webRoot) {
        this.webRoot = webRoot;
    }

    public F9StandardModule getStandardModule() {
        return standardModule;
    }

    public void setStandardModule(F9StandardModule standardModule) {
        this.standardModule = standardModule;
    }

    public String getCustomizeProjectPath() {
        return customizeProjectPath;
    }

    public void setCustomizeProjectPath(String customizeProjectPath) {
        this.customizeProjectPath = customizeProjectPath;
    }
}
