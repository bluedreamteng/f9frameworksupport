package com.tengfei.f9framework.service.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiFileFactory;

public class ServiceInterfaceCodeService {
    private final Project project;

    PsiFileFactory fileFactory;
    PsiElementFactory elementFactory;
    JavaPsiFacade javaPsiFacade;

    public ServiceInterfaceCodeService(Project project) {
        this.project = project;
        fileFactory = PsiFileFactory.getInstance(project);
        elementFactory = PsiElementFactory.getInstance(project);
        javaPsiFacade = JavaPsiFacade.getInstance(project);
    }


    private void test() {

    }


}
