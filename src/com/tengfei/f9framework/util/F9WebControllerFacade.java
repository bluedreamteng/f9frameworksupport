package com.tengfei.f9framework.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.ClassInheritorsSearch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author ztf
 */
public class F9WebControllerFacade {
    public static final String DTO_WEB_ROUTER = "com.epoint.core.dto.DtoWebRouter";
    public static final String REST_CONTROLLER_ANNOTATION = "org.springframework.web.bind.annotation.RestController";

    @NotNull
    private final Project project;
    @NotNull
    private final String controllerAnnotationValue;
    @Nullable
    private final PsiClass controllerClass;

    public static F9WebControllerFacade getInstance(@NotNull Project project, @NotNull String controllerAnnotationValue) {
        return new F9WebControllerFacade(project, controllerAnnotationValue);
    }

    private F9WebControllerFacade(@NotNull Project project, @NotNull String controllerAnnotationValue) {
        this.project = project;
        this.controllerAnnotationValue = controllerAnnotationValue;
        controllerClass = findJavaClass();
    }

    @Nullable
    public PsiClass getControllerClass(){
        return controllerClass;
    }


    @Nullable
    public PsiClass findJavaClass() {
        if (StringUtil.isEmpty(controllerAnnotationValue)) {
            return null;
        }
        PsiClass baseControllerClass = JavaPsiFacade.getInstance(project).
                findClass(DTO_WEB_ROUTER, GlobalSearchScope.allScope(project));
        if (baseControllerClass != null) {
            Collection<PsiClass> classes = ClassInheritorsSearch.search(baseControllerClass).findAll();
            for (PsiClass eachClass : classes) {
                PsiAnnotation controller = eachClass.getAnnotation(REST_CONTROLLER_ANNOTATION);
                if (controller != null) {
                    PsiAnnotationMemberValue value = controller.findAttributeValue("value");
                    if (value != null) {
                        String annotationValue = StringUtil.trim(value.getText(),(s)-> s != '"');
                        if (controllerAnnotationValue.equals(annotationValue)) {
                            return eachClass;
                        }
                    }
                }
            }
        }
        return null;
    }

    @NotNull
    public List<PsiMethod> findMethodByMethodName(String methodName) {
        List<PsiMethod> result = new ArrayList<>();
        if (controllerClass == null) {
            return result;
        }
        PsiMethod[] methods = controllerClass.getAllMethods();
        for (PsiMethod psiMethod : methods) {
            if (psiMethod.getName().equals(methodName)) {
                result.add(psiMethod);
            }
        }
        return result;
    }

    @NotNull
    public List<PsiMethod> findAllMethods() {
        if (controllerClass == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(controllerClass.getAllMethods());
    }

    public static List<PsiAnnotationMemberValue> findAllAnnotationValue(Project project) {
        List<PsiAnnotationMemberValue> result = new ArrayList<>();
        PsiClass baseControllerClass = JavaPsiFacade.getInstance(project).
                findClass(DTO_WEB_ROUTER, GlobalSearchScope.allScope(project));
        if (baseControllerClass != null) {
            Collection<PsiClass> classes = ClassInheritorsSearch.search(baseControllerClass).findAll();
            for (PsiClass eachClass : classes) {
                PsiAnnotation controller = eachClass.getAnnotation(REST_CONTROLLER_ANNOTATION);
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
}
