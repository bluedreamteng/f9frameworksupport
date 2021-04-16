package com.tengfei.f9framework.file;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.tengfei.f9framework.setting.F9CustomizeModule;
import com.tengfei.f9framework.setting.F9ProjectSetting;
import com.tengfei.f9framework.setting.F9StandardModule;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class F9FileFactory {

    public static final String JAR_EXTENSION = "jar";
    public static final String WEB_ROOT = "webapp";

    public static F9FileFactory getInstance() {
        return new F9FileFactory();
    }

    public F9File createF9File(@NotNull VirtualFile file, @NotNull Project project) {

        PsiFile psiFile = PsiManager.getInstance(project).findFile(file);

        if (isJavaFile(psiFile)) {
            return new F9JavaFile(file, project);
        }
        else if (isJarFile(file)) {
            return new F9JarFile(file, project);
        }
        else if (isStandardWebappFile(file, project)) {
            return new F9StandardWebappFile(file, project);
        }
        else if (isCustomizeWebappFile(file, project)) {
            return new F9CustomizeWebappFile(file, project);
        }
        else {
            return new F9UnSupportFile(file, project);
        }
    }


    public F9WebappFile createF9WebAppFile(@NotNull VirtualFile file, @NotNull Project project) {
        if (isStandardWebappFile(file, project)) {
            return new F9StandardWebappFile(file, project);
        }
        else if (isCustomizeWebappFile(file, project)) {
            return new F9CustomizeWebappFile(file, project);
        }
        else {
            throw new RuntimeException("unsupported file, please check your operation");
        }

    }

    private boolean isJarFile(VirtualFile file) {
        return JAR_EXTENSION.equals(file.getExtension());
    }

    private boolean isJavaFile(PsiFile psiFile) {
        if (psiFile != null) {
            return psiFile instanceof PsiJavaFile;
        }
        return false;
    }


    private boolean isStandardWebappFile(VirtualFile file, Project project) {
        F9ProjectSetting projectSetting = F9ProjectSetting.getInstance(project);
        Module moduleForFile = ModuleUtil.findModuleForFile(file, project);
        if (moduleForFile != null) {
            for (F9StandardModule standardModule : projectSetting.standardModules) {
                if (standardModule.getName().equals(moduleForFile.getName())) {
                    if (file.getPath().contains(WEB_ROOT)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isCustomizeWebappFile(VirtualFile file, Project project) {
        F9ProjectSetting projectSetting = F9ProjectSetting.getInstance(project);
        Module moduleForFile = ModuleUtil.findModuleForFile(file, project);
        if (moduleForFile != null) {
            for (F9StandardModule standardModule : projectSetting.standardModules) {
                for (F9CustomizeModule customizeModule : standardModule.customizeModuleList) {
                    if (customizeModule.getName().equals(moduleForFile.getName())) {
                        if (file.getPath().contains(customizeModule.getWebRoot())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
