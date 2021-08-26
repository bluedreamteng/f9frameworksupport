package com.tengfei.f9framework.postcompletetemplate.f9javapostfixtemplate;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateWithExpressionSelector;
import com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.tengfei.f9framework.util.EditorManage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 列举出所有的setter方法
 * @author ztf
 */
public class F9allSetTemplate extends PostfixTemplateWithExpressionSelector {

    public static final Pattern SETTERMETHODPATTERN = Pattern.compile("^set[A-Z]\\w*");

    public F9allSetTemplate() {
        super(
                "allset",
                "list all setter",
                JavaPostfixTemplatesUtils.selectorTopmost(element ->
                        element instanceof PsiReferenceExpression && ((PsiReferenceExpression) element).getType() != null)
        );
    }


    @Override
    protected void expandForChooseExpression(@NotNull PsiElement expression, @NotNull Editor editor) {
        EditorManage.getInstance(editor).removeExpression(expression);
        final Project project = expression.getProject();
        final TemplateManager manager = TemplateManager.getInstance(project);
        String className = ((PsiReferenceExpression) expression).getType().getCanonicalText();
        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, GlobalSearchScope.allScope(project));
        List<String> setters = getSetter(psiClass);
        final String stringTemplate = getSetsText(expression.getText(), setters) + "$END$";
        final Template template = manager.createTemplate("", "", stringTemplate);
        template.setToReformat(true);
        manager.startTemplate(editor, template);

    }

    private String getSetsText(String referenceName, List<String> methodNames) {
        StringBuilder builder = new StringBuilder();
        for (String methodName : methodNames) {
            builder.append(referenceName).append(".").append(methodName).append("();\n");
        }
        return builder.toString();
    }

    private static List<String> getSetter(PsiClass psiClass) {
        List<String> result = new ArrayList<>();
        if (psiClass == null) {
            return result;
        }
        PsiMethod[] methods = psiClass.getMethods();
        for (PsiMethod method : methods) {
            String methodName = method.getName();
            Matcher matcher = SETTERMETHODPATTERN.matcher(methodName);
            if (matcher.matches()) {
                result.add(methodName);
            }
        }
        return result;
    }
}