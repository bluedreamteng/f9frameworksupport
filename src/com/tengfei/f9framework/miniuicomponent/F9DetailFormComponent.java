package com.tengfei.f9framework.miniuicomponent;

import com.tengfei.f9framework.entity.ColumnInfo;

class F9DetailFormComponent implements F9FormComponentStrategy {


    @Override
    public String createFormComponent(ColumnInfo columnInfo) {
        if ("java.util.Date".equals(columnInfo.getType())) {
            return crateDateOutputText(columnInfo);
        }
        return crateSimpleOutputText(columnInfo);
    }

    private String crateSimpleOutputText(ColumnInfo columnInfo) {
        String simpleOutputText = "<div class=\"mini-outputtext\" bind=\"dataBean.%s\"></div>";
        return String.format(simpleOutputText, columnInfo.getComment(), columnInfo.getNameWithLowerCase());
    }


    private String crateDateOutputText(ColumnInfo columnInfo) {
        String simpleOutputText = "<div class=\"mini-outputtext\" bind=\"dataBean.%s\"\n" +
                "                         data-options=\"{format:'yyyy-MM-dd HH:mm:ss'}\"></div>";
        return String.format(simpleOutputText, columnInfo.getComment(), columnInfo.getNameWithLowerCase());
    }
}
