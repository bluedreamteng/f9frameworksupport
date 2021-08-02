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

    /**
     * 构造实体类文件
     * @param tableInfo 表信息
     * @param pathConfig 路径配置
     */
    void generateEntityByTableInfoAndPathConfig(TableInfo tableInfo, PathConfig pathConfig);

    /**
     * 构造service类文件
     * @param tableInfo 表信息
     * @param pathConfig 路径配置
     */
    void generateServiceByTableInfoAndPathConfig(TableInfo tableInfo, PathConfig pathConfig);
}
