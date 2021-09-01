package com.tengfei.f9framework.postcompletetemplate;

import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class F9JavaEditablePostfixTemplate extends F9AbstractPostfixTemplate{
    /**
     * @param name     name of postfixTemplate
     * @param example  示例
     * @param template
     * @param provider
     */
    protected F9JavaEditablePostfixTemplate(@NotNull String id, @NotNull String name, @NotNull String example, @NotNull String template, TemplateImpl liveTemplate, PostfixTemplateProvider provider) {
        super(id,name, example, template,liveTemplate, provider);
    }

    protected F9JavaEditablePostfixTemplate(@NotNull String id, @NotNull String name, @NotNull String example, @NotNull String template, PostfixTemplateProvider provider) {
        super(id,name, example, template, provider);
    }

    /**
     * 元素是否适用此模板
     *
     * @param context      context
     * @param copyDocument copyDocument
     * @param newOffset    newOffset
     * @return true 适用  false 不适用
     */
    @Override
    public boolean isApplicable(@NotNull PsiElement context, @NotNull Document copyDocument, int newOffset) {
        return false;
    }
}
