package com.tengfei.f9framework.postcompletetemplate.f9htmlpostfixtemplate;

import com.intellij.psi.PsiElement;

public class F9TestHtmlTemplate extends F9HtmlPostfixTemplateBase{
    public F9TestHtmlTemplate() {
        super("deleteselect", "delete Selected items");
    }

    @Override
    public String buildStringTemplate(PsiElement context) {
        String format = " public void deleteSelect() {\n" +
                "        java.util.List<String> select = getDataGridData().getSelectKeys();\n" +
                "        for (String sel : select) {\n" +
                "             service.deleteByGuid(sel);\n" +
                "        }\n" +
                "        addCallbackParam(\"msg\", \"成功删除！\");\n" +
                "    }";
        return format;
    }

}
