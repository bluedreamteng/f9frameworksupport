package com.tengfei.f9framework.miniuicomponent;

import com.tengfei.f9framework.entity.ColumnInfo;

class F9SearchFormComponent implements F9FormComponentStrategy {

    @Override
    public String createFormComponent(ColumnInfo columnInfo) {
        switch (columnInfo.getType()) {
            case "java.lang.Integer":
                return createSearchIntegerTextBox(columnInfo);
            case "java.lang.Double":
                return createSearchDoubleTextBox(columnInfo);
            case "java.util.Date":
                return createSearchDatePicker(columnInfo);
            case "java.lang.String":
                return createSearchStringTextBox(columnInfo);
        }
        return createSearchStringTextBox(columnInfo);
    }

    private String createSearchStringTextBox(ColumnInfo columnInfo) {
        String stringTextBoxTemplate = "<input id=\"search_%s\" class=\"mini-textbox\" bind=\"dataBean.%s\" />";
        return String.format(stringTextBoxTemplate, columnInfo.getNameWithLowerCase(), columnInfo.getNameWithLowerCase());
    }

    private String createSearchIntegerTextBox(ColumnInfo columnInfo) {
        String integerTextBoxTemplate = "<input id=\"search_%s_integer\" class=\"mini-textbox\" bind=\"dataBean.%s\"/>";
        return String.format(integerTextBoxTemplate, columnInfo.getNameWithLowerCase(), columnInfo.getNameWithLowerCase());
    }

    private String createSearchDoubleTextBox(ColumnInfo columnInfo) {
        String doubleTextBoxTemplate = "<input id=\"search_%s_number\" class=\"mini-textbox\" bind=\"dataBean.%s\"/>";
        return String.format(doubleTextBoxTemplate, columnInfo.getNameWithLowerCase(), columnInfo.getNameWithLowerCase());
    }

    private String createSearchDatePicker(ColumnInfo columnInfo) {
        String datePickerTemplate = "<input id=\"search_%s\" class=\"mini-datepicker\" bind=\"dataBean.%s\"/>";
        return String.format(datePickerTemplate, columnInfo.getNameWithLowerCase(), columnInfo.getNameWithLowerCase());
    }
}
