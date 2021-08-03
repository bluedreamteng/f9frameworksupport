package com.tengfei.f9framework.setting;

import java.util.ArrayList;
import java.util.List;

public class F9StandardModule {
    public String name;

    public String deployHost;

    public String productCustomizeName;

    public List<F9CustomizeModule> customizeModuleList = new ArrayList<>();


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

    public List<F9CustomizeModule> getCustomizeModuleList() {
        return customizeModuleList;
    }

    public void setCustomizeModuleList(List<F9CustomizeModule> customizeModuleList) {
        this.customizeModuleList = customizeModuleList;
    }
}
