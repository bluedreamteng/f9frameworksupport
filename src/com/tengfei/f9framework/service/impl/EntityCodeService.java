package com.tengfei.f9framework.service.impl;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.util.PackageUtil;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiUtil;
import com.tengfei.f9framework.entity.ColumnInfo;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.util.NameUtils;
import org.jetbrains.jps.model.java.JavaSourceRootType;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EntityCodeService {
    private final Project project;

    PsiFileFactory fileFactory;
    PsiElementFactory elementFactory;
    JavaPsiFacade javaPsiFacade;


    public EntityCodeService(Project project) {
        this.project = project;
        fileFactory = PsiFileFactory.getInstance(project);
        elementFactory = PsiElementFactory.getInstance(project);
        javaPsiFacade = JavaPsiFacade.getInstance(project);
    }

    public void generateEntityByTableInfoAndPathConfig(TableInfo tableInfo, PathConfig pathConfig) {
        //创建实体java文件
        PsiJavaFile entityFile = (PsiJavaFile) fileFactory.createFileFromText(tableInfo.getName() + ".java", JavaFileType.INSTANCE, "");
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
        } else {
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
        //创建实体get set方法
        entityFile.add(entityClass);
        reformatJavaFile(entityFile);
        Module moduleByName = ModuleManager.getInstance(project).findModuleByName(pathConfig.getModuleName());
        List<VirtualFile> sourceRoots = ModuleRootManager.getInstance(moduleByName).getSourceRoots(JavaSourceRootType.SOURCE);
        VirtualFile virtualFile = sourceRoots.get(0);
        PsiDirectory directory = PsiManager.getInstance(project).findDirectory(virtualFile);
        WriteCommandAction.runWriteCommandAction(project, () -> {
            PsiDirectory packageDirectory = PackageUtil.findOrCreateDirectoryForPackage(moduleByName, pathConfig.getEntityPackageName(), directory, true);
            assert packageDirectory != null;
            packageDirectory.add(entityFile);
        });
        //
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
        StringBuilder setterMethod = new StringBuilder();
        setterMethod.append(String.format("public void set%s(%s %s) {", NameUtils.toCamelCaseWithFirstUpper(columnInfo.getName()), columnInfo.getType(), columnInfo.getName().toLowerCase()));
        setterMethod.append(String.format(" super.set(\"%s\", %s);", columnInfo.getName().toLowerCase(), columnInfo.getName().toLowerCase()));
        setterMethod.append("}");
        return elementFactory.createMethodFromText(setterMethod.toString(), context);
    }

    private PsiMethod createGetterMethodByColumnInfo(ColumnInfo columnInfo, PsiClass context) {
        StringBuilder getterMethodText = new StringBuilder();
        getterMethodText.append(String.format("public %s get%s() {", columnInfo.getType(), NameUtils.toCamelCaseWithFirstUpper(columnInfo.getName())));
        getterMethodText.append(String.format("return super.get(\"%s\");", columnInfo.getName().toLowerCase()));
        getterMethodText.append("}");
        return elementFactory.createMethodFromText(getterMethodText.toString(), context);
    }
    private void reformatJavaFile(PsiJavaFile javaFile) {
        JavaCodeStyleManager.getInstance(javaFile.getProject()).shortenClassReferences(javaFile);
        CodeStyleManager.getInstance(javaFile.getProject()).reformat(javaFile);
    }
}
