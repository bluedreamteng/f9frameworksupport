package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.module.setting.F9ProjectSetting;
import com.tengfei.f9framework.module.setting.F9StandardModuleSetting;
import com.tengfei.f9framework.util.FileUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author ztf
 */
class F9JarFile extends F9File {
    F9JarFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
    }

    @Override
    public void copyToPatch(@NotNull VirtualFile directory) throws IOException {
        F9ProjectSetting projectSetting = F9ProjectSetting.getInstance(project);
        for (F9StandardModuleSetting standardModule : projectSetting.standardModules) {
            String containingFileDir = directory.getPresentableUrl()  + "/" + standardModule.getName() + "/" + "WEB-INF/lib";
            FileUtil.copyFileToTargetDirectory(containingFileDir, virtualFile);
        }
    }

    @Override
    public String getPatchDirRelativePath() {
        throw new UnsupportedOperationException();
    }
}
