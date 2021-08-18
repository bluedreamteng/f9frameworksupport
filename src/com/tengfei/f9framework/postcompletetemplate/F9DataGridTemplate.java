package com.tengfei.f9framework.postcompletetemplate;

import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateWithExpressionSelector;
import com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.tengfei.f9framework.util.EditorManage;
import org.jetbrains.annotations.NotNull;

public class F9DataGridTemplate extends PostfixTemplateWithExpressionSelector {
    protected F9DataGridTemplate() {
        super("datagrid", "datagrid", "getDataGridModel", JavaPostfixTemplatesUtils.selectorTopmost(element -> {
           if(!(element instanceof PsiIdentifier) && element.getParent() != null && element.getParent().getParent() != null){
               return false;
           }
           return element.getParent().getParent() instanceof PsiClass;

        }));
    }

    @Override
    protected void expandForChooseExpression(@NotNull PsiElement expression, @NotNull Editor editor) {
        EditorManage.getInstance(editor).removeExpression(expression);

    }
}
