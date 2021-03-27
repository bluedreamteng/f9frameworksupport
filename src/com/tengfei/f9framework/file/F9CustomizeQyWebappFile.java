package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author ztf
 */
class F9CustomizeQyWebappFile extends F9QyWebappFile {

    public F9CustomizeQyWebappFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
    }

    @Override
    public String getWebPath() {
        return getHost() + "/" + f9SettingsState.glProjectName + getWebRelativePath();
    }

    @Override
    public String getWebRoot() {
        return f9SettingsState.qyProjectPagePath;
    }

    @Override
    public String getPatchDirRelativePath() {
        if("".equals(getContainingFileDirPath())) {
            return f9SettingsState.qyProjectName + "/" + f9SettingsState.customizeProjectName;
        }
        return f9SettingsState.qyProjectName + "/" + f9SettingsState.customizeProjectName + "/" + getContainingFileDirPath();
    }
}
