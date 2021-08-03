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
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author ztf
 */
public abstract class F9WebappFile extends F9File {
    protected F9ProjectSetting projectSetting;

    public F9WebappFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
        projectSetting = F9ProjectSetting.getInstance(project);
    }

    /**
     * 返回web的路径
     *
     * @return 返回Web的路径
     */
    public String getDeployWebPath() {
        Module moduleForFile = ModuleUtil.findModuleForFile(virtualFile, project);
        return getHost() + "/" + moduleForFile.getName() + "/" + getWebRelativePath();
    }


    /**
     * 返回域名和端口号
     *
     * @return 返回域名和端口号
     */
    @NotNull
    public abstract String getHost();


    public String getWebRelativePath() {
        String result = virtualFile.getPath().split(getWebRoot() + "/")[1];
        if (virtualFile.getExtension() != null && "".equals(virtualFile.getExtension())) {
            result = result.substring(0, result.indexOf('.'));
        }
        return result;
    }

    public String getWebRoot() {
        return "webapp";
    }

    public String getContainingFileDirPath() {
        if (!getWebRelativePath().contains("/")) {
            //根目录
            return "";
        }
        return getWebRelativePath().substring(0, getWebRelativePath().lastIndexOf('/'));
    }

    @Override
    public String getPatchDirRelativePath() {
        Module moduleForFile = ModuleUtil.findModuleForFile(virtualFile, project);
        if (moduleForFile == null) {
            F9Notifier.notifyMessage(project, "文件不属于任何模块");
            throw new UnsupportedOperationException();
        }
        return moduleForFile.getName() + "/" + getContainingFileDirPath();
    }

    public void copyToCustomize() {
        //如何定位到目标文件夹
        PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
        if (psiFile == null) {
            throw new RuntimeException("file is valid");
        }
        Module moduleForFile = ModuleUtil.findModuleForFile(psiFile);
        assert moduleForFile != null;
        List<F9CustomizeModule> customizeListOfStandardModule = F9ProjectSetting.getInstance(project).findCustomizeListOfStandardModule(moduleForFile.getName());
        if (customizeListOfStandardModule.size() == 0) {
            F9Notifier.notifyWarning(project, "当前标版模块没有相关联的个性化模块");
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
