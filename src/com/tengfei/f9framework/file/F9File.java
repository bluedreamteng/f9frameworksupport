package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * F9文件的基类
 * @author ztf
 */
public abstract class F9File {

    protected VirtualFile virtualFile;
    protected Project project;
    public F9File(VirtualFile virtualFile, Project project) {
        this.virtualFile = virtualFile;
        this.project = project;
    }

    /**
     * 将文件复制到补丁包中
     * @param directory 补丁包目录
     * @return
     */
    public abstract void copyToPatch(VirtualFile directory);
}
