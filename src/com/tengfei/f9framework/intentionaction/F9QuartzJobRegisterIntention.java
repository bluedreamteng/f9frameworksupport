package com.tengfei.f9framework.intentionaction;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class F9QuartzJobRegisterIntention extends PsiElementBaseIntentionAction {

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getText() {
        return "Register job";
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "Register job to quartz-data config file";
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        if (!isJobClassName(element)) {
            return false;
        }
        //判断配置文件内是不是已经有了这个标签

        return true;
    }


    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) {

    }


    public static boolean isJobClassName(PsiElement element) {

        if (element == null) {
            return false;
        }

        if (!(element instanceof PsiIdentifier)) {
            return false;
        }

        if (!(element.getParent() instanceof PsiClass)) {
            return false;
        }
        PsiClass psiClass = (PsiClass) element.getParent();

        if (psiClass.getSuperClass() == null || !element.getText().equals(psiClass.getName())) {
            return false;
        }

        return "org.quartz.Job".equals(psiClass.getSuperClass().getQualifiedName());
    }
}
