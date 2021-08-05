package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.tengfei.f9framework.util.FileUtils;
import org.jetbrains.annotations.NotNull;

/**
 * F9文件的基类
 *
 * @author ztf
 */
public abstract class F9File {

    protected VirtualFile virtualFile;
    protected Project project;
    protected PsiFile psiFile;

    public F9File(VirtualFile virtualFile, Project project) {
        this.virtualFile = virtualFile;
        this.project = project;
        psiFile = PsiManager.getInstance(project).findFile(virtualFile);
    }

    /**
     * 将文件复制到补丁包中
     *
     * @param directory 补丁包目录
     */
    public void copyToPatch(@NotNull VirtualFile directory) {
        PsiDirectory targetDirectory = PsiManager.getInstance(project).findDirectory(directory);
        assert targetDirectory != null;
        String containingFileDirPath = targetDirectory.getVirtualFile().getPath() + "/" + getPatchDirRelativePath();
        FileUtils.copyFileToTargetDirectory(containingFileDirPath, psiFile);
    }

    /**
     * 获取补丁包文件夹的相对路径
     *
     * @return 补丁包文件夹的相对路径
     */
    public abstract String getPatchDirRelativePath();
}
