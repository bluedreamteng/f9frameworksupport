package com.tengfei.f9framework.postcompletetemplate.f9javapostfixtemplate;

import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.tengfei.f9framework.postcompletetemplate.F9AbstractPostfixTemplate;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public abstract class F9JavaPostfixTemplateBase extends F9AbstractPostfixTemplate {


    /**
     * @param name    name of postfixTemplate
     * @param example 示例
     */
    protected F9JavaPostfixTemplateBase(@NotNull String name, @NotNull String example,String template, PostfixTemplateProvider provider) {
        super(name, example,template,provider);
    }


    @Override
    public boolean isApplicable(@NotNull PsiElement context, @NotNull Document copyDocument, int newOffset) {
        return context instanceof PsiIdentifier;
    }


}
