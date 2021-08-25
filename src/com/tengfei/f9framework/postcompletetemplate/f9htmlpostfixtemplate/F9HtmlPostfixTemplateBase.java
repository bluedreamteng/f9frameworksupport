package com.tengfei.f9framework.postcompletetemplate.f9htmlpostfixtemplate;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlText;
import com.tengfei.f9framework.util.EditorManage;
import org.jetbrains.annotations.NotNull;

public abstract class F9HtmlPostfixTemplateBase extends PostfixTemplate {
    /**
     * @param name    name of postfixTemplate
     * @param example 示例
     */
    protected F9HtmlPostfixTemplateBase(@NotNull String name, @NotNull String example) {
        super(name, example);
    }


    @Override
    public boolean isApplicable(@NotNull PsiElement context, @NotNull Document copyDocument, int newOffset) {
        return context.getParent() instanceof XmlText;
    }

    @Override
    public void expand(@NotNull PsiElement context, @NotNull Editor editor) {
        EditorManage.getInstance(editor).removeExpression(context);
        Project project = editor.getProject();
        assert project != null;
        final TemplateManager manager = TemplateManager.getInstance(project);
        final Template template = manager.createTemplate("", "", buildStringTemplate(context));
        template.setToReformat(true);
        manager.startTemplate(editor, template);
    }

    /**
     * 构造模板
     * @param context psielement
     * @return string 模板
     */
    public abstract String buildStringTemplate(PsiElement context);
}
