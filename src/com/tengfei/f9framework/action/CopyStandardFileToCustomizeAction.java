package com.tengfei.f9framework.action;

import com.intellij.ide.util.PackageUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.tengfei.f9framework.notification.MyNotifier;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.java.JavaSourceRootType;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.tengfei.f9framework.setting.F9Settings.*;

/**
 * @author ztf
 */
public class CopyStandardFileToCustomizeAction extends AnAction {

    /**
     * @param anActionEvent 事件模型
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        PsiFile psiFile = anActionEvent.getData(CommonDataKeys.PSI_FILE);
        if (psiFile == null) {
            return;
        }
        if (psiFile instanceof PsiJavaFile) {
            copyJavaFileToCustomize((PsiJavaFile) psiFile);
        }
        else {
            copyWebFileToCustomize(psiFile);
        }
    }

    private void copyWebFileToCustomize(@NotNull PsiFile psiFile) {
        //如何定位到目标文件夹
        String path = psiFile.getContainingDirectory().getVirtualFile().getPath();
        if (!path.contains(WEB_ROOT_PATH) || path.contains(GL_PROJECT_PAGE_PATH) || path.contains(QY_PROJECT_PAGE_PATH)) {
            return;
        }

        WriteCommandAction.runWriteCommandAction(psiFile.getProject(), () -> {
            String newpath = path.replace(WEB_ROOT_PATH, WEB_ROOT_PATH + "/" + CUSTOMIZE_PROJECT_NAME);
            File file = new File(newpath);
            if (!file.exists()) {
                boolean success = file.mkdirs();
                if (!success) {
                    return;
                }
            }
            VirtualFile directoryIfMissing;
            try {
                String realPath = file.toPath().toRealPath().toString();
                file.delete();
                directoryIfMissing = VfsUtil.createDirectoryIfMissing(realPath);
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
            if (directoryIfMissing == null) {
                return;
            }

            PsiDirectory directory = PsiManager.getInstance(psiFile.getProject()).findDirectory(directoryIfMissing);
            if (directory == null) {
                return;
            }
            PsiFile targetFile = directory.findFile(psiFile.getName());
            if (targetFile == null) {
                PsiFile copiedFile = directory.copyFileFrom(psiFile.getName(), psiFile);
                FileEditorManager.getInstance(copiedFile.getProject()).openFile(copiedFile.getVirtualFile(), true);
            }
            else {
                MyNotifier.notifyError(psiFile.getProject(), "file is already exist");
            }
        });
    }


    private void copyJavaFileToCustomize(@NotNull PsiJavaFile psiJavaFile) {
        System.out.println(psiJavaFile.getPackageName());
        Module module = ModuleManager.getInstance(psiJavaFile.getProject()).findModuleByName("szjs_custom_sfzhgd");
        if(module == null) {
            return;
        }
        List<VirtualFile> sourceRoots = ModuleRootManager.getInstance(module).getSourceRoots(JavaSourceRootType.SOURCE);
        if(CollectionUtils.isEmpty(sourceRoots)) {
            return;
        }
        WriteCommandAction.runWriteCommandAction(psiJavaFile.getProject(),()->{
            VirtualFile virtualFile = sourceRoots.get(0);
            PsiDirectory directory = PsiManager.getInstance(psiJavaFile.getProject()).findDirectory(virtualFile);
            PsiDirectory packageDirectory = PackageUtil.findOrCreateDirectoryForPackage(module, psiJavaFile.getPackageName(), directory, true);
            if(packageDirectory == null) {
                return;
            }
            PsiFile foundFile = packageDirectory.findFile(psiJavaFile.getName());
            if(foundFile == null) {
                PsiJavaFile addedFile =(PsiJavaFile) packageDirectory.add(psiJavaFile);
                FileEditorManager.getInstance(psiJavaFile.getProject()).openFile(addedFile.getVirtualFile(), true);
            }else {
                MyNotifier.notifyError(psiJavaFile.getProject(), "file is already exist");
            }
        });


    }
}
