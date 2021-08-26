package com.tengfei.f9framework.postcompletetemplate.f9javapostfixtemplate;

import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;

/**
 * @author ztf
 */
public class F9ComboboxTemplate extends F9JavaPostfixTemplateBase{

    /**
     * @param provider 模板提供器
     */
    public F9ComboboxTemplate(PostfixTemplateProvider provider) {
        super("combobox", "下拉列表组件",provider);
    }

    /**
     * 构造模板
     *
     * @param context psielement
     * @return string 模板
     */
    @Override
    public String buildStringTemplate(PsiElement context) {
        String format = "public java.util.List<SelectItem> getCategoryModel() {\n" +
                "        if (%sModel == null) {\n" +
                "            model = DataUtil.convertMap2ComboBox((Map<String, String>>) CodeModalFactory.factory(\"下拉列表\", \"$end$\", null, false));\n" +
                "        }\n" +
                "        return this.%sModel;\n" +
                "    }";
        return String.format(format,context.getText(),context.getText(), StringUtil.capitalize(context.getText()));
    }
}
