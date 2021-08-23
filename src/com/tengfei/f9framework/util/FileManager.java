package com.tengfei.f9framework.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

import java.io.IOException;

/**
 * 文件操作相关工具类
 *
 * @author ztf
 */
public class FileManager {

    private final Project project;

    private FileManager(Project project) {
        this.project = project;
    }

    public static FileManager getInstance(Project project) {
        return new FileManager(project);
    }

    public void copyFileToTargetDirectory(String containingFileDirPath, VirtualFile virtualFile) {
        PsiDirectory psiDirectory = getPsiDirectory(containingFileDirPath);
        if (psiDirectory == null) {
            throw new RuntimeException("未找到对应文件夹的psi实例");
        }
        if (virtualFile.isDirectory()) {
            PsiDirectory directory = PsiManager.getInstance(project).findDirectory(virtualFile);
            assert directory != null;
            psiDirectory.add(directory);
        }
        else {
            PsiFile file = PsiManager.getInstance(project).findFile(virtualFile);
            assert file != null;
            psiDirectory.add(file);
        }
    }

    private PsiDirectory getPsiDirectory(String containingFileDirPath) {
        VirtualFile containingFileDir;
        try {
            containingFileDir = VfsUtil.createDirectoryIfMissing(containingFileDirPath);
        }
        catch (IOException e) {
            throw new RuntimeException("文件夹创建失败!!!");
        }
        assert containingFileDir != null;
        return PsiManager.getInstance(project).findDirectory(containingFileDir);
    }

}
