package test;

import com.intellij.facet.Facet;
import com.intellij.facet.FacetManager;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

public class TestAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project project = e.getProject();
        PsiFile data = e.getData(CommonDataKeys.PSI_FILE);
        Module moduleForFile = ModuleUtil.findModuleForFile(data);
        Facet<?>[] allFacets = FacetManager.getInstance(moduleForFile).getAllFacets();
        System.out.println(allFacets);
        WebFacet facetByType = FacetManager.getInstance(moduleForFile).getFacetByType(WebFacet.ID);
        System.out.println(facetByType.getWebRoots().get(0).getPresentableUrl());
    }
}
