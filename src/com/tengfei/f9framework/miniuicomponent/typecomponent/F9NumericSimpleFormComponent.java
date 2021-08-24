package com.tengfei.f9framework.miniuicomponent.typecomponent;

import com.tengfei.f9framework.entity.ColumnInfo;

public class F9NumericSimpleFormComponent extends F9SimpleFormComponentBase {

    F9NumericSimpleFormComponent(ColumnInfo columnInfo) {
        super(columnInfo);
    }

    @Override
    public String createSearchComponent() {
        String numericTemplate = "<input id=\"search_%s_number\" class=\"mini-textbox\" bind=\"dataBean.%s\"/>";
        return String.format(numericTemplate, columnInfo.getNameWithLowerCase(), columnInfo.getNameWithLowerCase());
    }

    @Override
    public String createEditComponent() {
        String numericTemplate = " <input id=\"%s\" class=\"mini-textbox\" bind=\"dataBean.%s\" required=\"true\"\n" +
                "                           requiredErrorText=\"%s不能为空!\"  vType=\"float\"/>";
        return String.format(numericTemplate, columnInfo.getNameWithLowerCase(), columnInfo.getNameWithLowerCase(),columnInfo.getComment());
    }

    @Override
    public String createDetailComponent() {
        return super.createDetailComponent();
    }
}
