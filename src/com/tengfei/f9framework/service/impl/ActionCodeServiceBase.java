package com.tengfei.f9framework.service.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElementFactory;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.util.F9JavaFileFacade;

/**
 * @author ztf
 */
public abstract class ActionCodeServiceBase {
    public static final String ADD_ACTION = "0";
    public static final String EDIT_ACTION = "1";
    public static final String LIST_ACTION = "2";
    public static final String DETAIL_ACTION = "3";


    private final Project project;
    PsiElementFactory elementFactory;
    JavaPsiFacade javaPsiFacade;

    protected final TableInfo tableInfo;
    protected final PathConfig pathConfig;

    public ActionCodeServiceBase(Project project,TableInfo tableInfo,PathConfig pathConfig) {
        this.project = project;
        elementFactory = PsiElementFactory.getInstance(project);
        javaPsiFacade = JavaPsiFacade.getInstance(project);
        this.tableInfo = tableInfo;
        this.pathConfig = pathConfig;
    }

    public static ActionCodeServiceBase getInstance(Project project, TableInfo tableInfo, PathConfig pathConfig, String type) {
        switch (type) {
            case ADD_ACTION:
                return new AddActionCodeService(project, tableInfo, pathConfig);
            case EDIT_ACTION:
                return new EditActionCodeService(project, tableInfo, pathConfig);
            case LIST_ACTION:
                return new ListActionCodeService(project, tableInfo, pathConfig);
            case DETAIL_ACTION:
                return new DetailActionCodeService(project, tableInfo, pathConfig);
            default:
                throw new RuntimeException("unsupported type");
        }
    }

    public void generateActionCode() {
        F9JavaFileFacade.getInstance(project).
                createJavaFile(pathConfig.getModuleName(),
                        pathConfig.getActionPackageName(),
                        getClassName(),buildActionTemplate());
    }

    protected abstract String getClassName();

    protected abstract String buildActionTemplate();
}
