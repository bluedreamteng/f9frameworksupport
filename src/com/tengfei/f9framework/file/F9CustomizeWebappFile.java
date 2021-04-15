package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.setting.F9CustomizeModule;
import com.tengfei.f9framework.setting.F9StandardModule;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class F9CustomizeWebappFile extends F9WebappFile {
    CustomizeModuleInfo customizeModuleInfo;
    public F9CustomizeWebappFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
        customizeModuleInfo = getCustomizeModuleInfo();
    }

    @Override
    public String getWebPath() {
        return getHost() + "/" + customizeModuleInfo.containingStandardPrjName + getWebRelativePath();
    }

    @Override
    public String getWebRoot() {
        return customizeModuleInfo.webRoot;
    }

    @NotNull
    @Override
    public String getHost() {
        return customizeModuleInfo.deployHost;
    }

    @Override
    public String getPatchDirRelativePath() {
        if ("".equals(getContainingFileDirPath())) {
            return customizeModuleInfo.containingStandardPrjName + "/" + customizeModuleInfo.customizeProjectPath;
        }
        return customizeModuleInfo.containingStandardPrjName + "/" + customizeModuleInfo.customizeProjectPath + "/" + getContainingFileDirPath();
    }

    private CustomizeModuleInfo getCustomizeModuleInfo() {
        CustomizeModuleInfo customizeModuleInfo = new CustomizeModuleInfo();
        for(F9StandardModule standardModule:projectSetting.standardModules) {
            for(F9CustomizeModule customizeModule:standardModule.customizeModuleList) {
                if(virtualFile.getPath().contains(customizeModule.getWebRoot())) {
                    customizeModuleInfo.name = customizeModule.name;
                    customizeModuleInfo.containingStandardPrjName = standardModule.name;
                    customizeModuleInfo.deployHost =standardModule.deployHost;
                    customizeModuleInfo.webRoot = customizeModule.webRoot;
                    customizeModuleInfo.customizeProjectPath = customizeModule.customizeProjectPath;
                }
            }
        }
        return customizeModuleInfo;
    }

    public static class CustomizeModuleInfo {
        public String name;
        public String webRoot;
        public String customizeProjectPath;

        public String containingStandardPrjName;

        public String deployHost;
    }
}
