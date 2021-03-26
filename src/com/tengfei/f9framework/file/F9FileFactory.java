package com.tengfei.f9framework.file;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.tengfei.f9framework.setting.F9SettingsState;

/**
 * @author ztf
 */
public class F9FileFactory {

    public static F9FileFactory getInstance() {
        return new F9FileFactory();
    }

    public F9File createFile(VirtualFile file, Project project) {
        Module moduleForFile = ModuleUtil.findModuleForFile(file,project);
        if (moduleForFile == null) {
            return new F9UnSupportFile(file, project);
        }
        F9SettingsState settingsState = F9SettingsState.getInstance(project);

        if (!(file.getPath().contains(settingsState.webRootPath))) {
            return new F9UnSupportFile(file, project);
        }

        if(settingsState.glProjectName.equals(moduleForFile.getName())) {
            return new F9StandardGlWebappFile(file, project);
        } else if (settingsState.qyProjectName.equals(moduleForFile.getName())) {
            return new F9StandardQyWebappFile(file, project);
        } else if(settingsState.customizeProjectName.equals(moduleForFile.getName())) {
            if (file.getPath().contains(settingsState.glProjectPagePath)) {
                return new F9CustomizeGlWebappFile(file, project);
            } else if (file.getPath().contains(settingsState.qyProjectPagePath)) {
                return new F9CustomizeQyWebappFile(file, project);
            }
        }
        return new F9UnSupportFile(file,project);
    }

    public F9WebappFile createWebappFile(VirtualFile file, Project project) {
        return null;
    }
}
