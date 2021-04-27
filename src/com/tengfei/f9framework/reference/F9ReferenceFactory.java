package com.tengfei.f9framework.reference;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.tengfei.f9framework.sytaxpattern.F9SyntaxPattern;
import com.tengfei.f9framework.util.StringUtil;

/**
 * @author ztf
 */
public class F9ReferenceFactory {

    public static final String PERIODOPERATOR = ".";

    public static PsiReference[] createReferenceByElement(PsiElement element) {

        if (F9SyntaxPattern.is1StParamOfSupportJsActionCall(element)) {
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
                return new PsiReference[]{new F9MethodReference(element, new TextRange(element.getText().indexOf(PERIODOPERATOR) + 1, element.getText().length() - 1), element.getText().split("\\.")[0], element.getText().split("\\.")[1]),
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
}
