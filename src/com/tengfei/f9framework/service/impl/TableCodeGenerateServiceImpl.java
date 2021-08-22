package com.tengfei.f9framework.service.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.service.TableCodeGenerateService;
import com.tengfei.f9framework.service.impl.htmlcodeservice.HtmlCodeServiceBase;
import com.tengfei.f9framework.service.impl.javacodeservice.ActionCodeServiceBase;
import com.tengfei.f9framework.service.impl.javacodeservice.EntityCodeService;
import com.tengfei.f9framework.service.impl.javacodeservice.ServiceCodeServiceBase;

/**
 * @author ztf
 */
public class TableCodeGenerateServiceImpl implements TableCodeGenerateService {
    private final Project project;

    public TableCodeGenerateServiceImpl(Project project) {
        this.project = project;
    }

    @Override
    public void generateEntityFile(TableInfo tableInfo, PathConfig pathConfig) {
        new EntityCodeService(project).generateEntityByTableInfoAndPathConfig(tableInfo, pathConfig);
    }

    @Override
    public void generateServiceFile(TableInfo tableInfo, PathConfig pathConfig) {
        //TODO INSERT YOUR CODE
        //生成serviceinterface
        ServiceCodeServiceBase.getInstance(project, tableInfo, pathConfig, ServiceCodeServiceBase.SERVICE_INTERFACE).generateCode();
        //生成serviceimpl
        ServiceCodeServiceBase.getInstance(project, tableInfo, pathConfig, ServiceCodeServiceBase.SERVICE_IMPL).generateCode();
        //生成service
        ServiceCodeServiceBase.getInstance(project, tableInfo, pathConfig, ServiceCodeServiceBase.SERVICE).generateCode();
    }

    @Override
    public void generateAddActionFile(TableInfo tableInfo, PathConfig pathConfig) {
        ActionCodeServiceBase.getInstance(project, tableInfo, pathConfig, ActionCodeServiceBase.ADD_ACTION).generateActionCode();
    }

    @Override
    public void generateEditActionFile(TableInfo tableInfo, PathConfig pathConfig) {
        ActionCodeServiceBase.getInstance(project, tableInfo, pathConfig, ActionCodeServiceBase.EDIT_ACTION).generateActionCode();

    }

    @Override
    public void generateListActionFile(TableInfo tableInfo, PathConfig pathConfig) {
        ActionCodeServiceBase.getInstance(project, tableInfo, pathConfig, ActionCodeServiceBase.LIST_ACTION).generateActionCode();

    }

    @Override
    public void generateDetailActionFile(TableInfo tableInfo, PathConfig pathConfig) {
        ActionCodeServiceBase.getInstance(project, tableInfo, pathConfig, ActionCodeServiceBase.DETAIL_ACTION).generateActionCode();

    }

    @Override
    public void generateAddHtmlFile(TableInfo tableInfo, VirtualFile directory) {
        //TODO
        HtmlCodeServiceBase.getInstance(project,tableInfo,directory,HtmlCodeServiceBase.ADD_HTML).generateHtmlCode();
    }

    @Override
    public void generateListHtmlFile(TableInfo tableInfo, VirtualFile directory) {
        //TODO
        HtmlCodeServiceBase.getInstance(project,tableInfo,directory,HtmlCodeServiceBase.LIST_HTML).generateHtmlCode();

    }

    @Override
    public void generateEditHtmlFile(TableInfo tableInfo, VirtualFile directory) {
        //TODO
        HtmlCodeServiceBase.getInstance(project,tableInfo,directory,HtmlCodeServiceBase.EDIT_HTML).generateHtmlCode();

    }

    @Override
    public void generateDetailHtmlFile(TableInfo tableInfo, VirtualFile directory) {
        //TODO
        HtmlCodeServiceBase.getInstance(project,tableInfo,directory,HtmlCodeServiceBase.Detail_HTML).generateHtmlCode();

    }

}
