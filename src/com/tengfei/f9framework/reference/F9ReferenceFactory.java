package com.tengfei.f9framework.reference;

import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.tengfei.f9framework.sytaxpattern.F9SyntaxPattern;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class F9ReferenceFactory {


    @NotNull
    public static PsiReference[] createReferenceByElement(PsiElement element) {

        if (empty(element.getText())) {
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


    @NotNull
    private static PsiReference[] createMethodReferenceByElement(PsiElement element) {
        assert notEmpty(element.getText());
        if (!element.getText().contains(".")) {
            return new PsiReference[]{new F9MethodReference(element, new TextRange(1, element.getText().length() - 1))};
        }
        else if (StringUtil.countChars(element.getText(), '.') == 1) {
            String controllerAnnotationValue = element.getText().substring(1, element.getText().indexOf('.'));
            String methodName = element.getText().substring(element.getText().indexOf('.') + 1, element.getText().length() - 1);
            TextRange methodTextRange = new TextRange(element.getText().indexOf('.') + 1, element.getText().length() - 1);
            return new PsiReference[]{new F9MethodReference(element, methodTextRange, controllerAnnotationValue, methodName),
                    new F9ActionReference(element, new TextRange(1, element.getText().indexOf('.')), false)};
        }
        else {
            return new PsiReference[]{new F9NullReference(element, new TextRange(1, element.getText().length() - 1))};
        }
    }


    private static boolean notEmpty(@NotNull String text) {
        return StringUtil.isNotEmpty(text) && !"''".equals(text) && !"\"\"".equals(text);
    }


    private static boolean empty(@NotNull String text) {
        return !notEmpty(text);
    }
}
