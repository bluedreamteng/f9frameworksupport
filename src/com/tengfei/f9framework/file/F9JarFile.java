package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import com.tengfei.f9framework.setting.F9ProjectSetting;
import com.tengfei.f9framework.setting.F9StandardModule;
import com.tengfei.f9framework.util.FileUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class F9JarFile extends F9File {
    public F9JarFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
    }

    @Override
    public void copyToPatch(@NotNull VirtualFile directory) {
        PsiDirectory targetDirectory = PsiManager.getInstance(project).findDirectory(directory);
        assert targetDirectory != null;
        F9ProjectSetting projectSetting = F9ProjectSetting.getInstance(project);
        for (F9StandardModule standardModule : projectSetting.standardModules) {
            String containingFileDir = targetDirectory + "/" + standardModule.getName() + "/" + "WEB-INF/lib";
            FileUtils.copyFileToTargetDirectory(containingFileDir, psiFile);
        }
    }

    @Override
    public String getPatchDirRelativePath() {
        throw new UnsupportedOperationException();
    }
}
