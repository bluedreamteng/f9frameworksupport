package com.tengfei.f9framework.file;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.module.F9CustomizeModule;
import com.tengfei.f9framework.module.F9StandardModule;
import com.tengfei.f9framework.notification.F9Notifier;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author ztf
 */
class F9StandardWebappFile extends F9WebappFile {
    private final F9StandardModule standardModule;

    F9StandardWebappFile(VirtualFile virtualFile, Project project) {
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
        String webRelativePath = getWebRelativePath();
        webRelativePath = StringUtil.trimExtensions(webRelativePath);
        return standardModule.getDeployHost() + "\\" + standardModule.getName() + "\\" + webRelativePath;
    }

    @Override
    public String getWebRelativePath() {
        String filePath = virtualFile.getPresentableUrl();
        String webPath = filePath.replace(standardModule.getWebRootPath(), "");
        webPath = StringUtil.trim(webPath, ch -> ch != '\\');
        List<F9CustomizeModule> customizeModuleList = standardModule.getCustomizeModuleList();
        //去除项目个性化文件目录
        for (F9CustomizeModule customizeModule : customizeModuleList) {
            String customizeProjectPath = customizeModule.getCustomizeProjectPath();
            if (!StringUtil.isEmpty(customizeProjectPath) && webPath.startsWith(customizeProjectPath)) {
                return StringUtil.trim(webPath.replaceFirst(customizeProjectPath, ""), ch -> ch != '\\');
            }
        }

        //去除产品个性化文件目录
        String customizeProductPath = standardModule.getProductCustomizeName();
        if (!StringUtil.isEmpty(customizeProductPath) && webPath.startsWith(customizeProductPath)) {
            return StringUtil.trim(webPath.replaceFirst(customizeProductPath, ""), ch -> ch != '\\');
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
        String parentPath = virtualFile.getParent().getPresentableUrl();
        String relativePath = parentPath.replace(standardModule.getWebRootPath(), "");
        relativePath = StringUtil.trim(relativePath, (ch -> ch != '\\'));
        return StringUtil.trim(standardModule.getName() + "\\" + relativePath,(ch -> ch != '\\'));
    }

    @Override
    public void copyToCustomize() {
        //如何定位到目标文件夹
        if (standardModule.getCustomizeModuleList().isEmpty()) {
            F9Notifier.notifyWarning(project, "当前标版模块没有相关联的个性化模块");
            return;
        }
        String webRelativePath = getWebRelativePath();
        String newPath = standardModule.getCustomizeModuleList().get(0).getWebRoot() + "/" + webRelativePath;
        File file = new File(newPath);
        if (file.exists()) {
            F9Notifier.notifyMessage(project, "文件已存在！！");
            return;
        }
        //默认选择第一个个性化模块
        WriteCommandAction.runWriteCommandAction(psiFile.getProject(), () -> {
            if (virtualFile.isDirectory()) {
                //在目标文件夹下创建一个一样的目录
                try {
                    VirtualFile directory = VfsUtil.createDirectoryIfMissing(file.getPath());
                    assert directory != null;
                    VfsUtil.copyDirectory("standardFile", virtualFile, directory, null);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else {
                try {
                    VirtualFile directoryIfMissing = VfsUtil.createDirectoryIfMissing(file.getParent());
                    assert directoryIfMissing != null;
                    VirtualFile copiedFile = VfsUtil.copy("standardmodule", virtualFile, directoryIfMissing);
                    FileEditorManager.getInstance(project).openFile(copiedFile, true);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("文件夹创建失败");
                }
            }
        });
        F9Notifier.notifyMessage(project, "相关文件创建完成");
    }
}
