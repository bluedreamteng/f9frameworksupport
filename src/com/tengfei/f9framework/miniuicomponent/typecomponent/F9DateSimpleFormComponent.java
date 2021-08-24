package com.tengfei.f9framework.miniuicomponent.typecomponent;

import com.tengfei.f9framework.entity.ColumnInfo;

public class F9DateSimpleFormComponent extends F9SimpleFormComponentBase {

    F9DateSimpleFormComponent(ColumnInfo columnInfo) {
        super(columnInfo);
    }

    @Override
    public String createSearchComponent() {
        String datePickerTemplate = "<input id=\"search_%s\" class=\"mini-datepicker\" bind=\"dataBean.%s\"/>";
        return String.format(datePickerTemplate, columnInfo.getNameWithLowerCase(), columnInfo.getNameWithLowerCase());
    }

    @Override
    public String createEditComponent() {
        String datePickerTemplate = " <input id=\"%s\" class=\"mini-datepicker\" bind=\"dataBean.%s\" required=\"true\"\n" +
                "                           requiredErrorText=\"%s不能为空!\"/>";
        return String.format(datePickerTemplate, columnInfo.getNameWithLowerCase(), columnInfo.getNameWithLowerCase(),columnInfo.getComment());
    }

    @Override
    public String createDetailComponent() {
        String simpleOutputText = "<div class=\"mini-outputtext\" bind=\"dataBean.%s\"\n" +
                "                         data-options=\"{format:'yyyy-MM-dd HH:mm:ss'}\"></div>";
        return String.format(simpleOutputText, columnInfo.getNameWithLowerCase());
    }
}
