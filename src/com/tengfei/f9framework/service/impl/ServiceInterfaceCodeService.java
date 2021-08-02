package com.tengfei.f9framework.service.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;

/**
 * @author ztf
 */
public class ServiceInterfaceCodeService extends ServiceCodeBase {

    public ServiceInterfaceCodeService(Project project, TableInfo tableInfo, PathConfig pathConfig) {
        super(project,tableInfo,pathConfig);
    }

    @Override
    PsiClass createClass() {
        return elementFactory.createInterface(tableInfo.getServiceInterfaceName());
    }

    @Override
    public PsiMethod createInsertMethod(PsiClass context) {
        String methodText = String.format("public int insert(%s record);", tableInfo.getName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    @Override
    public PsiMethod createDeleteMethod(PsiClass context) {
        String methodText = "public int deleteByGuid(String guid);";
        return elementFactory.createMethodFromText(methodText, context);
    }

    @Override
    public PsiMethod createUpdateMethod(PsiClass context) {
        String methodText = String.format("public int update(%s record);", tableInfo.getName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    @Override
    public PsiMethod createCountWithConditionMethod(PsiClass context) {
        String methodText = String.format("public Integer count%s(java.util.Map<String, Object> conditionMap);", tableInfo.getName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    @Override
    public PsiMethod createFindByPrimaryKeyMethod(PsiClass context) {
        String methodText = String.format("public %s find(Object primaryKey);", tableInfo.getName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    @Override
    public PsiMethod createFindWithConditionMethod(PsiClass context) {
        String methodText = String.format("public %s find(java.util.Map<String, Object> conditionMap);", tableInfo.getName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    @Override
    public PsiMethod createFindListWithConditionMethod(PsiClass context) {
        String methodText = String.format("public java.util.List<%s> findList(java.util.Map<String, Object> conditionMap);", tableInfo.getName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    @Override
    public PsiMethod createPaginatorListMethod(PsiClass context) {
        String methodText = String.format("public com.epoint.database.peisistence.crud.impl.model.PageData<%s> paginatorList(java.util.Map<String, Object> conditionMap, int pageNumber, int pageSize);", tableInfo.getName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    /**
     * 获取模块名称
     *
     * @return 模块名称
     */
    @Override
    String getModuleName() {
        return pathConfig.getModuleName();
    }

    /**
     * 获取包名称
     *
     * @return 包名称
     */
    @Override
    String getPackageName() {
        return pathConfig.getServiceInterfacePackageName();
    }


}
