package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.tengfei.f9framework.notification.F9Notifier;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * F9文件的基类
 *
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
     *
     * @param directory 补丁包目录
     */
    public void copyToPatch(@NotNull VirtualFile directory) {
        PsiDirectory targetDirectory = PsiManager.getInstance(project).findDirectory(directory);
        if(targetDirectory == null) {
            return;
        }

        copyToTargetDirectory(targetDirectory,getPatchDirRelativePath());
    }

    /**
     *
     * @return 补丁包文件夹的相对路径
     */
    public abstract String getPatchDirRelativePath();

    protected void copyToTargetDirectory(PsiDirectory targetDirectory, String containingFileDirPath) {
        String ContainingFileDirPath = targetDirectory.getVirtualFile().getPath() + "/" + containingFileDirPath;

        VirtualFile directoryIfMissing = null;
        try {
            directoryIfMissing = VfsUtil.createDirectoryIfMissing(ContainingFileDirPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (directoryIfMissing != null) {
            PsiDirectory psiDirectory = PsiManager.getInstance(project).findDirectory(directoryIfMissing);
            if (psiDirectory == null) {
                return;
            }
            if (virtualFile.isDirectory()) {
                try {
                    PsiDirectory subdirectory = psiDirectory.createSubdirectory(virtualFile.getName());
                    VfsUtil.copyDirectory(this, virtualFile, subdirectory.getVirtualFile(), null);
                } catch (IOException e) {
                    F9Notifier.notifyError(project, "文件夹复制失败");
                }

            } else {
                PsiFile file = PsiManager.getInstance(project).findFile(virtualFile);
                if (file != null) {
                    psiDirectory.add(file);
                }
            }
        }
    }
}
