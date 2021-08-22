package com.tengfei.f9framework.miniuicomponent;

import com.tengfei.f9framework.entity.ColumnInfo;

/**
 * 表单组件创建策略
 */
public interface F9FormComponentStrategy {
    F9FormComponentStrategy EDIT_FORM = new F9EditFormComponent();
    F9FormComponentStrategy DETAIL_FORM = new F9DetailFormComponent();
    F9FormComponentStrategy SEARCH_FORM = new F9SearchFormComponent();

    String createFormComponent(ColumnInfo columnInfo);
}
