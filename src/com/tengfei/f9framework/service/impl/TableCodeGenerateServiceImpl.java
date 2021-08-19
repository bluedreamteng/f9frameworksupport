package com.tengfei.f9framework.service.impl;

import com.intellij.openapi.project.Project;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.service.TableCodeGenerateService;

/**
 * @author ztf
 */
public class TableCodeGenerateServiceImpl implements TableCodeGenerateService {
    private final Project project;

    public TableCodeGenerateServiceImpl(Project project) {
        this.project = project;
    }

    @Override
    public void generateEntity(TableInfo tableInfo, PathConfig pathConfig) {
        new EntityCodeService(project).generateEntityByTableInfoAndPathConfig(tableInfo,pathConfig);
    }

    @Override
    public void generateService(TableInfo tableInfo, PathConfig pathConfig) {
        //TODO INSERT YOUR CODE
        //生成serviceinterface
        ServiceCodeServiceBase.getInstance(project,tableInfo,pathConfig, ServiceCodeServiceBase.SERVICE_INTERFACE).generateCode();
        //生成serviceimpl
        ServiceCodeServiceBase.getInstance(project,tableInfo,pathConfig, ServiceCodeServiceBase.SERVICE_IMPL).generateCode();
        //生成service
        ServiceCodeServiceBase.getInstance(project,tableInfo,pathConfig, ServiceCodeServiceBase.SERVICE).generateCode();
    }

    /**
     * 构造Add action文件
     *
     * @param tableInfo
     */
    @Override
    public void generateAddAction(TableInfo tableInfo,PathConfig pathConfig) {
        ActionCodeServiceBase.getInstance(project,tableInfo,pathConfig,ActionCodeServiceBase.ADD_ACTION).generateActionCode();
    }

    @Override
    public void generateEditAction(TableInfo tableInfo,PathConfig pathConfig) {
        ActionCodeServiceBase.getInstance(project,tableInfo,pathConfig,ActionCodeServiceBase.EDIT_ACTION).generateActionCode();

    }

    @Override
    public void generateListAction(TableInfo tableInfo,PathConfig pathConfig) {
        ActionCodeServiceBase.getInstance(project,tableInfo,pathConfig,ActionCodeServiceBase.LIST_ACTION).generateActionCode();

    }

    @Override
    public void generateDetailAction(TableInfo tableInfo, PathConfig pathConfig) {
        ActionCodeServiceBase.getInstance(project,tableInfo,pathConfig,ActionCodeServiceBase.DETAIL_ACTION).generateActionCode();

    }

}
