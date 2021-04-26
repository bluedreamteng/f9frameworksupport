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
import com.intellij.openapi.roots.ui.configuration.ChooseModulesDialog;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.tengfei.f9framework.notification.F9Notifier;
import com.tengfei.f9framework.setting.F9SettingsState;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.java.JavaSourceRootType;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
            copyJavaFileToTargetModule((PsiJavaFile) psiFile);
        }
        else {
            copyWebFileToCustomize(psiFile);
        }
    }

    private void copyWebFileToCustomize(@NotNull PsiFile psiFile) {

    }


    private void copyJavaFileToTargetModule(@NotNull PsiJavaFile psiJavaFile) {

        ChooseModulesDialog chooseModulesDialog = new ChooseModulesDialog(psiJavaFile.getProject(), Arrays.asList(ModuleManager.getInstance(psiJavaFile.getProject()).getModules()), "choose  module", null);
        chooseModulesDialog.setSingleSelectionMode();
        chooseModulesDialog.show();
        List<Module> chosenElements = chooseModulesDialog.getChosenElements();
        if (chosenElements.size() == 0) {
            return;
        }
        Module module = chosenElements.get(0);
        List<VirtualFile> sourceRoots = ModuleRootManager.getInstance(module).getSourceRoots(JavaSourceRootType.SOURCE);
        if (CollectionUtils.isEmpty(sourceRoots)) {
            return;
        }
        WriteCommandAction.runWriteCommandAction(psiJavaFile.getProject(), () -> {
            VirtualFile virtualFile = sourceRoots.get(0);
            PsiDirectory directory = PsiManager.getInstance(psiJavaFile.getProject()).findDirectory(virtualFile);
            PsiDirectory packageDirectory = PackageUtil.findOrCreateDirectoryForPackage(module, psiJavaFile.getPackageName(), directory, true);
            if (packageDirectory == null) {
                return;
            }
            PsiFile foundFile = packageDirectory.findFile(psiJavaFile.getName());
            if (foundFile == null) {
                PsiJavaFile addedFile = (PsiJavaFile) packageDirectory.add(psiJavaFile);
                FileEditorManager.getInstance(psiJavaFile.getProject()).openFile(addedFile.getVirtualFile(), true);
            }
            else {
                F9Notifier.notifyError(psiJavaFile.getProject(), "file is already exist");
            }
        });


    }
}
