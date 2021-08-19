package com.tengfei.f9framework.entity;

/**
 * @author ztf
 */
public class PathConfig {
    private final String moduleName;

    private final String packageName;

    private final boolean createDefaultPackage;

    public PathConfig(String moduleName, String packageName, boolean createDefaultPackage) {
        this.moduleName = moduleName;
        this.packageName = packageName;
        this.createDefaultPackage = createDefaultPackage;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getEntityPackageName() {
        String result = "";
        if (isCreateDefaultPackage()) {
            if ("".equals(packageName)) {
                result = packageName + "api.entity";
            }
            else {
                result = packageName + ".api.entity";
            }
        }
        return result;
    }

    public String getServiceInterfacePackageName() {
        String result = "";
        if (isCreateDefaultPackage()) {
            if ("".equals(packageName)) {
                result = packageName + "api";
            }
            else {
                result = packageName + ".api";
            }
        }
        return result;
    }

    public String getServiceImplPackageName() {
        String result = "";
        if (isCreateDefaultPackage()) {
            if ("".equals(packageName)) {
                result = packageName + "api.impl";
            }
            else {
                result = packageName + ".api.impl";
            }
        }
        return result;
    }

    public String getActionPackageName() {
        String result = "";
        if (isCreateDefaultPackage()) {
            if ("".equals(packageName)) {
                result = packageName + "action";
            }
            else {
                result = packageName + ".action";
            }
        }
        return result;
    }

    public String getServicePackageName() {
        return getServiceImplPackageName();
    }

    public boolean isCreateDefaultPackage() {
        return createDefaultPackage;
    }
}
