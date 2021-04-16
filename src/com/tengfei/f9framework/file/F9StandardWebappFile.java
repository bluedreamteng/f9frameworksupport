package com.tengfei.f9framework.file;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.tengfei.f9framework.notification.F9Notifier;
import com.tengfei.f9framework.setting.F9CustomizeModule;
import com.tengfei.f9framework.setting.F9ProjectSetting;
import com.tengfei.f9framework.setting.F9StandardModule;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author ztf
 */
public class F9StandardWebappFile extends F9WebappFile{
    public F9StandardWebappFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
    }

    @NotNull
    @Override
    public String getHost() {
        Module moduleForFile = ModuleUtil.findModuleForFile(virtualFile, project);
        if(moduleForFile == null) {
            return "";
        }
        for(F9StandardModule standardModule:projectSetting.standardModules) {
            if(moduleForFile.getName().equals(standardModule.getName())) {
                return standardModule.deployHost;
            }
        }
        return "";
    }

    public void copyToCustomize() {
        //如何定位到目标文件夹
        PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
        if(psiFile == null) {
            throw new RuntimeException("file is valid");
        }
        Module moduleForFile = ModuleUtil.findModuleForFile(psiFile);
        assert moduleForFile != null;
        List<F9CustomizeModule> customizeListOfStandardModule = F9ProjectSetting.getInstance(project).findCustomizeListOfStandardModule(moduleForFile.getName());
        if(customizeListOfStandardModule.size() == 0) {
            F9Notifier.notifyWarning(project,"当前标版模块没有相关联的个性化模块");
        }
        WriteCommandAction.runWriteCommandAction(psiFile.getProject(), () -> {
            String path = psiFile.getContainingDirectory().getVirtualFile().getPath();
            String newPath = path.replace(getWebRoot(), getWebRoot() + "/" + customizeListOfStandardModule.get(0).getCustomizeProjectPath());
            File file = new File(newPath);
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
                F9Notifier.notifyError(psiFile.getProject(), "file is already exist");
            }
        });
    }
}
