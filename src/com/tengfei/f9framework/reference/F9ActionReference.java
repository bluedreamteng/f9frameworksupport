package com.tengfei.f9framework.reference;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.codeInsight.lookup.LookupElementRenderer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.tengfei.f9framework.icons.F9Icons;
import com.tengfei.f9framework.util.F9WebControllerFacade;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * the reference of f9framework action
 *
 * @author tengfeizhang
 */
public class F9ActionReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private final String action;

    /**
     * @param element   the element of the PSI tree
     * @param textRange A text range defined by start and end (exclusive) offset
     */
    public F9ActionReference(@NotNull PsiElement element, TextRange textRange, boolean isSoft) {
        super(element, textRange, isSoft);
        action = StringUtil.trim(element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset()));
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        List<ResolveResult> results = new ArrayList<>();
        PsiClass controllerClass = F9WebControllerFacade.getInstance(project, action).getControllerClass();
        if(controllerClass != null) {
            results.add(new PsiElementResolveResult(controllerClass));
        }
        return results.toArray(new ResolveResult[0]);
    }

    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Project project = myElement.getProject();
        List<LookupElement> result = new ArrayList<>();
        List<PsiAnnotationMemberValue> allAnnotationValue = F9WebControllerFacade.findAllAnnotationValue(project);
        for (PsiAnnotationMemberValue annotationValue : allAnnotationValue) {
            LookupElementRenderer<LookupElement> renderer = new LookupElementRenderer<LookupElement>() {
                @Override
                public void renderElement(LookupElement element, LookupElementPresentation presentation) {
                    presentation.setItemText(element.getLookupString());
                    presentation.setIcon(F9Icons.SPRINGWEB);
                    presentation.setItemTextBold(true);
                }
            };
            LookupElementBuilder lookupElementBuilder = LookupElementBuilder.create(StringUtil.trim(annotationValue.getText()));
            result.add(lookupElementBuilder.withRenderer(renderer));
        }
        return result.toArray();
    }
}
