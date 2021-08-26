package com.tengfei.f9framework.postcompletetemplate.f9htmlpostfixtemplate;

import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;

public class F9TestHtmlTemplate extends F9HtmlPostfixTemplateBase {
    public F9TestHtmlTemplate(PostfixTemplateProvider provider) {
        super("deleteselect", "delete Selected items", provider);
    }

    @Override
    public String buildStringTemplate(PsiElement context) {
        String format = "<input class=\"mini-combobox\" action=\"get%sModel\" id=\"%s\"\n" +
                "                           bind=\"dataBean.%s\" textField=\"text\" valueField=\"id\" emptyText=\"请选择...\"\n" +
                "                           required=\"true\" requiredErrorText=\"$end$不能为空!\"/></div>";
        return String.format(format, StringUtil.capitalize(context.getText()),context.getText(),context.getText());
    }

}
