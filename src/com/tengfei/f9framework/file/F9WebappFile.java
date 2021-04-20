package com.tengfei.f9framework.file;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.notification.F9Notifier;
import com.tengfei.f9framework.setting.F9ProjectSetting;
import com.tengfei.f9framework.setting.F9SettingsState;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public abstract class F9WebappFile extends F9File {
    protected F9SettingsState f9SettingsState;
    protected F9ProjectSetting projectSetting;

    public F9WebappFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
        f9SettingsState = F9SettingsState.getInstance(project);
        projectSetting = F9ProjectSetting.getInstance(project);
    }

    /**
     * 返回web的路径
     *
     * @return 返回Web的路径
     */
    public String getDeployWebPath() {
        Module moduleForFile = ModuleUtil.findModuleForFile(virtualFile, project);
        assert moduleForFile != null;
        return getHost() + "/" + moduleForFile.getName() + "/" + getWebRelativePath();
    }


    /**
     * 返回域名和端口号
     *
     * @return 返回域名和端口号
     */
    @NotNull
    public abstract String getHost();


    public String getWebRelativePath() {
        String result = virtualFile.getPath().split(getWebRoot() + "/")[1];
        if (virtualFile.getExtension() != null && "".equals(virtualFile.getExtension())) {
            result = result.substring(0, result.indexOf('.'));
        }
        return result;
    }

    public String getWebRoot() {
        return "webapp";
    }

    public String getContainingFileDirPath() {
        if (!getWebRelativePath().contains("/")) {
            //根目录
            return "";
        }
        return getWebRelativePath().substring(0, getWebRelativePath().lastIndexOf('/'));
    }

    @Override
    public String getPatchDirRelativePath() {
        Module moduleForFile = ModuleUtil.findModuleForFile(virtualFile, project);
        if (moduleForFile == null) {
            F9Notifier.notifyMessage(project, "文件不属于任何模块");
            throw new UnsupportedOperationException();
        }
        return moduleForFile.getName() + "/" + getContainingFileDirPath();
    }
}
