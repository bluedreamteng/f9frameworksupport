package com.tengfei.f9framework.miniuicomponent.typecomponent;

import com.tengfei.f9framework.entity.ColumnInfo;

public class F9SimpleFormComponentFactory {

    private static F9SimpleFormComponentFactory INSTANCE;

    private F9SimpleFormComponentFactory() {
    }

    public static synchronized F9SimpleFormComponentFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new F9SimpleFormComponentFactory();
        }
        return INSTANCE;
    }

    public F9SimpleFormComponentBase createF9SimpleFormComponent(ColumnInfo columnInfo) {
        switch (columnInfo.getType()) {
            case "java.lang.Integer":
                return new F9IntegerSimpleFormComponent(columnInfo);
            case "java.lang.Double":
                return new F9NumericSimpleFormComponent(columnInfo);
            case "java.util.Date":
                return new F9DateSimpleFormComponent(columnInfo);
            default:
                return new F9SimpleFormComponentBase(columnInfo);
        }
    }


}
