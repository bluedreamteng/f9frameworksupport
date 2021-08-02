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
import org.jetbrains.annotations.NotNull;

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
        switch (type) {
            case SERVICE_INTERFACE:
                return new ServiceInterfaceCodeService(project, tableInfo, pathConfig);
            case SERVICE_IMPL:
                return new ServiceImplCodeService(project, tableInfo, pathConfig);
            case SERVICE:
                return new ServiceCodeService(project, tableInfo, pathConfig);
            default:
                throw new RuntimeException("unsupported type");
        }
    }


    public PsiClass generateCode() {
        PsiClass result = createClass();
        PsiMethod insertMethod = createInsertMethod(result);
        insertMethod.addBefore(createInsertDocComment(), insertMethod.getFirstChild());
        result.add(insertMethod);

        PsiMethod deleteMethod = createDeleteMethod(result);
        insertMethod.addBefore(createDeleteDocComment(), deleteMethod.getFirstChild());
        result.add(deleteMethod);

        PsiMethod updateMethod = createUpdateMethod(result);
        updateMethod.addBefore(createUpdateDocComment(), updateMethod.getFirstChild());
        result.add(updateMethod);

        PsiMethod countWithConditionMethod = createCountWithConditionMethod(result);
        countWithConditionMethod.addBefore(createCountWithConditionDocComment(),countWithConditionMethod.getFirstChild());
        result.add(countWithConditionMethod);

        PsiMethod findByPrimaryKeyMethod = createFindByPrimaryKeyMethod(result);
        findByPrimaryKeyMethod.addBefore(createFindByPrimaryKeyDocComment(),findByPrimaryKeyMethod.getFirstChild());
        result.add(findByPrimaryKeyMethod);

        PsiMethod findListWithConditionMethod = createFindListWithConditionMethod(result);
        findListWithConditionMethod.addBefore(createFindListWithConditionDocComment(),findListWithConditionMethod.getFirstChild());
        result.add(findListWithConditionMethod);

        PsiMethod paginatorListMethod = createPaginatorListMethod(result);
        paginatorListMethod.addBefore(createPaginatorDocComment(),paginatorListMethod.getFirstChild());
        result.add(paginatorListMethod);
        
        GenerateTableCodeUtil.generateCode(getModuleName(), getPackageName(), result);
        return result;
    }

    abstract PsiClass createClass();

    /**
     * 插入数据
     *
     * @param context 方法上下文
     * @return 插入数据方法的方法
     */
    abstract PsiMethod createInsertMethod(PsiClass context);

    @NotNull
    private PsiDocComment createInsertDocComment() {
        return elementFactory.createDocCommentFromText("/**\n" +
                "     * 插入数据\n" +
                "     * \n" +
                "     * @param record BaseEntity或Record对象 <必须继承Record>\n" +
                "     * @return int\n" +
                "     */");
    }

    /**
     * 删除数据
     *
     * @param context 方法上下文
     * @return 删除数据的方法
     */
    abstract PsiMethod createDeleteMethod(PsiClass context);


    @NotNull
    private PsiDocComment createDeleteDocComment() {
        return elementFactory.createDocCommentFromText("/**\n" +
                "     * 删除数据\n" +
                "     * \n" +
                "     * @param guid\n" +
                "     *            主键guid\n" +
                "     * @return int\n" +
                "     */");
    }

    /**
     * 更新数据
     *
     * @param context 方法上下文
     * @return 更新数据的方法
     */
    abstract PsiMethod createUpdateMethod(PsiClass context);

    @NotNull
    private PsiDocComment createUpdateDocComment() {
        return elementFactory.createDocCommentFromText("/**\n" +
                "     * 更新数据\n" +
                "     * \n" +
                "     * @param record\n" +
                "     *            BaseEntity或Record对象 <必须继承Record>\n" +
                "     * @return int\n" +
                "     */");
    }

    /**
     * 查询数量
     *
     * @param context 方法上下文
     * @return 查询数量的方法
     */
    abstract PsiMethod createCountWithConditionMethod(PsiClass context);

    @NotNull
    private PsiDocComment createCountWithConditionDocComment() {
        return elementFactory.createDocCommentFromText("/**\n" +
                "     * 查询数量\n" +
                "     * \n" +
                "     * @param conditionMap\n" +
                "     *            查询条件集合\n" +
                "     * @return Integer\n" +
                "     */");
    }

    /**
     * 根据ID查找单个实体
     *
     * @param context 方法上下文
     * @return 根据ID查找单个实体的方法
     */
    abstract PsiMethod createFindByPrimaryKeyMethod(PsiClass context);

    @NotNull
    private PsiDocComment createFindByPrimaryKeyDocComment() {
        return elementFactory.createDocCommentFromText("/**\n" +
                "     * 根据ID查找单个实体\n" +
                "     * \n" +
                "     * @param primaryKey\n" +
                "     *            主键\n" +
                "     * @return T extends BaseEntity\n" +
                "     */");
    }

    /**
     * 查找单条记录
     *
     * @param context 方法上下文
     * @return 查找单条记录的方法
     */
    abstract PsiMethod createFindWithConditionMethod(PsiClass context);

    @NotNull
    private PsiDocComment createFindWithConditionDocComment() {
        return elementFactory.createDocCommentFromText("/**\n" +
                "     * 查找单条记录\n" +
                "     * \n" +
                "     * @param conditionMap\n" +
                "     *            查询条件集合\n" +
                "     * @return T {String、Integer、Long、Record、FrameOu、Object[]等}\n" +
                "     */");
    }

    /**
     * 查找一个list
     *
     * @param context 方法上下文
     * @return 查找一个list的方法
     */
    abstract PsiMethod createFindListWithConditionMethod(PsiClass context);

    @NotNull
    private PsiDocComment createFindListWithConditionDocComment() {
        return elementFactory.createDocCommentFromText("/**\n" +
                "     * 查找一个list\n" +
                "     * \n" +
                "     * @param conditionMap\n" +
                "     *            查询条件集合\n" +
                "     * @return T extends BaseEntity\n" +
                "     */");
    }

    /**
     * 分页查找一个list
     *
     * @param context 方法上下文
     * @return 分页查找一个list的方法
     */
    abstract PsiMethod createPaginatorListMethod(PsiClass context);

    @NotNull
    private PsiDocComment createPaginatorDocComment() {
        return elementFactory.createDocCommentFromText("/**\n" +
                "     * 分页查找一个list\n" +
                "     * \n" +
                "     * @param conditionMap\n" +
                "     *            查询条件集合\n" +
                "     * @param pageNumber\n" +
                "     *            记录行的偏移量\n" +
                "     * @param pageSize\n" +
                "     *            记录行的最大数目\n" +
                "     * @return T extends BaseEntity\n" +
                "     */");
    }


    /**
     * 获取模块名称
     *
     * @return 模块名称
     */
    abstract String getModuleName();

    /**
     * 获取包名称
     *
     * @return 包名称
     */
    abstract String getPackageName();
}
