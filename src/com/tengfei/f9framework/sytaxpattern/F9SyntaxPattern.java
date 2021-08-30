package com.tengfei.f9framework.sytaxpattern;

import com.intellij.lang.javascript.psi.JSCallExpression;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;

/**
 * @author ztf
 */
public class F9SyntaxPattern {

    public static boolean is1StParamOfSupportActionCall(PsiElement element) {
        if (is1stParamOfJsMethodCall(element)) {
            JSCallExpression jsCallExpression = PsiTreeUtil.getParentOfType(element, JSCallExpression.class);
            assert jsCallExpression != null;
            for (String f9JsAction : F9JsMethod.getSupportedJsAction()) {
                if (jsCallExpression.getText().contains(f9JsAction)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean is1StParamOfSupportJsMethodCall(PsiElement element) {
        if (is1stParamOfJsMethodCall(element)) {
            JSCallExpression jsCallExpression = PsiTreeUtil.getParentOfType(element, JSCallExpression.class);
            assert jsCallExpression != null;
            for (String f9JsMethod : F9JsMethod.getSupportedJsMethod()) {
                if (jsCallExpression.getText().contains(f9JsMethod)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean is1stParamOfJsMethodCall(PsiElement element) {
        JSCallExpression jsCallExpression = PsiTreeUtil.getParentOfType(element, JSCallExpression.class);
        return jsCallExpression != null &&
                jsCallExpression.getArgumentList() != null &&
                jsCallExpression.getArgumentList().getArguments().length > 0 && element.equals(jsCallExpression.getArgumentList().getArguments()[0]);
    }

    public static boolean isSupportedAttributeValueOfHtmlTag(PsiElement element) {
        if (!(element.getParent() instanceof XmlAttributeValue)) {
            return false;
        }
        if (!(element.getParent().getParent() instanceof XmlAttribute)) {
            return false;
        }
        XmlAttribute xmlAttribute = (XmlAttribute) element.getParent().getParent();
        for (String f9xmlAttribute : F9HtmlTagAttribute.getSupportHtmlTagAttr()) {
            if (xmlAttribute.getText().equals(f9xmlAttribute)) {
                return true;
            }
        }
        return false;
    }

}
