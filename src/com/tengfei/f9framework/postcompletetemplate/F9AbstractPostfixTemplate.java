package com.tengfei.f9framework.postcompletetemplate;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.tengfei.f9framework.util.EditorManage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ztf
 */
public abstract class F9AbstractPostfixTemplate extends F9EditablePostfixTemplate {

    protected F9AbstractPostfixTemplate(@NotNull String id, @NotNull String name, @NotNull String example, TemplateImpl liveTemplate, PostfixTemplateProvider provider) {
        super(id,name,liveTemplate,example, provider);
    }

    protected F9AbstractPostfixTemplate(@NotNull String id, @NotNull String name, @NotNull String example, String templateText, PostfixTemplateProvider provider) {
        super(id,name,createTemplate(templateText),example, provider);
    }

    @Override
    protected List<PsiElement> getExpressions(@NotNull PsiElement context, @NotNull Document document, int offset) {
        return new ArrayList<>();
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
        final Template template = manager.createTemplate("", "", getTemplateText(context));
        template.setToReformat(true);
        manager.startTemplate(editor, template);
    }

    public String getTemplateText(PsiElement context) {
        String templateVariable = context.getText();
        String templateText = getLiveTemplate().getTemplateText();
        if(StringUtil.isNotEmpty(templateText)) {
            templateText = templateText.replaceAll("#expr#",templateVariable);
            templateText = templateText.replaceAll("#lowercaseexpr#",StringUtil.toLowerCase(templateVariable));
        }
        return templateText;
    }



    private static TemplateImpl createTemplate(@NotNull String templateText) {
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
