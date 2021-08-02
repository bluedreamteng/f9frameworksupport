package com.tengfei.f9framework.service.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.javadoc.PsiDocComment;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.util.GenerateTableCodeUtil;

/**
 * @author ztf
 */
public abstract class ServiceCodeBase {

    public static final String SERVICE_INTERFACE = "0";
    public static final String SERVICE_IMPL = "1";
    public static final String SERVICE = "2";

    protected final TableInfo tableInfo;
    protected final PathConfig pathConfig;

    protected PsiElementFactory elementFactory;
    protected JavaPsiFacade javaPsiFacade;

    public ServiceCodeBase(Project project, TableInfo tableInfo, PathConfig pathConfig) {
        this.tableInfo = tableInfo;
        this.pathConfig = pathConfig;
        elementFactory = PsiElementFactory.getInstance(project);
        javaPsiFacade = JavaPsiFacade.getInstance(project);
    }

    public static ServiceCodeBase getInstance(Project project, TableInfo tableInfo, PathConfig pathConfig, String type) {
        switch (type){
            case SERVICE_INTERFACE: return new ServiceInterfaceCodeService(project,tableInfo,pathConfig);
            case SERVICE_IMPL:return new ServiceImplCodeService(project, tableInfo, pathConfig);
            case SERVICE:return new ServiceCodeService(project,tableInfo,pathConfig);
            default:throw new RuntimeException("unsupported type");
        }
    }


    public PsiClass generateCode() {
        PsiClass result = createClass();
        PsiMethod insertMethod = createInsertMethod(result);
        PsiDocComment docCommentFromText = elementFactory.createDocCommentFromText("/**\n" +
                "     * 插入数据\n" +
                "     * \n" +
                "     * @param record BaseEntity或Record对象 <必须继承Record>\n" +
                "     * @return int\n" +
                "     */");
        insertMethod.addBefore(docCommentFromText,insertMethod.getFirstChild());
        result.add(insertMethod);
        result.add(createDeleteMethod(result));
        result.add(createUpdateMethod(result));
        result.add(createCountWithConditionMethod(result));
        result.add(createFindByPrimaryKeyMethod(result));
        result.add(createFindListWithConditionMethod(result));
        result.add(createPaginatorListMethod(result));
        GenerateTableCodeUtil.generateCode(getModuleName(), getPackageName(), result);
        return result;
    }

    abstract PsiClass createClass();

    /**
     * 插入数据
     * @param context 方法上下文
     * @return 插入数据方法的方法
     */
    abstract PsiMethod createInsertMethod(PsiClass context);

    /**
     * 删除数据
     * @param context 方法上下文
     * @return 删除数据的方法
     */
    abstract PsiMethod createDeleteMethod(PsiClass context);

    /**
     * 更新数据
     * @param context 方法上下文
     * @return 更新数据的方法
     */
    abstract PsiMethod createUpdateMethod(PsiClass context);

    /**
     * 查询数量
     * @param context 方法上下文
     * @return 查询数量的方法
     */
    abstract PsiMethod createCountWithConditionMethod(PsiClass context);

    /**
     * 根据ID查找单个实体
     * @param context 方法上下文
     * @return 根据ID查找单个实体的方法
     */
    abstract PsiMethod createFindByPrimaryKeyMethod(PsiClass context);

    /**
     * 查找单条记录
     * @param context 方法上下文
     * @return 查找单条记录的方法
     */
    abstract PsiMethod createFindWithConditionMethod(PsiClass context);

    /**
     * 查找一个list
     * @param context 方法上下文
     * @return 查找一个list的方法
     */
    abstract PsiMethod createFindListWithConditionMethod(PsiClass context);

    /**
     * 分页查找一个list
     * @param context 方法上下文
     * @return 分页查找一个list的方法
     */
    abstract PsiMethod createPaginatorListMethod(PsiClass context);


    /**
     * 获取模块名称
     * @return  模块名称
     */
    abstract String getModuleName();

    /**
     * 获取包名称
     * @return 包名称
     */
    abstract String getPackageName();
}
