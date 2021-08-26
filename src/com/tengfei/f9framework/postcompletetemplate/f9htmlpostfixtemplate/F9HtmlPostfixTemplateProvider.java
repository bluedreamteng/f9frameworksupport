package com.tengfei.f9framework.postcompletetemplate.f9htmlpostfixtemplate;

import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * @author ztf
 */
public class F9HtmlPostfixTemplateProvider implements PostfixTemplateProvider {
    private final Set<PostfixTemplate> myBuiltinTemplates = ContainerUtil.newHashSet();

    public F9HtmlPostfixTemplateProvider() {
        myBuiltinTemplates.add(new F9TestHtmlTemplate(this));
    }

    @Override
    @NotNull
    public Set<PostfixTemplate> getTemplates() {
        return myBuiltinTemplates;
    }

    @Override
    public boolean isTerminalSymbol(char currentChar) {
        return currentChar == '.';
    }

    @Override
    public void preExpand(@NotNull PsiFile file, @NotNull Editor editor) {
    }

    @Override
    public void afterExpand(@NotNull PsiFile file, @NotNull Editor editor) {

    }

    @Override
    @Nullable
    public String getPresentableName() {
        return "HTML";
    }

    @Override
    @NotNull
    public String getId() {
        return "f9builtin.html";
    }

    @Override
    @NotNull
    public PsiFile preCheck(@NotNull PsiFile copyFile, @NotNull Editor realEditor, int currentOffset) {
        return copyFile;
    }
}
