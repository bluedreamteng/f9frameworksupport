package com.tengfei.f9framework.miniuicomponent;

import com.tengfei.f9framework.entity.ColumnInfo;
import com.tengfei.f9framework.miniuicomponent.typecomponent.F9SimpleFormComponentFactory;

/**
 * 表单组件创建策略
 */
public interface F9FormComponentStrategy {
    F9FormComponentStrategy EDIT_FORM = (columnInfo) -> F9SimpleFormComponentFactory.getInstance().createF9SimpleFormComponent(columnInfo).createEditComponent();
    F9FormComponentStrategy DETAIL_FORM = (columnInfo) -> F9SimpleFormComponentFactory.getInstance().createF9SimpleFormComponent(columnInfo).createDetailComponent();
    F9FormComponentStrategy SEARCH_FORM = (columnInfo) -> F9SimpleFormComponentFactory.getInstance().createF9SimpleFormComponent(columnInfo).createSearchComponent();

    String createFormComponent(ColumnInfo columnInfo);
}
