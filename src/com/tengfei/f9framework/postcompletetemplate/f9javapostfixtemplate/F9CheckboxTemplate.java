package com.tengfei.f9framework.postcompletetemplate.f9javapostfixtemplate;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class F9CheckboxTemplate extends F9JavaPostfixTemplateBase{
    /**
     * @param name    name of postfixTemplate
     * @param example 示例
     */
    protected F9CheckboxTemplate(@NotNull String name, @NotNull String example) {
        super("combobox", "下拉列表组件");
    }

    /**
     * 构造模板
     *
     * @param context psielement
     * @return string 模板
     */
    @Override
    public String buildStringTemplate(PsiElement context) {
        String format = "public java.util.List<com.epoint.core.dto.model.SelectItem> getCategoryModel() {\n" +
                "        if (model == null) {\n" +
                "            model = com.epoint.basic.faces.util.DataUtil.convertMap2ComboBox((java.util.List<java.util.Map<String, String>>) com.epoint.frame.service.metadata.mis.util.CodeModalFactory.factory(\"下拉列表\", \"项目类型\", null, false));\n" +
                "        }\n" +
                "        return this.model;\n" +
                "    }";
        return format;
    }
}
