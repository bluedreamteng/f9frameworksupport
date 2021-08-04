package com.tengfei.f9framework.module;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ztf
 */
public class F9StandardModule {
    private String name;

    private String deployHost;

    private String productCustomizeName;

    private String webRootPath;

    private List<F9CustomizeModule> customizeModuleList = new ArrayList<>();

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

    public String getWebRootPath() {
        return webRootPath;
    }

    public void setWebRootPath(String webRootPath) {
        this.webRootPath = webRootPath;
    }

    public List<F9CustomizeModule> getCustomizeModuleList() {
        return customizeModuleList;
    }

    public void setCustomizeModuleList(List<F9CustomizeModule> customizeModuleList) {
        this.customizeModuleList = customizeModuleList;
    }
}
