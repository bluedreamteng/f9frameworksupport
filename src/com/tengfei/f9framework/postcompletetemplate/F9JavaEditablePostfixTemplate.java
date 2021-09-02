package com.tengfei.f9framework.postcompletetemplate;

import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ztf
 */
public class F9JavaEditablePostfixTemplate extends F9AbstractPostfixTemplate{
    /**
     * @param name     name of postfixTemplate
     * @param example  示例
     * @param provider
     */
    protected F9JavaEditablePostfixTemplate(@NotNull String id, @NotNull String name, @NotNull String example, TemplateImpl liveTemplate, PostfixTemplateProvider provider) {
        super(id,name, example,liveTemplate, provider);
    }

    protected F9JavaEditablePostfixTemplate(@NotNull String id, @NotNull String name, @NotNull String example, @NotNull String template, PostfixTemplateProvider provider) {
        super(id,name, example,createTemplate(template), provider);
    }

    @Override
    protected List<PsiElement> getExpressions(@NotNull PsiElement context, @NotNull Document document, int offset) {
        return new ArrayList<>();
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
        return true;
    }
}
