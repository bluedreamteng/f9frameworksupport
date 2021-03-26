package com.tengfei.f9framework.file;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.tengfei.f9framework.setting.F9SettingsState;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * @author ztf
 */
public abstract class F9WebappFile extends F9File {
    protected F9SettingsState f9SettingsState;
    public F9WebappFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
        f9SettingsState = F9SettingsState.getInstance(project);
    }

    /**
     * 返回web的路径
     *
     * @return 返回Web的路径
     */
    public String getWebPath() {
        return getHost() + getWebRelativePath();
    }


    /**
     * 返回域名和端口号
     *
     * @return 返回域名和端口号
     */
    @NotNull
    public abstract String getHost();


    public String getWebRelativePath() {
        return virtualFile.getPath().split(f9SettingsState.webRootPath)[1];
    }

    @Override
    public void copyToPatch(@NotNull VirtualFile directory) {
        PsiDirectory targetDirectory = PsiManager.getInstance(project).findDirectory(directory);
        if(targetDirectory == null) {
            return;
        }
        Module containingModule = ModuleUtil.findModuleForFile(virtualFile, project);
        if(containingModule == null) {
            throw new InvalidParameterException("not module file");
        }
        String path = containingModule.getName()+getWebRelativePath();
        copyToTargetDirectory(targetDirectory, path);

    }

    protected void copyToTargetDirectory(PsiDirectory targetDirectory, String path) {
        VirtualFile directoryIfMissing = null;
        try {
            directoryIfMissing = VfsUtil.createDirectoryIfMissing(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(directoryIfMissing != null) {
            PsiDirectory psiDirectory = PsiManager.getInstance(project).findDirectory(directoryIfMissing);
            if(psiDirectory == null) {
                return;
            }
            PsiFile file = PsiManager.getInstance(project).findFile(virtualFile);
            if(file != null) {
                psiDirectory.add(file);
                targetDirectory.add(psiDirectory);
            }
        }
    }

}
