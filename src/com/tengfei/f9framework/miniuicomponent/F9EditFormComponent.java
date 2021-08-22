package com.tengfei.f9framework.miniuicomponent;

import com.tengfei.f9framework.entity.ColumnInfo;

class F9EditFormComponent implements F9FormComponentStrategy {


    @Override
    public String createFormComponent(ColumnInfo columnInfo) {
        switch (columnInfo.getType()) {
            case "java.lang.Integer":
                return createIntegerTextBox(columnInfo);
            case "java.lang.Double":
                return createDoubleTextBox(columnInfo);
            case "java.util.Date":
                return createDatePicker(columnInfo);
            case "java.lang.String":
                return createStringTextBox(columnInfo);
        }
        return createStringTextBox(columnInfo);
    }

    private String createStringTextBox(ColumnInfo columnInfo) {
        String stringTextBoxTemplate = "<input id=\"%s\" class=\"mini-textbox\" bind=\"dataBean.%s\" required=\"true\"\n" +
                "                           requiredErrorText=\"%s不能为空!\"/>";
        return String.format(stringTextBoxTemplate, columnInfo.getNameWithLowerCase(), columnInfo.getNameWithLowerCase(), columnInfo.getComment());
    }

    private String createIntegerTextBox(ColumnInfo columnInfo) {
        String integerTextBoxTemplate = "<input id=\"%s\" class=\"mini-textbox\" bind=\"dataBean.%s\" required=\"true\"\n" +
                "                           requiredErrorText=\"整数类型不能为空!\" vType=\"int\"/>";
        return String.format(integerTextBoxTemplate, columnInfo.getNameWithLowerCase(), columnInfo.getNameWithLowerCase());
    }

    private String createDoubleTextBox(ColumnInfo columnInfo) {
        String doubleTextBoxTemplate = " <input id=\"%s\" class=\"mini-textbox\" bind=\"dataBean.%s\" required=\"true\"\n" +
                "                           requiredErrorText=\"数字类型不能为空!\"  vType=\"float\"/>";
        return String.format(doubleTextBoxTemplate, columnInfo.getNameWithLowerCase(), columnInfo.getNameWithLowerCase());
    }

    private String createDatePicker(ColumnInfo columnInfo) {
        String datePickerTemplate = " <input id=\"%s\" class=\"mini-datepicker\" bind=\"dataBean.%s\" required=\"true\"\n" +
                "                           requiredErrorText=\"日期类型不能为空!\"/>";
        return String.format(datePickerTemplate, columnInfo.getNameWithLowerCase(), columnInfo.getNameWithLowerCase());
    }
}
