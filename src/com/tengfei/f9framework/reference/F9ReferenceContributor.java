package com.tengfei.f9framework.reference;

import com.intellij.lang.javascript.psi.JSLiteralExpression;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

/**
 * 引用拓展
 *
 * @author ztf
 */
public class F9ReferenceContributor extends PsiReferenceContributor {
    /**
     * register referenceProviders
     *
     * @param registrar Allows to register reference providers for specific locations
     */
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(JSLiteralExpression.class), new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                return F9ReferenceFactory.createReferenceByElement(element);
            }
        });

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(XmlAttributeValue.class), new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                return F9ReferenceFactory.createReferenceByElement(element);
            }
        });
    }
}
