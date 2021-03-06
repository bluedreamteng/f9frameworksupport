package com.tengfei.f9framework.reference;

import com.intellij.lang.javascript.psi.JSCallExpression;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.tengfei.f9framework.sytaxpattern.F9JsMethod;
import com.tengfei.f9framework.util.F9WebControllerFacade;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * the reference of f9framework action method
 *
 * @author ztf
 */
public class F9MethodReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private final String methodName;
    private final String annotationValue;

    public F9MethodReference(@NotNull PsiElement element, TextRange textRange) {
        super(element, textRange);
        methodName = StringUtil.trim(element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset()));
        annotationValue = StringUtil.trim(getInitPageUrl(),(ch -> ch != '"' && ch != '\''));
    }

    private String getInitPageUrl() {
        PsiElement[] psiElements = PsiTreeUtil.collectElements(myElement.getContainingFile(), element -> element instanceof JSCallExpression);
        for (PsiElement psiElement : psiElements) {
            JSCallExpression jsCallExpression = (JSCallExpression) psiElement;
            if (jsCallExpression.getText().contains(F9JsMethod.INITPAGE)) {
                assert jsCallExpression.getArgumentList() != null;
                return jsCallExpression.getArgumentList().getArguments()[0].getText();
            }
        }
        return null;
    }

    public F9MethodReference(@NotNull PsiElement element, TextRange textRange, @NotNull String annotationValue, @NotNull String methodName) {
        super(element, textRange);
        this.methodName = StringUtil.trim(methodName);
        this.annotationValue = StringUtil.trim(annotationValue);
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        final List<PsiMethod> psiElements = F9WebControllerFacade.getInstance(project,annotationValue).findMethodByMethodName(methodName);
        List<ResolveResult> results = new ArrayList<>();
        for (PsiMethod psiMethod : psiElements) {
            results.add(new PsiElementResolveResult(psiMethod));
        }
        return results.toArray(new ResolveResult[0]);
    }

    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Project project = myElement.getProject();
        List<PsiMethod> javaMethodByAnnotationValue = F9WebControllerFacade.getInstance(project, annotationValue).findAllMethods();
        return javaMethodByAnnotationValue.toArray();
    }
}
