package com.tengfei.f9framework.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.ClassInheritorsSearch;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author ztf
 */
public class F9Util {

    public static List<PsiMethod> findJavaMethod(@NotNull Project project,
                                                 String annotationValue,
                                                 String methodName) {
        List<PsiMethod> psiMethods = new ArrayList<>();
        if (methodName == null || annotationValue == null) {
            return psiMethods;
        }
        List<PsiClass> allPsiClasses = findJavaClass(project, annotationValue);

        for (PsiClass psiClass : allPsiClasses) {
            PsiMethod[] methods = psiClass.getMethods();
            for (PsiMethod psiMethod : methods) {
                if (StringUtil.isEquals(psiMethod.getName(), StringUtil.trim(methodName))) {
                    psiMethods.add(psiMethod);
                }
            }
        }
        return psiMethods;

    }

    public static List<PsiMethod> findAllJavaMethodsByAnnotationValue(Project project, String annotationValue) {
        List<PsiMethod> psiMethods = new ArrayList<>();
        if (annotationValue == null) {
            return psiMethods;
        }
        List<PsiClass> allPsiClasses = findJavaClass(project, annotationValue);

        for (PsiClass psiClass : allPsiClasses) {
            PsiMethod[] methods = psiClass.getMethods();
            psiMethods.addAll(Arrays.asList(methods));
        }
        return psiMethods;
    }

    public static List<PsiAnnotationMemberValue> findAllAnnotationValue(Project project) {
        List<PsiAnnotationMemberValue> result = new ArrayList<>();

        PsiClass baseControllerClass = JavaPsiFacade.getInstance(project).
                findClass("com.epoint.basic.controller.BaseController", GlobalSearchScope.allScope(project));
        if (baseControllerClass != null) {
            Collection<PsiClass> classes = ClassInheritorsSearch.search(baseControllerClass).findAll();
            for (PsiClass eachClass : classes) {
                PsiAnnotation controller = eachClass.getAnnotation("org.springframework.web.bind.annotation.RestController");
                if (controller != null) {
                    PsiAnnotationMemberValue value = controller.findAttributeValue("value");
                    if (value != null) {
                        result.add(value);
                    }
                }
            }
        }
        return result;
    }


    /**
     * @param project         project instance
     * @param annotationValue the value of annotation
     * @return the set of PsiElements
     */
    @NotNull
    public static List<PsiClass> findJavaClass(Project project, String
            annotationValue) {
        List<PsiClass> result = new ArrayList<>();

        if (annotationValue == null) {
            return result;
        }
        PsiClass baseControllerClass = JavaPsiFacade.getInstance(project).
                findClass("com.epoint.basic.controller.BaseController", GlobalSearchScope.allScope(project));

        if (baseControllerClass != null) {
            Collection<PsiClass> classes = ClassInheritorsSearch.search(baseControllerClass).findAll();
            for (PsiClass eachClass : classes) {
                PsiAnnotation controller = eachClass.getAnnotation("org.springframework.web.bind.annotation.RestController");
                if (controller != null) {
                    PsiAnnotationMemberValue value = controller.findAttributeValue("value");
                    if (value != null) {
                        if (StringUtil.isEquals(StringUtil.trim(annotationValue), StringUtil.trim(value.getText()))) {
                            result.add(eachClass);
                        }

                    }
                }
            }
        }


        return result;
    }
}
