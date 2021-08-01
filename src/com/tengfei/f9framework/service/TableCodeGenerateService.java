package com.tengfei.f9framework.service;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;
import org.jetbrains.annotations.NotNull;

/**
 * 数据表代码生成服务
 *
 * @author ZTF
 */
public interface TableCodeGenerateService {

    static TableCodeGenerateService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, TableCodeGenerateService.class);
    }

    void generateEntityByTableInfoAndPathConfig(TableInfo tableInfo, PathConfig pathConfig);

    void generateServiceByTableInfoAndPathConfig(TableInfo tableInfo, PathConfig pathConfig);
}
