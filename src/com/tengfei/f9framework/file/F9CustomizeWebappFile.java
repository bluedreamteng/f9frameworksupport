package com.tengfei.f9framework.file;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.module.F9CustomizeModule;

/**
 * @author ztf
 */
public class F9CustomizeWebappFile extends F9WebappFile {
    private final F9CustomizeModule customizeModule;

    public F9CustomizeWebappFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
        Module moduleForFile = ModuleUtil.findModuleForFile(virtualFile, project);
        if (moduleForFile == null) {
            throw new RuntimeException("该文件未找到对应模块");
        }
        customizeModule = moduleFacade.findCustomizeModuleByName(moduleForFile.getName());
        if (customizeModule == null) {
            throw new RuntimeException("该文件未找到对应配置模块");
        }
    }

    @Override
    public String getDeployWebPath() {
        return null;
    }

    @Override
    public String getWebRelativePath() {
        return null;
    }

    @Override
    public String getPatchDirRelativePath() {
        return null;
    }
}
