package com.tengfei.f9framework.postcompletetemplate.f9javapostfixtemplate;

import com.intellij.psi.PsiElement;

/**
 * @author ztf
 */
public class F9ExportTemplate extends F9JavaPostfixTemplateBase {

    public F9ExportTemplate() {
        super("export", "getExportModel");
    }

    @Override
    public String buildStringTemplate(PsiElement context) {
        String format = "public com.epoint.basic.faces.export.ExportModel getExportModel() {\n" +
                "        if (exportModel == null) {\n" +
                "            exportModel = new ExportModel(\"\", \"\");\n" +
                "        }\n" +
                "        return exportModel;\n" +
                "    }";
        return format;
    }
}
