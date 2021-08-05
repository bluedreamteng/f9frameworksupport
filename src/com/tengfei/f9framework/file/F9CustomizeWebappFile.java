package com.tengfei.f9framework.file;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.module.F9CustomizeModule;
import com.tengfei.f9framework.module.F9StandardModule;
import com.tengfei.f9framework.notification.F9Notifier;

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
        F9StandardModule standardModule = customizeModule.getStandardModule();
        return standardModule.getDeployHost() + "/" + standardModule.getName() + "/" + getWebRelativePath();
    }

    @Override
    public String getWebRelativePath() {
        return virtualFile.getCanonicalPath().replaceFirst(customizeModule.getWebRoot(),"");
    }

    @Override
    public String getPatchDirRelativePath() {
        F9StandardModule standardModule = customizeModule.getStandardModule();
        String relativepath = virtualFile.getCanonicalPath().replaceFirst(customizeModule.getWebRoot(),"");
        return standardModule + "/" + customizeModule.getCustomizeProjectPath() + relativepath;
    }

    /**
     * 复制到个性化目录
     */
    @Override
    public void copyToCustomize() {
        F9Notifier.notifyMessage(project,"个性化文件夹不支持此操作");
    }
}
