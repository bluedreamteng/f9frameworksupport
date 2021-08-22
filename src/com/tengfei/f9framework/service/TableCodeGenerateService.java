package com.tengfei.f9framework.service;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
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
    void generateEntityFile(TableInfo tableInfo, PathConfig pathConfig);

    /**
     * 构造service类文件
     * @param tableInfo 表信息
     * @param pathConfig 路径配置
     */
    void generateServiceFile(TableInfo tableInfo, PathConfig pathConfig);


    /**
     * 构造Add action文件
     * @param tableInfo 表信息
     */
    void generateAddActionFile(TableInfo tableInfo, PathConfig pathConfig);

    /**
     * 构造Edit action文件
     * @param tableInfo 表信息
     */
    void generateEditActionFile(TableInfo tableInfo, PathConfig pathConfig);

    /**
     * 构造List action文件
     * @param tableInfo 表信息
     */
    void generateListActionFile(TableInfo tableInfo, PathConfig pathConfig);

    /**
     * 构造Detail action文件
     * @param tableInfo 表信息
     */
    void generateDetailActionFile(TableInfo tableInfo, PathConfig pathConfig);

    /**
     * 构造 add html 文件
     * @param tableInfo 表信息
     * @param directory 目录
     */
    void generateAddHtmlFile(TableInfo tableInfo, VirtualFile directory);


    /**
     * 构造 list html 文件
     * @param tableInfo 表信息
     * @param directory 目录
     */
    void generateListHtmlFile(TableInfo tableInfo, VirtualFile directory);

    /**
     * 构造 edit html 文件
     * @param tableInfo 表信息
     * @param directory 目录
     */
    void generateEditHtmlFile(TableInfo tableInfo, VirtualFile directory);

    /**
     * 构造 detail html 文件
     * @param tableInfo 表信息
     * @param directory 目录
     */
    void generateDetailHtmlFile(TableInfo tableInfo, VirtualFile directory);

}
