package com.tengfei.f9framework.postcompletetemplate.htmlpostfix;

import com.tengfei.f9framework.entity.ColumnInfo;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.postcompletetemplate.F9PostfixTemplateWithTable;
import com.tengfei.f9framework.util.CollectionUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author ztf
 */
public class F9EditFormPostTemplate extends F9PostfixTemplateWithTable {
    protected F9EditFormPostTemplate() {
        super("editform", "可编辑表单");
    }

    @NotNull
    @Override
    public String getTemplate(@NotNull TableInfo tableInfo) {
        StringBuilder result = new StringBuilder();
        List<ColumnInfo> otherColumn = tableInfo.getOtherColumn();
        List<List<ColumnInfo>> columnInfoLists = CollectionUtil.splitList(otherColumn, 2);
        for (List<ColumnInfo> columnInfoList : columnInfoLists) {
            result.append("<div role=\"row\">");
            for (ColumnInfo columnInfo : columnInfoList) {
                String columnType = columnInfo.getType();
                String columnComment = "".equals(columnInfo.getComment()) ? "":columnInfo.getComment();
                String columnName = columnInfo.getNameWithLowerCase();
                String template;
                if ("java.lang.Integer".equals(columnType)) {
                    template = String.format("<div role=\"control\" label=\"%s\" starred=\"true\">\n" +
                            "                    <input id=\"%s\" class=\"mini-textbox\" bind=\"dataBean.%s\" required=\"true\"\n" +
                            "                           requiredErrorText=\"%s不能为空!\" vType=\"int\"/></div>",columnComment,columnName,columnName,columnComment);
                }
                else if ("java.lang.Double".equals(columnType)) {
                    template = String.format("<div role=\"control\" label=\"%s\" starred=\"true\">\n" +
                            "                    <input id=\"%s\" class=\"mini-textbox\" bind=\"dataBean.%s\" required=\"true\"\n" +
                            "                           requiredErrorText=\"%s不能为空!\" vType=\"float\"/></div>",columnComment,columnName,columnName,columnComment);
                }
                else if ("java.util.Date".equals(columnType)) {
                    template = String.format("<div role=\"control\" label=\"%s\" starred=\"true\">\n" +
                            "                    <input id=\"%s\" class=\"mini-datepicker\" bind=\"dataBean.%s\" required=\"true\"\n" +
                            "                           requiredErrorText=\"%s不能为空!\"></div>",columnComment,columnName,columnName,columnComment);
                } else {
                    template = String.format("<div role=\"control\" label=\"%s\" starred=\"true\">\n" +
                            "                    <input id=\"%s\" class=\"mini-textbox\" bind=\"dataBean.%s\" required=\"true\"\n" +
                            "                           requiredErrorText=\"%s不能为空!\" ></div>",columnComment,columnName,columnName,columnComment);
                }
                result.append(template);
            }
            result.append("</div>");
        }

        return result.toString();
    }
}
