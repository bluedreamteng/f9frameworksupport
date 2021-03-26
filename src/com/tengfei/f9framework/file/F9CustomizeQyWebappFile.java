package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class F9CustomizeQyWebappFile extends F9WebappFile {

    public F9CustomizeQyWebappFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
    }

    @Override
    public String getWebRelativePath() {
        return virtualFile.getPath().split(f9SettingsState.qyProjectPagePath)[1];
    }

    @Override
    public void copyToPatch(VirtualFile directory) {

    }

    @NotNull
    @Override
    public String getHost() {
        return f9SettingsState.qyDeployHost;
    }
}
