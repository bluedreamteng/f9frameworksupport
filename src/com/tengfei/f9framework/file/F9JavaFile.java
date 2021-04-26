package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;

public class F9JavaFile extends F9File {
    public F9JavaFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
    }

    @Override
    public String getPatchDirRelativePath() {
        PsiFile file = PsiManager.getInstance(project).findFile(virtualFile);
        PsiJavaFile javaFile = (PsiJavaFile) file;
        String packageName = javaFile.getPackageName();
        if ("".equals(packageName)) {
            return "src";
        }
        return "src" + "/" + packageName.replaceAll("\\.", "/");
    }
}
