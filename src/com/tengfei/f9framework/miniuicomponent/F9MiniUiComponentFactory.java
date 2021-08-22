package com.tengfei.f9framework.miniuicomponent;

import com.tengfei.f9framework.entity.ColumnInfo;

import java.util.List;

/**
 * 构造mini ui组件
 *
 * @author ztf
 */
public class F9MiniUiComponentFactory {

    private final F9FormComponentStrategy formComponentStrategy;

    public static F9MiniUiComponentFactory getInstance(F9FormComponentStrategy formComponentStrategy) {
        return new F9MiniUiComponentFactory(formComponentStrategy);
    }

    public static F9MiniUiComponentFactory getInstance() {
        return new F9MiniUiComponentFactory(F9FormComponentStrategy.EDIT_FORM);
    }

    private F9MiniUiComponentFactory(F9FormComponentStrategy formComponentStrategy) {
        this.formComponentStrategy = formComponentStrategy;
    }

    public String createDivRows(List<List<ColumnInfo>> columnListInfos) {
        StringBuilder divRowText = new StringBuilder();
        for (List<ColumnInfo> columnInfos : columnListInfos) {
            divRowText.append(createDivRow(columnInfos));
        }
        return divRowText.toString();
    }


    public String createDivRow(List<ColumnInfo> columnInfos) {
        String divRowText = "<div role=\"row\">\n" +
                "%s" +
                "            </div>";
        return String.format(divRowText, createDivControls(columnInfos));
    }

    public String createDivControls(List<ColumnInfo> columnInfos) {
        StringBuilder result = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            result.append(createDivControl(columnInfo));
        }
        return result.toString();
    }

    public String createDivControl(ColumnInfo columnInfo) {
        String divControlTemplate = " <div role=\"control\" label=\"%s\">\n" +
                "%s" + "</div>";
        return String.format(divControlTemplate, columnInfo.getComment(), formComponentStrategy.createFormComponent(columnInfo));
    }

    public String createDivFields(List<ColumnInfo> columnInfos) {
        StringBuilder result = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            result.append(createDivField(columnInfo));
        }
        return result.toString();
    }

    public String createDivField(ColumnInfo columnInfo) {
        if ("java.util.Date".equals(columnInfo.getType())) {
            return String.format(" <div field=\"%s\" data-options=\"{'format':'yyyy-MM-dd HH:mm:ss'}\" dateFormat=\"yyyy-MM-dd HH:mm:ss\">\n" +
                    "                %s\n" +
                    "            </div>", columnInfo.getNameWithLowerCase(), columnInfo.getComment());
        } else {
            return String.format("<div field=\"%s\">\n" +
                    "                %s\n" +
                    "            </div>", columnInfo.getNameWithLowerCase(), columnInfo.getComment());
        }
    }


}
