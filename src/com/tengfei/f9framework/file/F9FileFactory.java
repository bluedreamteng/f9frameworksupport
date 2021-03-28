package com.tengfei.f9framework.file;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.setting.F9CustomizeModule;
import com.tengfei.f9framework.setting.F9ProjectSetting;
import com.tengfei.f9framework.setting.F9SettingsState;
import com.tengfei.f9framework.setting.F9StandardModule;

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
        if(isStandardModule(file,project) ) {
            return new F9StandardWebappFile(file,project);
        }
        if(isCustomizeModule(file,project)) {
            return new F9CustomizeWebappFile(file,project);
        }

        return new F9UnSupportFile(file,project);
    }

    public F9WebappFile createWebappFile(VirtualFile file, Project project) {
        return null;
    }

    private boolean isStandardModule(VirtualFile file, Project project) {
        F9ProjectSetting projectSetting = F9ProjectSetting.getInstance(project);
        Module moduleForFile = ModuleUtil.findModuleForFile(file,project);
        if(moduleForFile == null) {
            return false;
        }
        for(F9StandardModule standardModule:projectSetting.standardModules) {
            if(standardModule.getName().equals(moduleForFile.getName())) {
                return true;
            }
        }
        return false;


    }

    private boolean isCustomizeModule(VirtualFile file, Project project) {
        F9ProjectSetting projectSetting = F9ProjectSetting.getInstance(project);
        Module moduleForFile = ModuleUtil.findModuleForFile(file,project);
        if(moduleForFile == null) {
            return false;
        }
        for(F9StandardModule standardModule:projectSetting.standardModules) {
            for(F9CustomizeModule customizeModule:standardModule.customizeModuleList) {
                if(customizeModule.getName().equals(moduleForFile.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
