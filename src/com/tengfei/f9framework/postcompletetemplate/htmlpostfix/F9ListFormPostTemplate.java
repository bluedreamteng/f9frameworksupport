package com.tengfei.f9framework.postcompletetemplate.htmlpostfix;

import com.tengfei.f9framework.entity.ColumnInfo;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.postcompletetemplate.F9PostfixTemplateWithTable;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class F9ListFormPostTemplate extends F9PostfixTemplateWithTable {
    protected F9ListFormPostTemplate() {
        super("listform", "f9列表");
    }

    @NotNull
    @Override
    public String getTemplate(@NotNull TableInfo tableInfo) {
        StringBuilder result = new StringBuilder();
        String pk = tableInfo.getPkColumn().isEmpty()?"":tableInfo.getPkColumn().get(0).getName();
        result.append(String.format("<div id=\"datagrid\" class=\"mini-datagrid\" idField=\"%s\" action=\"getDataGridData\" sortOrder=\"desc\"\n" +
                "         showPager=\"true\" style=\"height: 100%%;\" allowResize=\"true\" multiSelect=\"true\" allowCellEdit=\"true\"\n" +
                "         allowCellSelect=\"true\" editNextOnEnterKey=\"true\" editNextRowCell=\"true\">\n" +
                "        <div property=\"columns\">\n" +
                "            <div type=\"checkcolumn\" width=\"40\"></div>\n" +
                "            <div type=\"indexcolumn\" width=\"40\" headerAlign=\"center\">序</div>",pk));

        for (ColumnInfo columnInfo : tableInfo.getOtherColumn()) {
            String columnType = columnInfo.getType();
            String columnComment = "".equals(columnInfo.getComment()) ? "":columnInfo.getComment();
            String columnName = columnInfo.getNameWithLowerCase();
            String template;
            if ("java.util.Date".equals(columnType)) {
                template = String.format("<div field=\"%s\" data-options=\"{'format':'yyyy-MM-dd HH:mm:ss'}\" dateFormat=\"yyyy-MM-dd HH:mm:ss\">\n" +
                        "                %s\n" +
                        "            </div>",columnName,columnComment);
            } else {
                template = String.format("<div field=\"%s\">\n" +
                        "                %s\n" +
                        "            </div>",columnName,columnComment);
            }
            result.append(template);
        }

        result.append(" <div width=\"50\" align=\"center\" headerAlign=\"center\" renderer=\"onEditRenderer\">\n" +
                "                修改\n" +
                "            </div>\n" +
                "            <div width=\"50\" align=\"center\" headerAlign=\"center\" renderer=\"onDetailRenderer\">\n" +
                "                查看\n" +
                "            </div> </div>\n" +
                "    </div>");

        return result.toString();
    }
}
