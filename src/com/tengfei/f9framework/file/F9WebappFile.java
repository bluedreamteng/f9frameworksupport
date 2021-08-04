package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.module.F9ModuleFacade;

/**
 * @author ztf
 */
public abstract class F9WebappFile extends F9File {
    protected F9ModuleFacade moduleFacade;

    public F9WebappFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
        F9ModuleFacade.getInstance(project);
    }

    /**
     * 返回web的路径
     *
     * @return 返回Web的路径
     */
    public abstract String getDeployWebPath();

    /**
     * 获取文件在web的相对路径
     * @return 文件在web的相对路径
     */
    public abstract String getWebRelativePath();

    /**
     * 获取文件补丁包的相对路径
     * @return 文件补丁包的相对路径
     */
    @Override
    public abstract String getPatchDirRelativePath();
}
