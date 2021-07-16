package com.tengfei.f9framework.file;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.setting.F9StandardModule;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class F9StandardWebappFile extends F9WebappFile {
    public F9StandardWebappFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
    }

    @NotNull
    @Override
    public String getHost() {
        Module moduleForFile = ModuleUtil.findModuleForFile(virtualFile, project);
        if (moduleForFile == null) {
            return "";
        }
        for (F9StandardModule standardModule : projectSetting.standardModules) {
            if (moduleForFile.getName().equals(standardModule.getName())) {
                return standardModule.deployHost;
            }
        }
        return "";
    }

}
