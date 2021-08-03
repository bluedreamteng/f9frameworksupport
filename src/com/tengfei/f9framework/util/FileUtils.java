package com.tengfei.f9framework.util;

import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

import java.io.IOException;

/**
 * 文件操作相关工具类
 * @author ztf
 */
public class FileUtils {
    public static void copyFileToTargetDirectory(String containingFileDirPath, PsiFile file) {
        VirtualFile containingFileDir;
        try {
            containingFileDir = VfsUtil.createDirectoryIfMissing(containingFileDirPath);
        }
        catch (IOException e) {
           throw new RuntimeException("文件夹创建失败!!!");
        }
        if(containingFileDir == null) {
            throw new RuntimeException("文件夹创建失败!!!");
        }
        PsiDirectory psiDirectory = PsiManager.getInstance(file.getProject()).findDirectory(containingFileDir);
        if(psiDirectory == null) {
            throw new RuntimeException("未找到对应文件夹的psi实例");
        }
        psiDirectory.add(file);
    }

}
