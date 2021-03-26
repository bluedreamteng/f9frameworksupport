package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
class F9CustomizeGlWebappFile extends F9WebappFile {

    public F9CustomizeGlWebappFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
    }

    @Override
    public String getWebRelativePath() {
        return virtualFile.getPath().split(f9SettingsState.glProjectPagePath)[1];
    }

    @Override
    public void copyToPatch(@NotNull VirtualFile directory) {
        PsiDirectory targetDirectory = PsiManager.getInstance(project).findDirectory(directory);
        if(targetDirectory == null) {
            return;
        }
        String path = f9SettingsState.glProjectName + "/" + f9SettingsState.customizeProjectName + getWebRelativePath();
        copyToTargetDirectory(targetDirectory,path);
    }

    @NotNull
    @Override
    public String getHost() {
        return f9SettingsState.glDeployHost;
    }
}
