package com.tengfei.f9framework.postcompletetemplate;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateWithExpressionSelector;
import com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.tengfei.f9framework.util.EditorManage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class F9DataGridTemplate extends PostfixTemplate {


    protected F9DataGridTemplate() {
        super("datagrid", "getDataGridModel");
    }

    @Override
    public boolean isApplicable(@NotNull PsiElement context, @NotNull Document copyDocument, int newOffset) {
        return context instanceof PsiIdentifier;
    }

    @Override
    public void expand(@NotNull PsiElement context, @NotNull Editor editor) {
        EditorManage.getInstance(editor).removeExpression(context);
        Project project =editor.getProject();

        assert project != null;
        final TemplateManager manager = TemplateManager.getInstance(project);
        final String stringTemplate = "System.out.println(\"kdkdk\");";
        final Template template = manager.createTemplate("", "", stringTemplate);
        template.setToReformat(true);
        manager.startTemplate(editor, template);
    }
}
