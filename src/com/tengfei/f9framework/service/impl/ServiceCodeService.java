package com.tengfei.f9framework.service.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;

/**
 * @author ztf
 */
public class ServiceCodeService extends ServiceCodeBase {

     ServiceCodeService(Project project, TableInfo tableInfo, PathConfig pathConfig) {
        super(project, tableInfo, pathConfig);
    }

    @Override
    PsiClass createClass() {
        PsiClass aClass = elementFactory.createClass(tableInfo.getServiceName());
        PsiCodeBlock codeBlockFromText = elementFactory.createCodeBlockFromText(
                "{" +
                        "baseDao = com.epoint.core.dao.CommonDao.getInstance();" +
                        "}", null);
        PsiField field = elementFactory.createFieldFromText("protected com.epoint.core.dao.ICommonDao baseDao;", aClass);
        aClass.add(field);
        PsiMethod constructor = elementFactory.createConstructor(aClass.getName(), null);
        constructor.getModifierList().add(elementFactory.createKeyword("public"));
        constructor.getBody().replace(codeBlockFromText);
        aClass.add(constructor);
        return aClass;
    }

    /**
     * 插入数据
     *
     * @param context 方法上下文
     * @return 插入数据方法的方法
     */
    @Override
    PsiMethod createInsertMethod(PsiClass context) {
        String methodText = String.format("public int insert(%s record) {" +
                "return baseDao.insert(record);" +
                "}", tableInfo.getEntityName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    /**
     * 删除数据
     *
     * @param context 方法上下文
     * @return 删除数据的方法
     */
    @Override
    PsiMethod createDeleteMethod(PsiClass context) {
        String methodText = String.format("public <T extends com.epoint.core.grammar.Record> int deleteByGuid(String guid) {" +
                "T t = baseDao.find(%s.class, guid);" +
                "return baseDao.delete(t);" +
                "}", tableInfo.getEntityName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    /**
     * 更新数据
     *
     * @param context 方法上下文
     * @return 更新数据的方法
     */
    @Override
    PsiMethod createUpdateMethod(PsiClass context) {
        String methodText = String.format("public int update(%s record) {" +
                "return baseDao.update(record);" +
                "}", tableInfo.getEntityName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    /**
     * 查询数量
     *
     * @param context 方法上下文
     * @return 查询数量的方法
     */
    @Override
    PsiMethod createCountWithConditionMethod(PsiClass context) {
        String methodText = String.format("public Integer count%s(java.util.Map<String, Object> conditionMap) {" +
                "com.epoint.core.utils.sql.SqlConditionUtil conditionUtil = new SqlConditionUtil(conditionMap);" +
                "conditionUtil.setSelectFields(\"count(*)\");" +
                "List<Object> params = new java.util.ArrayList<>();" +
                "return baseDao.queryInt(new com.epoint.core.utils.sql.SqlHelper().getSqlComplete(%s.class, conditionUtil.getMap(), params)," +
                "params.toArray());" +
                "}", tableInfo.getEntityName(), tableInfo.getEntityName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    /**
     * 根据ID查找单个实体
     *
     * @param context 方法上下文
     * @return 根据ID查找单个实体的方法
     */
    @Override
    PsiMethod createFindByPrimaryKeyMethod(PsiClass context) {
        String methodText = String.format("public %s find(Object primaryKey) {" +
                "return baseDao.find(%s.class, primaryKey);" +
                "}", tableInfo.getEntityName(), tableInfo.getEntityName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    /**
     * 查找单条记录
     *
     * @param context 方法上下文
     * @return 查找单条记录的方法
     */
    @Override
    PsiMethod createFindWithConditionMethod(PsiClass context) {
        String methodText = String.format("public %s find(Map<String, Object> conditionMap) {" +
                "java.util.List<Object> params = new java.util.ArrayList<>();" +
                "return baseDao.find(new com.epoint.core.utils.sql.SqlHelper().getSqlComplete(%s.class, conditionMap, params), %s.class," +
                "params.toArray());" +
                "}", tableInfo.getEntityName(), tableInfo.getEntityName(), tableInfo.getEntityName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    /**
     * 查找一个list
     *
     * @param context 方法上下文
     * @return 查找一个list的方法
     */
    @Override
    PsiMethod createFindListWithConditionMethod(PsiClass context) {
        String methodText = String.format("public List<%s> findList(Map<String, Object> conditionMap) {" +
                "java.util.List<Object> params = new java.util.ArrayList<>();" +
                "return baseDao.findList(new com.epoint.core.utils.sql.SqlHelper().getSqlComplete(%s.class, conditionMap, params), %s.class," +
                "params.toArray());" +
                "}", tableInfo.getEntityName(), tableInfo.getEntityName(), tableInfo.getEntityName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    /**
     * 分页查找一个list
     *
     * @param context 方法上下文
     * @return 分页查找一个list的方法
     */
    @Override
    PsiMethod createPaginatorListMethod(PsiClass context) {
        String methodText = String.format("public com.epoint.database.peisistence.crud.impl.model.PageData<%s> paginatorList(Map<String, Object> conditionMap, int pageNumber, int pageSize) {" +
                "List<Object> params = new java.util.ArrayList<>();" +
                "List<%s> list = baseDao.findList(new com.epoint.core.utils.sql.SqlHelper().getSqlComplete(%s.class, conditionMap, params), pageNumber," +
                "pageSize, %s.class, params.toArray());" +
                "int count = count%s(conditionMap);" +
                "com.epoint.database.peisistence.crud.impl.model.PageData<%s> pageData = new com.epoint.database.peisistence.crud.impl.model.PageData<%s>(list, count);" +
                "return pageData;" +
                "}", tableInfo.getEntityName(), tableInfo.getEntityName(), tableInfo.getEntityName(), tableInfo.getEntityName(), tableInfo.getEntityName(), tableInfo.getEntityName(), tableInfo.getEntityName());
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
        return pathConfig.getServicePackageName();
    }
}
