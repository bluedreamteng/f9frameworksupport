package com.tengfei.f9framework.postcompletetemplate.f9htmlpostfixtemplate;

import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlText;
import com.tengfei.f9framework.postcompletetemplate.F9AbstractPostfixTemplate;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class F9HtmlPostfixTemplate extends F9AbstractPostfixTemplate {
    /**
     * @param name    name of postfixTemplate
     * @param example 示例
     */
    protected F9HtmlPostfixTemplate(@NotNull String name, @NotNull String example, String template, PostfixTemplateProvider provider) {
        super(name, example,template, provider);
    }


    @Override
    public boolean isApplicable(@NotNull PsiElement context, @NotNull Document copyDocument, int newOffset) {
        return context.getParent() instanceof XmlText;
    }
}