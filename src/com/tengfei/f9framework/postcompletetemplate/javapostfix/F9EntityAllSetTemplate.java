package com.tengfei.f9framework.postcompletetemplate.javapostfix;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateWithExpressionSelector;
import com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.search.GlobalSearchScope;
import com.tengfei.f9framework.util.EditorManage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 列举出所有的setter方法
 *
 * @author ztf
 */
public class F9EntityAllSetTemplate extends PostfixTemplateWithExpressionSelector {

    public static final Pattern SETTERMETHODPATTERN = Pattern.compile("^set([A-Z]\\w*)");

    public F9EntityAllSetTemplate() {
        super(
                "entityallset",
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
        final String stringTemplate = getSetsWithComment(expression.getText(), psiClass) + "$END$";
        final Template template = manager.createTemplate("", "", stringTemplate);
        template.setToReformat(true);
        manager.startTemplate(editor, template);

    }


    private static String getSetsWithComment(String referenceName, PsiClass psiClass) {
        StringBuilder result = new StringBuilder();
        List<PsiMethod> allSetMethods = getSetter(psiClass);
        for (PsiMethod setterMethod : allSetMethods) {
            StringBuilder eachText = new StringBuilder();
            String fieldName = getFieldNameBySetterMethod(setterMethod);
            //获取getMethod
            String getMethodName = setterMethod.getName().replace("set","get");
            PsiMethod psiMethod = getMethodByMethodName(getMethodName, psiClass);
            //获取getMethod上面的注释
            String comment = getMethodComment(psiMethod);
            eachText.append("//" + comment + "\n");
            //获取getMethod的返回类型
            String returnType = getMethodReturnType(psiMethod);
            eachText.append(returnType + " "+ fieldName + " = null;" + "\n");
            eachText.append(referenceName + "." + setterMethod.getName() + "(" + fieldName + ");\n");
            result.append(eachText + "\n");
        }
        return result.toString();


    }

    private static String getFieldNameBySetterMethod(PsiMethod setterMethod) {
        PsiParameter[] parameters = setterMethod.getParameterList().getParameters();
        return parameters[0].getName();
    }


    @Nullable
    private static PsiMethod getMethodByMethodName(String methodName, PsiClass psiClass) {
        if (psiClass == null || StringUtil.isEmpty(methodName)) {
            return null;
        }
        PsiMethod[] methods = psiClass.getMethods();
        //获取所有字段名称
        for (PsiMethod method : methods) {
            if (StringUtil.toLowerCase(methodName).toLowerCase().equals(StringUtil.toLowerCase(method.getName()))) {
                return method;
            }
        }
        return null;
    }


    @NotNull
    private static String getMethodComment(PsiMethod psiMethod) {
        if (psiMethod == null) {
            return "";
        }
        PsiDocComment docComment = psiMethod.getDocComment();
        if (docComment == null) {
            return "";
        }
        PsiElement[] descriptionElements = docComment.getDescriptionElements();
        if (descriptionElements.length == 0) {
            return "";
        }
        return descriptionElements[1].getText();
    }

    @NotNull
    private static String getMethodReturnType(PsiMethod psiMethod) {
        if (psiMethod == null) {
            return "";
        }
        PsiType returnType = psiMethod.getReturnType();
        if (returnType == null) {
            return "";
        }
        return returnType.getPresentableText();
    }


    private static List<PsiMethod> getSetter(PsiClass psiClass) {
        List<PsiMethod> result = new ArrayList<>();
        if (psiClass == null) {
            return result;
        }
        PsiMethod[] methods = psiClass.getMethods();
        for (PsiMethod method : methods) {
            String methodName = method.getName();
            Matcher matcher = SETTERMETHODPATTERN.matcher(methodName);
            if (matcher.matches()) {
                result.add(method);
            }
        }
        return result;
    }


}