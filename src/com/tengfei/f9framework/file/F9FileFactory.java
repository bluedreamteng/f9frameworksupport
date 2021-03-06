package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.tengfei.f9framework.module.F9CustomizeModule;
import com.tengfei.f9framework.module.F9ModuleFacade;
import com.tengfei.f9framework.module.F9StandardModule;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author ztf
 */
public class F9FileFactory {

    public static final String JAR_EXTENSION = "jar";

    private final F9ModuleFacade f9ModuleFacade;

    public static F9FileFactory getInstance(Project project) {
        return new F9FileFactory(project);
    }

    public F9FileFactory(Project project) {
        f9ModuleFacade = F9ModuleFacade.getInstance(project);
    }

    public F9File createF9File(@NotNull VirtualFile file, @NotNull Project project) throws UnsupportedFileException {

        PsiFile psiFile = PsiManager.getInstance(project).findFile(file);

        if (isJavaFile(psiFile)) {
            return new F9JavaFile(file, project);
        }
        else if (isJarFile(file)) {
            return new F9JarFile(file, project);
        }
        else if (isStandardWebappFile(file)) {
            return new F9StandardWebappFile(file, project);
        }
        else if (isCustomizeWebappFile(file)) {
            return new F9CustomizeWebappFile(file, project);
        }
        else {
           throw new UnsupportedFileException();
        }
    }


    public F9WebappFile createF9WebAppFile(@NotNull VirtualFile file, @NotNull Project project) throws UnsupportedFileException {
        if (isStandardWebappFile(file)) {
            return new F9StandardWebappFile(file, project);
        }
        else if (isCustomizeWebappFile(file)) {
            return new F9CustomizeWebappFile(file, project);
        }
        else {
            throw new UnsupportedFileException();
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


    private boolean isStandardWebappFile(VirtualFile file) {
        List<F9StandardModule> allStandardModules = f9ModuleFacade.findAllStandardModules();
        for (F9StandardModule standardModule : allStandardModules) {
            if (standardModule.getWebRootPath() == null) {
                continue;
            }
            //?????????????????????
            if (file.getPresentableUrl().equals(standardModule.getWebRootPath())) {
                continue;
            }

            //??????????????????????????????
            if (file.getPresentableUrl().startsWith(standardModule.getWebRootPath())) {
                return true;
            }
        }

        return false;
    }

    private boolean isCustomizeWebappFile(VirtualFile file) {
        List<F9CustomizeModule> allCustomizeModules = f9ModuleFacade.findAllCustomizeModules();
        for (F9CustomizeModule customizeModule : allCustomizeModules) {
            if (customizeModule.getWebRoot() == null) {
                continue;
            }

            //?????????????????????
            if (file.getPresentableUrl().equals(customizeModule.getWebRoot())) {
                continue;
            }

            //??????????????????????????????
            if (file.getPresentableUrl().startsWith(customizeModule.getWebRoot())) {
                return true;
            }

        }
        return false;
    }
}
