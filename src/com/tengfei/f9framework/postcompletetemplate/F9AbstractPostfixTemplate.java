package com.tengfei.f9framework.postcompletetemplate;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.tengfei.f9framework.util.EditorManage;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public abstract class F9AbstractPostfixTemplate extends F9EditablePostfixTemplate {

    protected F9AbstractPostfixTemplate(@NotNull String id, @NotNull String name, @NotNull String example, TemplateImpl liveTemplate, PostfixTemplateProvider provider) {
        super(id,name,liveTemplate,example, provider);
    }

    /**
     * 元素是否适用此模板
     * @param context context
     * @param copyDocument copyDocument
     * @param newOffset newOffset
     * @return true 适用  false 不适用
     */
    @Override
    public abstract boolean isApplicable(@NotNull PsiElement context, @NotNull Document copyDocument, int newOffset);

    @Override
    public void expand(@NotNull PsiElement context, @NotNull Editor editor) {
        EditorManage.getInstance(editor).removeExpression(context);
        Project project = editor.getProject();
        assert project != null;
        final TemplateManager manager = TemplateManager.getInstance(project);
        final Template template = manager.createTemplate("", "", getLiveTemplate().getTemplateText());
        template.setToReformat(true);
        manager.startTemplate(editor, template);
        System.out.println("helloworld");
    }

    protected static TemplateImpl createTemplate(@NotNull String templateText) {
        TemplateImpl template = new TemplateImpl("fakeKey", templateText, "");
        template.setToReformat(true);
        template.parseSegments();
        return template;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }
}
