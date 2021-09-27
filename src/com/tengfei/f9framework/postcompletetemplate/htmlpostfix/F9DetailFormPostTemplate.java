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
public class F9DetailFormPostTemplate extends F9PostfixTemplateWithTable {
    protected F9DetailFormPostTemplate() {
        super("detailform", "详情表单");
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
               if ("java.util.Date".equals(columnType)) {
                    template = String.format(" <div role=\"control\" label=\"%s\">\n" +
                            "                    <div class=\"mini-outputtext\" bind=\"dataBean.%s\"\n" +
                            "                         data-options=\"{format:'yyyy-MM-dd HH:mm:ss'}\"></div>\n" +
                            "                </div>",columnComment,columnName);
                } else {
                    template = String.format("<div role=\"control\" label=\"%s\">\n" +
                            "                    <div class=\"mini-outputtext\" bind=\"dataBean.%s\"></div>\n" +
                            "                </div>",columnComment,columnName);
                }
                result.append(template);
            }
            result.append("</div>");
        }

        return result.toString();
    }
}
