package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
class F9StandardGlWebappFile extends F9WebappFile {

    public F9StandardGlWebappFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
    }


    @NotNull
    @Override
    public String getHost() {
        return f9SettingsState.glDeployHost;
    }
}
