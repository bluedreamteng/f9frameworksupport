package com.tengfei.f9framework.util;

import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;

/**
 * @author ztf
 */
public class PsiFileUtil {
    public static void reformatJavaFile(PsiJavaFile javaFile) {
        JavaCodeStyleManager.getInstance(javaFile.getProject()).shortenClassReferences(javaFile);
        CodeStyleManager.getInstance(javaFile.getProject()).reformat(javaFile);
    }

    public static void reformatFile(PsiFile psiFile) {
        CodeStyleManager.getInstance(psiFile.getProject()).reformat(psiFile);
    }
}
