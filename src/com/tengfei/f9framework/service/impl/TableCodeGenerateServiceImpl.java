package com.tengfei.f9framework.service.impl;

import com.intellij.openapi.project.Project;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.service.TableCodeGenerateService;

public class TableCodeGenerateServiceImpl implements TableCodeGenerateService {
    private final Project project;

    public TableCodeGenerateServiceImpl(Project project) {
        this.project = project;
    }

    @Override
    public void generateEntityByTableInfoAndPathConfig(TableInfo tableInfo, PathConfig pathConfig) {
        new EntityCodeService(project).generateEntityByTableInfoAndPathConfig(tableInfo,pathConfig);
    }

    @Override
    public void generateServiceByTableInfoAndPathConfig(TableInfo tableInfo, PathConfig pathConfig) {
        //TODO INSERT YOUR CODE
        //生成serviceinterface
        ServiceCodeBase.getInstance(project,tableInfo,pathConfig,ServiceCodeBase.SERVICE_INTERFACE).generateCode();
        //生成serviceimpl
        ServiceCodeBase.getInstance(project,tableInfo,pathConfig,ServiceCodeBase.SERVICE_IMPL).generateCode();
        //生成service
        ServiceCodeBase.getInstance(project,tableInfo,pathConfig,ServiceCodeBase.SERVICE).generateCode();
    }


}
