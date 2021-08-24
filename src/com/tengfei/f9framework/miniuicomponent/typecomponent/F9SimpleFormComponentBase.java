package com.tengfei.f9framework.miniuicomponent.typecomponent;

import com.tengfei.f9framework.entity.ColumnInfo;

public class F9SimpleFormComponentBase {

    protected ColumnInfo columnInfo;

    protected F9SimpleFormComponentBase(ColumnInfo columnInfo) {
        this.columnInfo = columnInfo;
    }

    public String createSearchComponent() {
        String template = "<input id=\"search_%s\" class=\"mini-textbox\" bind=\"dataBean.%s\" />";
        return String.format(template, columnInfo.getNameWithLowerCase(), columnInfo.getNameWithLowerCase());
    }

    public String createEditComponent() {
        String template = "<input id=\"%s\" class=\"mini-textbox\" bind=\"dataBean.%s\" required=\"true\"\n" +
                "                           requiredErrorText=\"%s不能为空!\"/>";
        return String.format(template, columnInfo.getNameWithLowerCase(), columnInfo.getNameWithLowerCase(), columnInfo.getComment());
    }

    public String createDetailComponent() {
        String template = "<div class=\"mini-outputtext\" bind=\"dataBean.%s\"></div>";
        return String.format(template, columnInfo.getNameWithLowerCase());
    }
}
