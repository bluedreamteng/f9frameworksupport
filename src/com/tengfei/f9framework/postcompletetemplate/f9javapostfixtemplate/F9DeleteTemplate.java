package com.tengfei.f9framework.postcompletetemplate.f9javapostfixtemplate;


import com.intellij.psi.PsiElement;


/**
 * @author ztf
 */
public class F9DeleteTemplate extends F9JavaPostfixTemplateBase {
    public F9DeleteTemplate() {
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
