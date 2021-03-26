package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.setting.F9SettingsState;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public abstract class F9WebappFile extends F9File {
    protected F9SettingsState f9SettingsState;
    public F9WebappFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
        f9SettingsState = F9SettingsState.getInstance(project);
    }

    /**
     * 返回web的路径
     *
     * @return 返回Web的路径
     */
    public String getWebPath() {
        return getHost() + getWebRelativePath();
    }


    /**
     * 返回域名和端口号
     *
     * @return 返回域名和端口号
     */
    @NotNull
    public abstract String getHost();


    public String getWebRelativePath() {
        return virtualFile.getPath().split(f9SettingsState.webRootPath)[1];
    }

    @Override
    public abstract void copyToPatch(VirtualFile directory);
}
