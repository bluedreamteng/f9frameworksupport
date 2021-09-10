package com.tengfei.f9framework.util;

import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.util.PackageUtil;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import org.jetbrains.jps.model.java.JavaSourceRootType;

import java.util.List;

/**
 * @author ztf
 */
public class F9FileFacade {

    private final PsiFileFactory fileFactory;
    private final Project project;


    private F9FileFacade(Project project) {
        this.project = project;
        fileFactory = PsiFileFactory.getInstance(project);
    }

    public static F9FileFacade getInstance(Project project) {
        return new F9FileFacade(project);
    }


    public void createJavaFile(String moduleName, String packageName, PsiClass javaClass) {
        //创建实体java文件
        PsiJavaFile entityFile = (PsiJavaFile) fileFactory.createFileFromText(javaClass.getName() + "." + JavaFileType.INSTANCE.getDefaultExtension(), JavaFileType.INSTANCE, "");
        entityFile.add(javaClass);
        createJavaFile(moduleName,packageName,entityFile);
    }


    public void createJavaFile(String moduleName, String packageName, String className, String classText) {
        //创建实体java文件
        PsiJavaFile javafile = (PsiJavaFile) fileFactory.createFileFromText(className + "." + JavaFileType.INSTANCE.getDefaultExtension(), JavaFileType.INSTANCE, classText);
        createJavaFile(moduleName, packageName, javafile);
    }

    public void createJavaFile(String moduleName, String packageName, PsiJavaFile javafile) {
        PsiFileCodeStyleUtil.reformatJavaFile(javafile);
        Module moduleByName = ModuleManager.getInstance(javafile.getProject()).findModuleByName(moduleName);
        assert moduleByName != null;
        List<VirtualFile> sourceRoots = ModuleRootManager.getInstance(moduleByName).getSourceRoots(JavaSourceRootType.SOURCE);
        VirtualFile virtualFile = sourceRoots.get(0);
        PsiDirectory directory = PsiManager.getInstance(javafile.getProject()).findDirectory(virtualFile);
        WriteCommandAction.runWriteCommandAction(javafile.getProject(), () -> {
            PsiDirectory packageDirectory = PackageUtil.findOrCreateDirectoryForPackage(moduleByName, packageName, directory, false);
            assert packageDirectory != null;
            packageDirectory.add(javafile);
        });
    }

    public void createHtmlFile(String fileName,String htmlFileText,VirtualFile directory) {
        if(directory == null || !directory.isDirectory()) {
            throw new RuntimeException("directory must not be null and be a real directory");
        }
        PsiFile htmlFile = fileFactory.createFileFromText(fileName + HtmlFileType.DOT_DEFAULT_EXTENSION, HtmlFileType.INSTANCE, htmlFileText);
        PsiFileCodeStyleUtil.reformatFile(htmlFile);
        PsiDirectory psiDirectory = PsiManager.getInstance(project).findDirectory(directory);
        WriteCommandAction.runWriteCommandAction(project, () -> {
            assert psiDirectory != null;
            psiDirectory.add(htmlFile);
        });

    }
}
