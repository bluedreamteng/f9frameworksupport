package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
class F9StandardQyWebappFile extends F9WebappFile {

    public F9StandardQyWebappFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
    }

    @NotNull
    @Override
    public String getHost() {
        return f9SettingsState.qyDeployHost;
    }
}
