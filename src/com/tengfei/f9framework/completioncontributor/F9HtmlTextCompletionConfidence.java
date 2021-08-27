package com.tengfei.f9framework.completioncontributor;

import com.intellij.codeInsight.completion.CompletionConfidence;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlText;
import com.intellij.psi.xml.XmlTokenType;
import com.intellij.util.ThreeState;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class F9HtmlTextCompletionConfidence  extends CompletionConfidence {
    @NotNull
    @Override
    public ThreeState shouldSkipAutopopup(@NotNull PsiElement contextElement, @NotNull PsiFile psiFile, int offset) {
        return notSkipAutopopupInHtml(contextElement, offset) ? ThreeState.NO : ThreeState.UNSURE;
    }

    public static boolean notSkipAutopopupInHtml(@NotNull PsiElement contextElement, int offset) {
        ASTNode node = contextElement.getNode();
        if (node != null && node.getElementType() == XmlTokenType.XML_DATA_CHARACTERS) {
            PsiElement parent = contextElement.getParent();
            if (parent instanceof XmlText || parent instanceof XmlDocument) {
                String contextElementText = contextElement.getText();
                int endOffset = offset - contextElement.getTextRange().getStartOffset();
                String prefix = contextElementText.substring(0, Math.min(contextElementText.length(), endOffset));
                return prefix.contains(".");
            }
        }
        return false;
    }
}
