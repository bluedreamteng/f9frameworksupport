package com.tengfei.f9framework.service.impl;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.util.PackageUtil;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.tengfei.f9framework.entity.ColumnInfo;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.service.TableCodeGenerateService;
import com.tengfei.f9framework.util.NameUtils;
import org.jetbrains.jps.model.java.JavaSourceRootType;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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


    }


}
