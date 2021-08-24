package com.tengfei.f9framework.miniuicomponent.typecomponent;

import com.tengfei.f9framework.entity.ColumnInfo;

public class F9IntegerSimpleFormComponent extends F9SimpleFormComponentBase {

     F9IntegerSimpleFormComponent(ColumnInfo columnInfo) {
         super(columnInfo);
     }

    @Override
    public String createSearchComponent() {
        String integerTextBoxTemplate = "<input id=\"search_%s_integer\" class=\"mini-textbox\" bind=\"dataBean.%s\"/>";
        return String.format(integerTextBoxTemplate, columnInfo.getNameWithLowerCase(), columnInfo.getNameWithLowerCase());
    }

    @Override
    public String createEditComponent() {
        String integerTextBoxTemplate = "<input id=\"%s\" class=\"mini-textbox\" bind=\"dataBean.%s\" required=\"true\"\n" +
                "                           requiredErrorText=\"%s不能为空!\" vType=\"int\"/>";
        return String.format(integerTextBoxTemplate, columnInfo.getNameWithLowerCase(), columnInfo.getNameWithLowerCase(),columnInfo.getComment());
    }

    @Override
    public String createDetailComponent() {
        return super.createDetailComponent();
    }
}
