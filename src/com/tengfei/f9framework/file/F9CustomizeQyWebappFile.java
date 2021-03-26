package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
class F9CustomizeQyWebappFile extends F9WebappFile {

    public F9CustomizeQyWebappFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
    }

    @Override
    public String getWebRelativePath() {
        return virtualFile.getPath().split(f9SettingsState.qyProjectPagePath)[1];
    }

    @Override
    public void copyToPatch(VirtualFile directory) {
        PsiDirectory targetDirectory = PsiManager.getInstance(project).findDirectory(directory);
        if(targetDirectory == null) {
            return;
        }
        String path = f9SettingsState.qyProjectName + "/" + f9SettingsState.qyProjectName + getWebRelativePath();
        copyToTargetDirectory(targetDirectory,path);
    }

    @NotNull
    @Override
    public String getHost() {
        return f9SettingsState.qyDeployHost;
    }
}
