package com.tengfei.f9framework.reference;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import org.jetbrains.annotations.NotNull;

/**
 * the reference of f9framework action method
 * @author ztf
 */
public class F9NullReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {


    public F9NullReference(@NotNull PsiElement element, TextRange textRange) {
        super(element, textRange);
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        return ResolveResult.EMPTY_ARRAY;
    }

    @Override
    public PsiElement resolve() {
        return null;
    }
}
