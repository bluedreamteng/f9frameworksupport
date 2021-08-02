package com.tengfei.f9framework.util;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.util.PackageUtil;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.tengfei.f9framework.entity.PathConfig;
import org.jetbrains.jps.model.java.JavaSourceRootType;

import java.util.List;

/**
 * @author ztf
 */
public class GenerateTableCodeUtil {
    public static void generateCode(String moduleName, String packageName, PsiClass javaClass) {
        PsiFileFactory fileFactory = PsiFileFactory.getInstance(javaClass.getProject());
        //创建实体java文件
        PsiJavaFile entityFile = (PsiJavaFile) fileFactory.createFileFromText(javaClass.getName() + ".java", JavaFileType.INSTANCE, "");
        entityFile.add(javaClass);
        PsiJavaFileUtil.reformatJavaFile(entityFile);
        Module moduleByName = ModuleManager.getInstance(entityFile.getProject()).findModuleByName(moduleName);
        assert moduleByName != null;
        List<VirtualFile> sourceRoots = ModuleRootManager.getInstance(moduleByName).getSourceRoots(JavaSourceRootType.SOURCE);
        VirtualFile virtualFile = sourceRoots.get(0);
        PsiDirectory directory = PsiManager.getInstance(entityFile.getProject()).findDirectory(virtualFile);
        WriteCommandAction.runWriteCommandAction(entityFile.getProject(), () -> {
            PsiDirectory packageDirectory = PackageUtil.findOrCreateDirectoryForPackage(moduleByName, packageName, directory, true);
            assert packageDirectory != null;
            packageDirectory.add(entityFile);
        });
    }
}
