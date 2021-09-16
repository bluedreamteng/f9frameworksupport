package com.tengfei.f9framework.settings.modulesetting;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author ztf
 */
public class F9CustomizeModuleSetting implements F9ModuleSetting{
    public String name;
    public String standardName;
    public String customizeProjectPath;

    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(@NotNull String standardName) {
        this.standardName = standardName;
    }

    public String getCustomizeProjectPath() {
        return customizeProjectPath;
    }

    public void setCustomizeProjectPath(@NotNull String customizeProjectPath) {
        this.customizeProjectPath = customizeProjectPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        F9CustomizeModuleSetting that = (F9CustomizeModuleSetting) o;
        return Objects.equals(standardName, that.standardName) &&
                Objects.equals(customizeProjectPath, that.customizeProjectPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(standardName, customizeProjectPath);
    }
}
