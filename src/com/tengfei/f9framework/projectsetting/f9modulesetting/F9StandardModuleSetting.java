package com.tengfei.f9framework.projectsetting.f9modulesetting;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ztf
 */
public class F9StandardModuleSetting {
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
}
