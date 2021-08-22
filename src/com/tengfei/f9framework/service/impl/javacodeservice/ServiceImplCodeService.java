package com.tengfei.f9framework.service.impl.javacodeservice;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;

/**
 * @author ztf
 */
public class ServiceImplCodeService extends ServiceCodeServiceBase {

    private Project project;

    public ServiceImplCodeService(Project project, TableInfo tableInfo, PathConfig pathConfig) {
        super(project, tableInfo, pathConfig);
        this.project = project;
        javaPsiFacade = JavaPsiFacade.getInstance(project);
    }

    @Override
    PsiClass createClass() {
        PsiClass interfaceClass = javaPsiFacade.findClass(tableInfo.getServiceInterfaceName(), GlobalSearchScope.allScope(project));
        PsiClass result = elementFactory.createClass(tableInfo.getServiceImplName());
        if(interfaceClass != null) {
            result.getImplementsList().add(elementFactory.createClassReferenceElement(interfaceClass));
        }
        //加入注解
        result.getModifierList().addAnnotation("org.springframework.stereotype.Component");
        return result;
    }

    /**
     * 插入数据
     *
     * @param context 方法上下文
     * @return 插入数据方法的方法
     */
    @Override
    public PsiMethod createInsertMethod(PsiClass context) {
        String methodText = String.format("public int insert(%s record) {" +
                        "return new %s().insert(record);" +
                        "}",
                tableInfo.getEntityName(), tableInfo.getServiceName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    /**
     * 删除数据
     *
     * @param context 方法上下文
     * @return 删除数据的方法
     */
    @Override
    public PsiMethod createDeleteMethod(PsiClass context) {
        String methodText = String.format("public int deleteByGuid(String guid) {" +
                "return new %s().deleteByGuid(guid);" +
                "}", tableInfo.getServiceName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    /**
     * 更新数据
     *
     * @param context 方法上下文
     * @return 更新数据的方法
     */
    @Override
    public PsiMethod createUpdateMethod(PsiClass context) {
        String methodText = String.format("public int update(%s record) {" +
                "return new %s().update(record);" +
                "}", tableInfo.getEntityName(), tableInfo.getServiceName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    /**
     * 查询数量
     *
     * @param context 方法上下文
     * @return 查询数量的方法
     */
    @Override
    public PsiMethod createCountWithConditionMethod(PsiClass context) {
        String methodText = String.format("public Integer count%s(java.util.Map<String, Object> conditionMap) {" +
                "return new %s().count%s(conditionMap);" +
                "}", tableInfo.getEntityName(), tableInfo.getServiceName(), tableInfo.getEntityName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    /**
     * 根据ID查找单个实体
     *
     * @param context 方法上下文
     * @return 根据ID查找单个实体的方法
     */
    @Override
    public PsiMethod createFindByPrimaryKeyMethod(PsiClass context) {
        String methodText = String.format("public %s find(Object primaryKey) {" +
                "return new %s().find(primaryKey);" +
                "}", tableInfo.getEntityName(), tableInfo.getServiceName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    /**
     * 查找单条记录
     *
     * @param context 方法上下文
     * @return 查找单条记录的方法
     */
    @Override
    public PsiMethod createFindWithConditionMethod(PsiClass context) {
        String methodText = String.format("public %s find(java.util.Map<String, Object> conditionMap) {" +
                "return new %s().find(conditionMap);" +
                "}", tableInfo.getEntityName(), tableInfo.getServiceName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    /**
     * 查找一个list
     *
     * @param context 方法上下文
     * @return 查找一个list的方法
     */
    @Override
    public PsiMethod createFindListWithConditionMethod(PsiClass context) {
        String methodText = String.format("public java.util.List<%s> findList(java.util.Map<String, Object> conditionMap) {" +
                "return new %s().findList(conditionMap);" +
                "}",tableInfo.getEntityName(),tableInfo.getServiceName());
        return elementFactory.createMethodFromText(methodText, context);
    }

    /**
     * 分页查找一个list
     *
     * @param context 方法上下文
     * @return 分页查找一个list的方法
     */
    @Override
    public PsiMethod createPaginatorListMethod(PsiClass context) {
        String methodText = String.format("public com.epoint.database.peisistence.crud.impl.model.PageData<%s> paginatorList(java.util.Map<String, Object> conditionMap, int pageNumber, int pageSize) {" +
                "return new %s().paginatorList(conditionMap, pageNumber, pageSize);" +
                "}",tableInfo.getEntityName(),tableInfo.getServiceName());
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
        return pathConfig.getServiceImplPackageName();
    }
}
