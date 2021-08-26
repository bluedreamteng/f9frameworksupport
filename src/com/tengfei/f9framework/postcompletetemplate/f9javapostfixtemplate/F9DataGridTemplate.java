package com.tengfei.f9framework.postcompletetemplate.f9javapostfixtemplate;

import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.psi.PsiElement;

/**
 * getDataGridData 方法模板
 * @author ztf
 */
public class F9DataGridTemplate extends F9JavaPostfixTemplateBase {


    public F9DataGridTemplate( PostfixTemplateProvider provider) {
        super("datagrid", "getDataGridData",provider);
    }

    @Override
    public String buildStringTemplate(PsiElement context) {
        String expressionName = context.getText();
        String format = "public com.epoint.core.dto.model.DataGridModel<%s> getDataGridData() {\n" +
                "        // 获得表格对象\n" +
                "        if (model == null) {\n" +
                "            model = new com.epoint.core.dto.model.DataGridModel<%s>() {\n" +
                "\n" +
                "                @Override\n" +
                "                public java.util.List<%s> fetchData(int first, int pageSize, String sortField, String sortOrder) {\n" +
                "                    // 获取where条件Map集合\n" +
                "                    java.util.Map<String, Object> conditionMap = com.epoint.frame.service.metadata.mis.util.ListGenerator.getSearchMap(getRequestContext().getComponents(),\n" +
                "                            sortField, sortOrder);\n" +
                "                    com.epoint.database.peisistence.crud.impl.model.PageData<%s> pageData = service.paginatorList(conditionMap, first, pageSize);\n" +
                "                    this.setRowCount(pageData.getRowCount());\n" +
                "                    return pageData.getList();\n" +
                "                }\n" +
                "\n" +
                "            };\n" +
                "        }\n" +
                "        return model;\n" +
                "    }";
        return String.format(format,expressionName,expressionName,expressionName,expressionName);
    }
}
