package com.tengfei.f9framework.file;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.module.F9CustomizeModule;
import com.tengfei.f9framework.module.F9StandardModule;
import com.tengfei.f9framework.notification.F9Notifier;

/**
 * @author ztf
 */
class F9CustomizeWebappFile extends F9WebappFile {
    private final F9CustomizeModule customizeModule;

    F9CustomizeWebappFile(VirtualFile virtualFile, Project project) {
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
        String webRelativePath =  virtualFile.getPresentableUrl().replace(customizeModule.getWebRoot(), "");
        webRelativePath = StringUtil.trim(webRelativePath,(ch)-> ch != '\\');
        return webRelativePath;
    }

    @Override
    public String getPatchDirRelativePath() {
        F9StandardModule standardModule = customizeModule.getStandardModule();
        String relativepath = virtualFile.getParent().getPresentableUrl().replace(customizeModule.getWebRoot(), "");
        relativepath = StringUtil.trim(relativepath,(ch)->ch != '\\');
        return standardModule.getName() + "/" + customizeModule.getCustomizeProjectPath() + "/" + relativepath;
    }

    /**
     * 复制到个性化目录
     */
    @Override
    public void copyToCustomize() {
        F9Notifier.notifyMessage(project, "个性化文件夹不支持此操作");
    }
}
