package com.tengfei.f9framework.service.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.tengfei.f9framework.entity.ColumnInfo;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.util.GenerateTableCodeUtil;
import com.tengfei.f9framework.util.NameUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ztf
 */
public class EntityCodeService {
    private final Project project;

    PsiElementFactory elementFactory;
    JavaPsiFacade javaPsiFacade;


    public EntityCodeService(Project project) {
        this.project = project;
        elementFactory = PsiElementFactory.getInstance(project);
        javaPsiFacade = JavaPsiFacade.getInstance(project);
    }

    public void generateEntityByTableInfoAndPathConfig(TableInfo tableInfo, PathConfig pathConfig) {
        //创建实体java类
        PsiClass entityClass = elementFactory.createClass(tableInfo.getName());
        assert entityClass.getExtendsList() != null;
        PsiClass extendClass = javaPsiFacade.findClass("com.epoint.core.BaseEntity", GlobalSearchScope.allScope(project));
        if (extendClass != null) {
            entityClass.getExtendsList().add(elementFactory.createClassReferenceElement(extendClass));
        }
        //加入注解
        String tableValue = tableInfo.getOriginal().getName();
        String primaryKeys = tableInfo.getPkColumn().stream().map((columnInfo -> columnInfo.getObj().getName().toLowerCase())).collect(Collectors.joining("\",\""));
        PsiAnnotation tempEntityAnnotation;
        if (!StringUtil.isEmpty(primaryKeys)) {
            tempEntityAnnotation = elementFactory.createAnnotationFromText(String.format("@com.epoint.core.annotation.Entity(table = \"%s\", id = {\"%s\"})", tableValue, primaryKeys), entityClass);
        }
        else {
            tempEntityAnnotation = elementFactory.createAnnotationFromText(String.format("@com.epoint.core.annotation.Entity(table = \"%s\")", tableValue), entityClass);
        }
        PsiAnnotation entityAnnotation = Objects.requireNonNull(entityClass.getModifierList()).addAnnotation(Objects.requireNonNull(tempEntityAnnotation.getQualifiedName()));
        entityAnnotation.setDeclaredAttributeValue("table", tempEntityAnnotation.findAttributeValue("table"));
        entityAnnotation.setDeclaredAttributeValue("id", tempEntityAnnotation.findAttributeValue("id"));
        List<ColumnInfo> fullColumn = tableInfo.getFullColumn();
        for (ColumnInfo columnInfo : fullColumn) {
            PsiAnnotation getterAnnotation = createGetterAnnotationByColumnInfo(columnInfo, entityClass);
            PsiMethod getterMethod = createGetterMethodByColumnInfo(columnInfo, entityClass);
            PsiMethod setterMethod = createSetterMethodByColumnInfo(columnInfo, entityClass);
            entityClass.add(setterMethod);
            if (getterAnnotation != null) {
                getterMethod.getModifierList().addAnnotation(getterAnnotation.getQualifiedName()).
                        setDeclaredAttributeValue("length", getterAnnotation.findAttributeValue("length"));
            }
            entityClass.add(getterMethod);
        }
        GenerateTableCodeUtil.generateCode(pathConfig.getModuleName(), pathConfig.getEntityPackageName(), entityClass);
    }

    private PsiAnnotation createGetterAnnotationByColumnInfo(ColumnInfo columnInfo, PsiClass entityClass) {
        if ("java.lang.String".equals(columnInfo.getType())) {
            //加上注解
            return elementFactory.createAnnotationFromText(
                    String.format("@com.epoint.core.annotation.EntityLength(length = %d)",
                            columnInfo.getLength()), entityClass);
        }
        return null;
    }

    private PsiMethod createSetterMethodByColumnInfo(ColumnInfo columnInfo, PsiClass context) {
        StringBuilder methodText = new StringBuilder();
        methodText.append(String.format("public void set%s(%s %s) {", NameUtils.toCamelCaseWithFirstUpper(columnInfo.getName()), columnInfo.getType(), columnInfo.getName().toLowerCase()));
        methodText.append(String.format(" super.set(\"%s\", %s);", columnInfo.getName().toLowerCase(), columnInfo.getName().toLowerCase()));
        methodText.append("}");
        return elementFactory.createMethodFromText(methodText.toString(), context);
    }

    private PsiMethod createGetterMethodByColumnInfo(ColumnInfo columnInfo, PsiClass context) {
        StringBuilder methodText = new StringBuilder();
        methodText.append(String.format("public %s get%s() {", columnInfo.getType(), NameUtils.toCamelCaseWithFirstUpper(columnInfo.getName())));
        methodText.append(String.format("return super.get(\"%s\");", columnInfo.getName().toLowerCase()));
        methodText.append("}");
        return elementFactory.createMethodFromText(methodText.toString(), context);
    }
}
