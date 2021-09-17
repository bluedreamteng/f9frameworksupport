package com.tengfei.f9framework.settings.modulesetting;

import java.util.*;

/**
 * @author ztf
 */
public class F9StandardModuleSetting implements F9ModuleSetting{
    public String name;

    public String deployHost;

    public String productCustomizeName;

    public List<F9CustomizeModuleSetting> customizeModuleList = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeployHost() {
        return deployHost;
    }

    public void setDeployHost(String deployHost) {
        this.deployHost = deployHost;
    }

    public String getProductCustomizeName() {
        return productCustomizeName;
    }

    public void setProductCustomizeName(String productCustomizeName) {
        this.productCustomizeName = productCustomizeName;
    }

    public List<F9CustomizeModuleSetting> getCustomizeModuleList() {
        return customizeModuleList;
    }

    public void setCustomizeModuleList(List<F9CustomizeModuleSetting> customizeModuleList) {
        this.customizeModuleList = customizeModuleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        F9StandardModuleSetting that = (F9StandardModuleSetting) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
