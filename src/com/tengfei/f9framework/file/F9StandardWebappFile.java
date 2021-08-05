package com.tengfei.f9framework.file;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.tengfei.f9framework.module.F9CustomizeModule;
import com.tengfei.f9framework.module.F9StandardModule;
import com.tengfei.f9framework.notification.F9Notifier;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author ztf
 */
public class F9StandardWebappFile extends F9WebappFile {
    private final F9StandardModule standardModule;

    public F9StandardWebappFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
        Module moduleForFile = ModuleUtil.findModuleForFile(virtualFile, project);
        if (moduleForFile == null) {
            throw new RuntimeException("该文件未找到对应模块");
        }
        standardModule = moduleFacade.findStandardModuleByName(moduleForFile.getName());
        if (standardModule == null) {
            throw new RuntimeException("该文件未找到对应配置模块");
        }
    }

    /**
     * 返回web的路径
     *
     * @return 返回Web的路径
     */
    @Override
    public String getDeployWebPath() {
        return standardModule.getDeployHost() + "/" + standardModule.getName() + "/" + getWebRelativePath();
    }

    @Override
    public String getWebRelativePath() {
        String filePath = virtualFile.getPresentableUrl();
        assert filePath != null;
        String webPath = filePath.replace(standardModule.getWebRootPath(), "");
        List<F9CustomizeModule> customizeModuleList = standardModule.getCustomizeModuleList();
        //去除项目个性化文件目录
        for (F9CustomizeModule customizeModule : customizeModuleList) {
            String customizeProjectPath = customizeModule.getCustomizeProjectPath();
            if (!StringUtil.isEmpty(customizeProjectPath) && webPath.startsWith(customizeProjectPath)) {
                return webPath.replaceFirst(customizeProjectPath, "");
            }
        }

        //去除产品个性化文件目录
        String customizeProductPath = standardModule.getProductCustomizeName();
        if (!StringUtil.isEmpty(customizeProductPath) && webPath.startsWith(customizeProductPath)) {
            return webPath.replaceFirst(customizeProductPath, "");
        }
        return webPath;
    }

    /**
     * 获取文件补丁包的相对路径
     *
     * @return 文件补丁包的相对路径
     */
    @Override
    public String getPatchDirRelativePath() {
        String filePath = virtualFile.getPresentableUrl();
        return filePath.replace(standardModule.getWebRootPath(), "");
    }

    @Override
    public void copyToCustomize() {
        //如何定位到目标文件夹
        PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
        if (psiFile == null) {
            throw new RuntimeException("file is valid");
        }
        if (standardModule.getCustomizeModuleList().isEmpty()) {
            F9Notifier.notifyWarning(project, "当前标版模块没有相关联的个性化模块");
        }

        //默认选择第一个个性化模块
        WriteCommandAction.runWriteCommandAction(psiFile.getProject(), () -> {
            String webRelativePath = getWebRelativePath();
            String newPath = standardModule.getCustomizeModuleList().get(0).getWebRoot() + "/" + webRelativePath;
            File file = new File(newPath);
            if (file.exists()) {
                F9Notifier.notifyMessage(project, "文件已存在！！");
                return;
            }
            VirtualFile directoryIfMissing;
            try {
                directoryIfMissing = VfsUtil.createDirectoryIfMissing(file.getParent());
            }
            catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("文件夹创建失败");
            }
            assert directoryIfMissing != null;
            PsiDirectory directory = PsiManager.getInstance(psiFile.getProject()).findDirectory(directoryIfMissing);
            assert directory != null;
            PsiFile copiedFile = directory.copyFileFrom(psiFile.getName(), psiFile);
            FileEditorManager.getInstance(copiedFile.getProject()).openFile(copiedFile.getVirtualFile(), true);
        });
    }
}
