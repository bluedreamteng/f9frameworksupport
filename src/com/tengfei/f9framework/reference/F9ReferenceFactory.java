package com.tengfei.f9framework.reference;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.tengfei.f9framework.sytaxpattern.F9SyntaxPattern;
import com.tengfei.f9framework.util.StringUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class F9ReferenceFactory {

    public static final String PERIODOPERATOR = ".";

    public static PsiReference[] createReferenceByElement(PsiElement element) {

        if (!notEmpty(element.getText())) {
            return PsiReference.EMPTY_ARRAY;
        }

        if (F9SyntaxPattern.is1StParamOfSupportActionCall(element)) {
            return new PsiReference[]{new F9ActionReference(element,
                    new TextRange(1, element.getText().length() - 1), false)};
        }
        else if (F9SyntaxPattern.is1StParamOfSupportJsMethodCall(element)
                || F9SyntaxPattern.isSupportedAttributeValueOfHtmlTag(element)) {
            return createMethodReferenceByElement(element);
        }
        else {
            return PsiReference.EMPTY_ARRAY;
        }
    }


    private static PsiReference[] createMethodReferenceByElement(PsiElement element) {
        if (element.getText().contains(PERIODOPERATOR)) {
            if (StringUtil.countPeriodOperator(element.getText()) == 1) {
                String annotationValue = element.getText().substring(1, element.getText().indexOf(PERIODOPERATOR));
                String methodName = element.getText().substring(element.getText().indexOf(PERIODOPERATOR) + 1, element.getText().length() - 1);
                TextRange methodTextRange = new TextRange(element.getText().indexOf(PERIODOPERATOR) + 1, element.getText().length() - 1);
                return new PsiReference[]{new F9MethodReference(element, methodTextRange, annotationValue, methodName),
                        new F9ActionReference(element, new TextRange(1, element.getText().indexOf(PERIODOPERATOR)), false)};
            }
            else {
                return new PsiReference[]{new F9ActionMethodNullReference(element, new TextRange(1, element.getText().length() - 1))};
            }
        }
        else {
            return new PsiReference[]{new F9MethodReference(element, new TextRange(1, element.getText().length() - 1)),
                    new F9ActionReference(element, new TextRange(1, element.getText().length() - 1), true)};
        }
    }

    private static boolean notEmpty(@NotNull String text) {
        return !"''".equals(text) && !"\"\"".equals(text);
    }
}
